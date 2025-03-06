# 6. 기본적인 리팩터링

## 6.1 함수 추출하기

| 리팩터링 전                                                                                                                                                                                                                                                                                                                                                   | 리팩터링 후                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  |
| -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| public class InvoicePrinter {  <br>    public void printOwing(Invoice invoice) {  <br>        printBanner();  <br>        var outstanding = calculateOutstanding();  <br>  <br>        // 세부 사항 출력  <br>        ==System.out.println("고객명 : " + invoice.getCustomer());==  <br>        ==System.out.println("채무액 : " + outstanding);==  <br>    }  <br>} | public class InvoicePrinter {  <br>  <br>    public void printOwing(Invoice invoice) {  <br>        printBanner();  <br>        var outstanding = calculateOutstanding();  <br>  <br>        // 세부 사항 출력  <br>        ==printDetails(invoice, outstanding);==<br>    }  <br><br>  private void printDetails(Invoice invoice, Integer outstanding) {  <br>    System.out.println("고객명 : " + invoice.getCustomer());  <br>    System.out.println("채무액 : " + outstanding);  <br>    }<br>} |
- 코드 조각을 찾아 무슨 일을 하는지 파악한 다음, 독립된 함수로 추출하고 목적에 맞는 이름을 붙인다.

### 함수 추출 기준
- 길이
  - 한 화면을 넘어가지 않게 하기 위해 추출
- 재사용
  - 두번 이상 사용되는 코드는 함수로 만든다
- 목적과 구현을 분리 (추천)
  - 코드를 보고 무슨 일을 하는지 파악하는데 오래 걸린다면 함수로 추출 후 이름을 붙인다.

### 절차
- 함수를 새로 만들고 목적을 잘 드러내는 이름을 붙인다.
  - 어떻게(how) 가 아닌 무엇을(what) 하는지 잘 드러나게 붙인다
- 추출할 코드를 복사, 붙여넣기한다
- 필요한 데이터는 매개변수로 전달한다
- 원본 함수에서 추출한 코드 부분을 새로 만든 함수를 호출하는 문장으로 바꾼다
- 테스트
- 적용할 다른 코드가 있는지 확인한다.

### 주의
추출한 함수에서 변경한 변수가 함수 밖에서 사용될 때는 주의해야 한다.
```javaava
public void printOwing(Invoice invoice) {  
    int outstanding = 0;  
      
    printBanner();  
  
    for (Order order : invoice.getOrders()) {  
        outstanding += order.getAmount();  
    }  
  
    // 세부 사항 출력  
    printDetails(invoice, outstanding);  
}
```

- outstanding 변수 위치를 옮긴다
  - 변수 조작을 모두 한곳에 처리하도록 모아둔다.
- 새로운 함수를 만들어 추출할 부분을 새로운 함수로 복사한다.
- 추출한 코드의 원래 자리를 새로 뽑아낸 함수를 호출하는 문장으로 교체한다.

```java
public void printOwing(Invoice invoice) {  
    printBanner();  
  
    int outstanding = calculateOutstanding(invoice);  
  
    // 세부 사항 출력  
    printDetails(invoice, outstanding);  
}  
  
private int calculateOutstanding(Invoice invoice) {  
    int outstanding = 0;  
    for (Order order : invoice.getOrders()) {  
        outstanding += order.getAmount();  
    }  
    return outstanding;  
}
```

> [!info] 값을 반환할 변수가 여러개라면?
- 함수가 하나의 값만 반환하게 여러개의 함수로 만든다.


## 6.2 함수 인라인하기

| 리팩터링 전                                                                                                                                                                                                                                        | 리팩터링 후                                                                                                       |
| --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------ |
| public int getRating(Driver driver) {  <br>    return moreThanFiveLateDeliveries(driver) ? 2 : 1;  <br>}  <br>  <br>public boolean moreThanFiveLateDeliveries(Driver driver) {  <br>    return driver.getNumberOfLateDeliveries() > 5;  <br>} | public int getRating(Driver driver) {  <br>    return driver.getNumberOfLateDeliveries() > 5 ? 2 : 1;  <br>} |
### 함수 인라인 대상
- 함수 본문이 이름만큼 명확한 경우
- 간접 호출을 너무 과하게 쓰는 코드
  - 위임 관계가 복잡하게 얽혀 있으면 인라인해버린다.

### 절차
- 다형 메서드인지 확인한다
  - 서브클래스에서 오버라이드하는 메서드는 인라인하면 안된다
- 인라인할 함수를 호출하는 곳을 모두 찾는다
- 각 호출문을 함수 본문으로 교체한다
- 하나씩 교체 후 테스트
- 기존 정의된 함수를 삭제한다

## 6.3 변수 추출하기

| 리팩터링 전                                                                                                                                                                                                                                                                               | 리팩터링 후                                                                                                                                                                                                                                                                                                                                                                           |
| ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| public double calculate(Order order) {  <br>    return order.getQuantity() * order.getItemPrice() -  <br>            Math.max(0, order.getQuantity() - 500) * order.getItemPrice() * 0.05 +  <br>            Math.min(order.getQuantity() * order.getItemPrice() * 0.1, 100);  <br>} | public double calculate(Order order) {  <br>    int basePrice = order.getQuantity() * order.getItemPrice();  <br>    double quantityDiscount = Math.max(0, order.getQuantity() - 500) * order.getItemPrice() * 0.05;  <br>    double shipping = Math.min(order.getQuantity() * order.getItemPrice() * 0.1, 100);  <br>    return basePrice - quantityDiscount + shipping;  <br>} |
### 배경
표현식이 복잡해서 이해하기 어려운 코드가 있다. 이럴 때 지역 변수를 활용하면 표현식을 쪼개 관리하기 더 쉽게 만들 수 있다. 복잡한 로직을 구성하는 단계마다 이름을 붙일 수 있어서 코드의 목적을 훨씬 명확하게 드러낼 수 있다.

함수 안에서만 의미가 있는 변수인지, 함수를 벗어난 넓은 문맥을 고려해야 한다면 넓은 범위에서 통용되는 이름이 필요하다. 이럴 땐 변수가 아닌 (주로) 함수로 추출해야 한다.

### 절차
- 표현식 추출에 부작용이 없는지 확인한다
- 불변 변수를 하나 선언하고 이름을 붙일 표현식의 복제본을 대입한다.
- 원본 표현식을 새로 만든 변수로 교체
- 테스트
- 사용하는 곳을 새로 만든 변수로 교체한다.

클래스 전체에 영향을 줄 때는 변수가 아닌 메서드로 추출해본다
```java
public double calculate(Order order) {  
    return basePrice() - quantityDiscount() + shipping();  
}  
  
public int basePrice() {  
    return getQuantity() * getItemPrice();  
}  
  
public double quantityDiscount() {  
    return Math.max(0, getQuantity() - 500) * getItemPrice() * 0.05;  
}  
  
public double shipping() {  
    return Math.min(getQuantity() * getItemPrice() * 0.1, 100);  
}
```

## 6.4 변수 인라인하기

| 리팩터링 전                                                                                                                               | 리팩터링 후                                                                                         |
| ------------------------------------------------------------------------------------------------------------------------------------ | ---------------------------------------------------------------------------------------------- |
| public boolean isExpensive(Order anOrder) {  <br>    int basePrice = anOrder.basePrice();  <br>    return (basePrice > 1000);  <br>} | public boolean isExpensive(Order anOrder) {  <br>    return anOrder.basePrice() > 1000;  <br>} |
변수는 함수 안에서 표현식을 가리키는 이름으로 쓰이며, 대체로 긍정적인 효과를 준다.

### 변수 인라인 대상
- 변수의 이름이 표현식과 다를 바 없을 때
- 주변 코드를 리팩터링하는데 방해가 될 때

### 절차
- 표현식에서 부작용이 생기지는 않는지 확인
- 변수가 불변으로 선언되지 않았다면 불변으로 만든 후 테스트한다
  - 이렇게 하면 변수에 값이 단 한 번만 대입되는지 확인이 가능
- 이 변수를 가장 처음 사용하는 코드를 찾아서 대입문 우변의 코드로 바꾼다.
- 테스트
- 변수를 사용하는 부분을 모두 교체할 때까지 이 과정을 반복
- 변수 선언문과 대입문을 지운다

## 6.5 함수 선언 바꾸기

| 리팩터링 전                           | 리팩터링 후                                  |
| -------------------------------- | --------------------------------------- |
| void circum(Radius radius) {...} | void circumference(Radius radius) {...} |

함수 선언은 시스템 구성 요소를 조립하는 연결부 역할을 한다.
- 함수 선언을 잘하면?
  - 시스템에 새로운 부분을 추가하기가 쉬워진다
- 함수 선언을 잘못하면?
  - 소프트웨어 동작을 파악하기 어려워진다
  - 요구사항이 바뀔 때 수정이 어렵다
- 함수 선언부 이름
  - 이름이 좋으면 함수의 구현 코드를 보지 않고 무슨 일을 하는지 파악이 가능하다
  - 이름이 잘 떠오르지 않으면? 주석을 이용해 함수의 목적을 설명해봐라
- 함수 선언부 매개변수
  - 예로 전화번호 포매팅 함수의 매개변수로 사람을 받는 함수는 매개변수로 전화번호를 받을 수 없다.
  - 사람대신 전화번호 자체를 받도록 정의하면 함수의 활용 범위를 넓힐 수 있다.
  - 다른 모듈과의 결합을 제거할 수 있다
    - 전화번호 포매팅 로직을 사람 관련 정보를 전혀 모르는 모듈에 둘 수 있다
    - 동작에 필요한 모듈 수가 줄어들수록 무언가를 수정할 때 담아둬야 하는 내용도 적다
  - 매개변수를 객체로 정의하면 이 객체는 인터페이스와 결합된다. (수정이 어렵다)
    - 대신 객체의 속성에 쉽게 접근할 수 있다
    - 캡슐화 수준을 높일 수 있다

### 간단한 절차
- 매개변수를 제거하려면 먼저 함수 본문에서 제거 대상 매개변수를 참조하는 곳은 없는지 확인
- 메서드 선언을 원하는 형태로 변경
- 기존 메서드 선언을 참조하는 부분을 모두 찾아서 바뀐 형태로 수정한다
- 테스트

이름 변경과 매개변수 추가를 모두 하고 싶다면 각각 독립적으로 처리하자. 문제가 생기면 작업을 되돌리고 마이그레이션 절차를 따른다.

```java
// 리팩터링 전
public double circum(Integer radius) {  
    return 2 * Math.PI * radius;  
}

// 리팩터링 후
public double circumference(Integer radius) {  
    return 2 * Math.PI * radius;  
}
```

### 마이그레이션 절차
- 함수의 본문을 새로운 함수로 추출한다
  - 새로 만들 함수 이름이 기존 함수와 같다면 검색하기 쉬운 이름을 임시로 사용
- 추출한 함수에 매개변수를 추가해야 한다면 간단한 절차를 따라 추가한다
- 테스트
- 기존 함수를 인라인한다
  - 예전 함수를 호출하는 부분이 모두 새 함수를 호출하도록 바뀐다.
- 이름을 임시로 붙여뒀다면 함수 선언 바꾸기를 한번 더 적용해서 원래 이름으로 되돌린다
- 테스트

```java
// 리팩터링 전
public double circum(Integer radius) {  
    return 2 * Math.PI * radius;  
}
```

```java
// 리팩터링 후
public double circum(Integer radius) {  
    return circumference(radius);  
}  
  
public double circumference(Integer radius) {  
    return 2 * Math.PI * radius;  
}
```

### 매개변수 속성 바꾸기
```java
public boolean isNewEngland(Customer customer) {  
    return Stream.of("MA","CT","ME","VT","NH","RI")  
            .anyMatch(k -> k.equals(customer.state));  
}

호출부
// 사용하는 곳
List<Customer> englandCustomers = customer.stream()  
        .filter(this::isNewEngland)  
        .toList();
```

isNewEngland 함수는 고객이 뉴잉글랜드에 사는지 판단하는 함수다. 이 함수는 현재 Customer 와 결합되어 넓은 문맥에서 사용할 수 없다. 넓게 활용하기 위해선 Customer 대신 state 를 사용하게 리팩터링 해야한다.

- 매개변수로 사용할 코드를 변수로 추출
```java
public boolean isNewEngland(Customer customer) {  
    String state = customer.state; 
    return Stream.of("MA","CT","ME","VT","NH","RI")  
            .anyMatch(k -> k.equals(state));  
}
```
- 새 함수를 만든다.
```java
public boolean isNewEngland(Customer customer) {  
    String state = customer.state;  
    return Stream.of("MA","CT","ME","VT","NH","RI")  
            .anyMatch(k -> k.equals(state));  
}  
  
public boolean xxIsNewEngland(String state) {  
    return Stream.of("MA","CT","ME","VT","NH","RI")  
            .anyMatch(k -> k.equals(state));  
}
```
- 인라인
```java
public boolean isNewEngland(Customer customer) {  
    return xxIsNewEngland(customer.state);  
}  
  
public boolean xxIsNewEngland(String state) {  
    return Stream.of("MA","CT","ME","VT","NH","RI")  
            .anyMatch(k -> k.equals(state));  
}

호출부
List<Customer> englandCustomers = customer.stream()  
        .filter((k) -> xxIsNewEngland(k.state))  
        .toList();
```
- 함수 이름 변경 (xxIsNewEngland -> isNewEngland)
- 기존 isNewEngland(Customer customer) 는 삭제한다

## 6.6 변수 캡슐화하기

함수의 이름을 바꾸거나 다른 모듈로 옮기는건 어렵지 않다. 기존 함수를 그대로 둔 채 전달 함수로 활용할 수도 있다. 예전 코드들은 기존 함수를 호출하고, 이 기존 함수가 새로 만든 함수를 호출하는 식이다.

데이터는 다루기 까다롭다. 좁은 범위에서 사용되는 데이터는 어렵지 않지만, 범위가 넓어질수록 다루기 어려워진다. 전역 데이터가 골치 아픈 이유도 여기에 있다. 그래서 범위가 넓은 데이터를 옮길 때는 먼저 접근을 제한하는 함수를 만드는 식으로 캡슐화하는 것이 좋은 방법이다.

데이터 캡슐화 장점
- 데이터 변경, 사용의 통로가 되어줘 데이터 변경 전 검증이나 변경 후 추가로직을 쉽게 끼워 넣을 수 있다
- 데이터 결합도를 막을 수 있다.

불변성은 강력한 방부제다
- 불변 데이터는 캡슐화할 이유가 적다
- 데이터가 변경될 일이 없어서 갱신 전 검증 같은 추가 로직을 구현할 필요 없다
- 불변 데이터는 옮길 필요 없이 그냥 복제하면 된다
- 데이터를 참조하는 코드를 변경할 필요 없고, 데이터를 변형 시키는 코드를 걱정할 일이 없다

### 절차
- 변수로의 접근과 갱신을 전담하는 캡슐화 함수를 만든다
- 변수를 직접 참조하던 부분을 모두 적절한 캡슐화 함수 호출로 바꾼다.
- 테스트
- 변수의 접근 범위를 제한한다.(private -> public)
- 테스트

### 값 캡슐화
기본 캡슐화 기법은 데이터 항목을 참조하는 부분만 캡슐화한다. 하지만 변수뿐 아니라 변수에 담긴 내용을 변경하는 행위까지 제어할 수 있게 캡슐화하고 싶을 때도 많다

- 값을 바꿀 수 없게 만든다
  - 게터가 데이터의 복제본을 반환하도록 수정
  - 복제본을 반환하면 클라이언트는 게터로 얻은 데이터를 변경할 수는 있지만 원본에는 영향을 주지 못한다
- 레코드 캡슐화(7장에서 자세히)

변경을 감지하여 막는 기법을 임시로 활용해보면 도움이 될 때가 많다. 변경하는 부분을 없앨 수도 있고, 적절한 변경 함수를 제공할 수도 있다. 적절히 처리한 후에 게터가 복제본을 반환하도록 수정하면 된다.

## 6.7 변수 이름 바꾸기

명확한 프로그래밍의 핵심은 이름짓기다.

이름을 잘 못 짓는 경우
- 고민을 충분히 하지 않아서
- 문제에 대한 이해도가 낮아서
- 사용자의 요구가 달라져서 프로그램의 목적이 변해서

### 절차
- 폭넓게 쓰이는 변수라면 변수 캡슐화하기(6.6)를 고려한다
- 이름을 바꿀 변수를 참조하는 곳을 모두 찾아서 하나씩 변경
  - 다른 코드베이스에서 참조하는 변수는 외부에 공개된 변수이므로 이 리팩터링 적용 불가
  - 변수 값이 변하지 않는다면 다른 이름으로 복제본을 만들어서 하나씩 점진적으로 변경
- 테스트

## 6.8 매개변수 객체 만들기

| 리팩터링 전                                                                                                                                     | 리팩터링 후                                                                                                                   |
| ------------------------------------------------------------------------------------------------------------------------------------------ | ------------------------------------------------------------------------------------------------------------------------ |
| public Integer calculateTotal(Integer amount, Integer quantity, Integer itemPrice) {  <br>    return amount * quantity * itemPrice;  <br>} | public Integer calculateTotal(Order order) {  <br>    return order.amount * order.quantity * order.itemPrice;  <br>}<br> |
여러 데이터가 이 함수 저 함수 몰려다니는 경우가 있다. 이런 데이터 무리를 발견하면 데이터 구조 하나로 모아준다.

### 데이터 구조 묶기의 장점
- 데이터 구조로 묶으면 데이터 사이의 관계가 명확해진다
- 같은 데이터 구조를 사용하는 모든 함수가 원소를 참조할 때 항상 똑같은 이름을 사용해 일관성을 지킬 수 있다
- 코드를 근본적으로?? 바꿔준다
  - 데이터 구조에 담길 데이터에 적용되는 동작을 추출해서 함수로 만든다
  - 데이터와 동작을 합쳐 문제 영역을 간결하게 표현하는 새로운 추상개념을 만든다.
  - 코드의 개념적인 그림을 다시 그릴 수 있게 된다.
- 이런 장점은 매개변수 객체 만들기부터 시작한다

### 절차
- 적당한 데이터 구조가 없다면 새로 만든다
  - 클래스 만드는 것을 선호한다. 왜냐면 동작까지 함께 묶기 좋기 때문이다
- 테스트
- 함수 선언 바꾸기로 새 데이터 구조를 배개변수로 추가
- 테스트
- 함수 호출시 새로운 데이터 구조 인스턴스를 넘기도록 수정
- 테스트
- 기존 매개변수를 사용하던 코드를 새 데이터 구조의 원소를 사용하도록 변경
- 다 바꿨으면 기존 매개변수 제거
- 테스트

### 예시
```java
@DisplayName("온도가 min 보다 작거나 max 보다 크면 true 를 반환한다.")  
@Test  
void outsideRange() {  
    Station station = new Station("ZB1", defaultReadings());  
    OperatingPlan operatingPlan = new OperatingPlan(45, 60);  
  
    boolean result = readingsOutsideRange(  
            station, 
            operatingPlan.temperatureFloor(),
            operatingPlan.temperatureCeiling()  
    );  
  
    assertThat(result).isFalse();  
}  
  
public boolean readingsOutsideRange(Station station, Integer min, Integer max) {  
    List<Reading> readings = station.getReadings();  
    return readings.stream()  
            .anyMatch(r -> r.temp() < min || r.temp() > max);  
}
```

OperatingPlan 의 데이터 항목 두 개를 가져와서 readingsOutsideRange() 로 전달한다. 그리고 operatingPlan 은 범위의 시작과 끝 이름을 readingsOutsideRange() 와 다르게 표현한다. 이와 같이 범위(range) 라는 개념은 객체 하나로 묶어 표현하는 게 낫다.

묶은 데이터를 표현하는 클래스 선언
```java
public class NumberRange {  
    private Integer min;  
    private Integer max;  
  
    public NumberRange(Integer min, Integer max) {  
        this.min = min;  
        this.max = max;  
    } 
	public Integer min() {return min;}  
	public Integer max() {return max;}
}
```

값 객체로 만들 가능성이 높기 때문에 세터는 만들지 않는다. 새 객체를 만들었으니 readingsOutsideRange() 의 매개변수로 추가한다.
```java
boolean result = readingsOutsideRange(  
        station,  
        operatingPlan.temperatureFloor(),  
        operatingPlan.temperatureCeiling(),  
        null  
);

public boolean readingsOutsideRange(Station station, Integer min, Integer max, NumberRange range) {  
    List<Reading> readings = station.getReadings();  
    return readings.stream()  
            .anyMatch(r -> r.temp() < min || r.temp() > max);  
}
```

매개변수만 추가한 상태이니 테스트는 통과할 것이다. 이제 온도 범위를 객체 형태로 전달하도록 호출문을 바꾼다.
```java
boolean result = readingsOutsideRange(  
        station,  
        operatingPlan.temperatureFloor(),  
        operatingPlan.temperatureCeiling(),  
        new NumberRange(
	        operatingPlan.temperatureFloor(), operatingPlan.temperatureCeiling()
        )  
);
```

readingsOutsideRange() 에서 새 매개변수를 사용하지 않으니 테스트는 통과한다. 이제 새 매개변수를 사용하게 바꾸자.
```java
public boolean readingsOutsideRange(Station station, Integer min, Integer max, NumberRange range) {  
    List<Reading> readings = station.getReadings();  
    return readings.stream()  
            .anyMatch(r -> r.temp() < range.min() || r.temp() > range.max());  
}
```
기존 매개변수인 min, max 는 사용하지 않는 상태이므로 삭제해주면 된다. 그리고 호출문 부분도 매개변수로 전달하지 않게 수정하면 매개변수 객체 만들기가 끝난다.

>[!info] 진정한 값 객체로 거듭나기
>객체로 만들어두면 관련 동작들을 이 클래스로 옮길 수 있다는 장점이 생긴다.
>이 예시에선 온도가 허용 범위에 있는지 검사하는 메서드를 클래스에 추가할 수 있다.

## 6.9 여러 함수를 클래스로 묶기

| 리팩터링 전                                                                                                               | 리팩터링 후                                                                                                                           |
| -------------------------------------------------------------------------------------------------------------------- | -------------------------------------------------------------------------------------------------------------------------------- |
| funtion base(aReading) {...}<br>funtion taxableCharge(aReading) {...}<br>funtion calculateBaseCharge(aReading) {...} | class Reading {<br>    funtion base() {...}<br>    funtion taxableCharge() {...}<br>    funtion calculateBaseCharge() {...}<br>} |
공통 데이터를 중심으로 긴밀하게 엮여 작동하는 함수 무리를 발견하면 클래스 하나로 묶어보자.

### 장점
- 클래스로 묶으면 이 함수들이 공유하는 공통 환경을 명확하게 표현 가능
- 함수에 전달되는 인수를 줄여 객체 안에서의 함수 호출을 간결하게 만들기 가능
- 객체를 시스템의 다른 부분에 전달하기 위한 참조를 제공하는 것도 가능
- ==클라이언트가 핵심 객체의 데이터를 변경할 수 있고, 파생 객체들을 일관되게 관리할 수 있다.==

### 절차
- 함수들이 공유하는 공통 데이터 레코드를 캡슐화 한다
  - 공통 데이터가 레코드 구조로 묶여 있지 않다면 데이터를 하나로 묶는 레코드를 만든다
- 공통 레코드를 사용하는 함수 각각을 새 클래스로 옮긴다
  - 공통 레코드의 멤버는 함수 호출문의 인수 목록에서 제거한다
- 데이터를 조작하는 로직들은 함수로 추출해서 새 클래스로 옮긴다.

파생 데이터 모두를 필요한 시점에 계산하게 만들면, 저장된 데이터를 갱신하더라도 문제가 생길일이 없다. 프로그램의 다른 부분에서 갱신할 가능성이 있을 땐 클래스로 묶어두면 큰 도움이 된다.

## 6.10 여러 함수를 변환 함수로 묶기
소프트웨어는 데이터를 입력받아 정보를 도출한다. 이렇게 도출된 정보는 여러 곳에서 사용되는데, 이 정보가 사용되는 곳마다 같은 도출 로직이 반복되기도 한다. 이런 도출 작업들을 한곳으로 모으면 검색과 갱신을 일관된 장소에서 처리할 수 있고 로직 중복도 막을 수 있다.

변환함수
- 원본 데이터를 입력받아서 필요한 정보를 모두 도출한 뒤, 각각을 출력데이터의 필드에 넣어 반환한다.
- 도출 과정을 검토할 일이 생겼을 때 변환함수만 살펴보면 된다
- 데이터 구조와 이를 사용하는 함수가 근처에 위치시키면 발견하기 쉽다
- 변환 함수( 또는 클래스) 로 묶으면 함수를 쉽게 찾아 사용이 가능하다

### 클래스 묶기 vs 변환 함수 묶기
- 원본 데이터가 코드 안에서 갱신될 때는 클래스로 묶는 편이 낫다
- 변환 함수로 묶으면 가공한 데이터를 새로운 레코드에 저장하므로, 원본 데이터가 수정되면 일관성이 깨질 수 있다

### 절차
- 변환할 레코드를 입력받아 값을 그대로 반환하는 변환 함수를 만든다
- 묶을 함수 중 함수 하나를 골라 본문 코드를 변환 함수로 옮기고, 처리 결과를 레코드에 새 필드로 기록한다. 클라이언트 코드가 이 필드를 사용하도록 수정한다
- 테스트
- 나머지 관련 함수도 위 과정에 따라 처리한다

## 6.11 단계 쪼개기
서로 다른 두 대상을 한꺼번에 다루는 코드를 발견하면 각각의 별개 모듈로 나눈다. 코드를 수정할 때 하나에만 집중하기 위해서다. 모듈이 잘 분리됐다면 다른 모듈의 상세 내용은 기억하지 못해도 원하는 대로 수정할 수 있다.

컴파일러 예시
- 컴파일러는 텍스트를 입력 받아 실행 가능한 형태로 변환한다
- 텍스트를 토큰화, 파싱, 구문트리 생성, 목적 코드 생성
- 각 단계는 자신만의 문제에 집중하기 때문에 나머지 관계는 몰라도 된다.

### 절차
- 두번째 단계에 해당하는 코드를 독립함수로 추출
- 테스트
- 중간 데이터 구조를 만들어 앞에서 추출한 함수의 인수로 추가
- 테스트
- 추출한 두번째 단계 함수의 매개변수를 하나씩 검토한다. 그중 첫번째 단계에서 사용되는 것은 중간 데이터 구조로 옮긴다. 하나씩 옮길 때마다 테스트한다
- 첫번째 단계 코드를 함수로 추출하면서 중간 데이터 구조를 반환하도록 만든다

### 예시
```java
public Integer priceOrder(Product product, Integer quantity, ShippingMethod shippingMethod) {  
    int basePrice = product.basePrice() * quantity;  
    int discount = Math.max(quantity - product.discountThreshold(), 0)  
            * product.basePrice() * product.discountRate();  
    Integer shippingPerCase = (basePrice > shippingMethod.discountThreshold())  
            ? shippingMethod.discountedFee()  
            : shippingMethod.feePerCase();  
    Integer shippingCost = quantity * shippingPerCase;  
    Integer price = basePrice - discount + shippingCost;  
    return price;  
}
```
이 코드는 두 단계로 이루어졌다. 앞은 상품 정보를 이용해 결제 금액 중 상품 가격을 계산한다. 뒤의 코드는 배송 정보를 이용하여 결제 금액중 배송비를 계산한다.
나중에 상품 가격과 배송비 계산을 더 복잡하게 만드는 변경이 있다면 서로 독립적으로 처리할 수 있게 두 단계로 나누는 것이 좋다

먼저 배송비 계산 부분을 추출한다
```java
public Integer priceOrder(
	Product product, Integer quantity,
	ShippingMethod shippingMethod) 
{  
    int basePrice = product.basePrice() * quantity;  
    int discount = Math.max(quantity - product.discountThreshold(), 0)  
            * product.basePrice() * product.discountRate();  
    Integer price = applyShipping(basePrice,shippingMethod,quantity,discount);  
    return price;  
}  
  
public Integer applyShipping(  
        Integer basePrice, ShippingMethod shippingMethod,  
        Integer quantity, Integer discount)
{  
    Integer shippingPerCase = (basePrice > shippingMethod.discountThreshold())  
            ? shippingMethod.discountedFee()  
            : shippingMethod.feePerCase();  
    Integer shippingCost = quantity * shippingPerCase;  
    return basePrice - discount + shippingCost;  
}
```
두번째 단계에 필요한 데이터를 모두 개별 매개변수로 전달했다. 다음으로 첫 번째 단계와 두 번째 단계가 주고받을 중간 데이터 구조를 만든다.
basePrice 는 첫번째 단계 수행 후 만들어지는 값이다. 중간 데이터 구조로 옮기고 매개변수 목록에서 제거한다.
```java
public Integer priceOrder(  
        Product product, Integer quantity,  
        ShippingMethod shippingMethod)  
{  
    int basePrice = product.basePrice() * quantity;  
    int discount = Math.max(quantity - product.discountThreshold(), 0)  
            * product.basePrice() * product.discountRate();  
    PriceData priceData = new PriceData(basePrice);  
    Integer price = applyShipping(priceData,shippingMethod,quantity,discount);  
    return price;  
}  
  
public Integer applyShipping(  
        PriceData priceData, ShippingMethod shippingMethod,  
        Integer quantity, Integer discount)  
{  
    Integer shippingPerCase = (priceData.basePrice() > shippingMethod.discountThreshold())  
            ? shippingMethod.discountedFee()  
            : shippingMethod.feePerCase();  
    Integer shippingCost = quantity * shippingPerCase;  
    return priceData.basePrice() - discount + shippingCost;  
}
```
shippingMethod 매개변수는 첫 번째 단계에서 사용하지 않으므로 그대로 둔다. quantity 는 첫 번째 단계에서 사용하지만 거기서 생성된 것은 아니다. 그러니 매개변수로 놔둬도 되지만 중간 데이터 구조에 담는 것을 선호하기에 옮겨본다. discount 도 함께 옮긴다.
```java
public Integer priceOrder(  
        Product product, Integer quantity,  
        ShippingMethod shippingMethod)  
{  
    int basePrice = product.basePrice() * quantity;  
    int discount = Math.max(quantity - product.discountThreshold(), 0)  
            * product.basePrice() * product.discountRate();  
    PriceData priceData = new PriceData(basePrice, quantity, discount);  
    Integer price = applyShipping(priceData,shippingMethod);  
    return price;  
}  
  
public Integer applyShipping(  
        PriceData priceData, ShippingMethod shippingMethod)  
{  
    Integer shippingPerCase = (priceData.basePrice() > shippingMethod.discountThreshold())  
            ? shippingMethod.discountedFee()  
            : shippingMethod.feePerCase();  
    Integer shippingCost = priceData.quantity() * shippingPerCase;  
    return priceData.basePrice() - priceData.discount() + shippingCost;  
}
```
이제 매개변수를 처리했으니 첫 번째 단계 코드를 함수로 추출하고 이 데이터 구조를 반환하게 변경해보자
```java
public Integer priceOrder(  
        Product product, Integer quantity,  
        ShippingMethod shippingMethod)  
{  
    PriceData priceData = calculatePricingData(product, quantity);  
    return applyShipping(priceData,shippingMethod);
}  
  
public PriceData calculatePricingData(Product product, Integer quantity) {  
    int basePrice = product.basePrice() * quantity;  
    int discount = Math.max(quantity - product.discountThreshold(), 0)  
            * product.basePrice() * product.discountRate();  
    return new PriceData(basePrice, quantity, discount);  
}  
  
public Integer applyShipping(  
        PriceData priceData, ShippingMethod shippingMethod)  
{  
    Integer shippingPerCase = (priceData.basePrice() > shippingMethod.discountThreshold())  
            ? shippingMethod.discountedFee()  
            : shippingMethod.feePerCase();  
    Integer shippingCost = priceData.quantity() * shippingPerCase;  
    return priceData.basePrice() - priceData.discount() + shippingCost;  
}
```



