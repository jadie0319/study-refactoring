# **7. 캡슐화**

모듈을 분리하는 가장 중요한 기준은 시스템에서 각 모듈이 자신을 제외한 다른 부분에 드러내지 않아야 할 비밀을 얼마나 잘 숨기느냐에 있다.

## 7.1 레코드 캡슐화하기

### 레코드 특징

레코드 장점

- 연관된 데이터를 직관적인 방식으로 묶을 수 있어서 각각 취급할 때보다 훨씬 의미 있는 단위로 전달할 수 있게 해준다
  레코드 단점
- 계산해서 얻을 수 있는 값과 그렇지 않은 값을 명확히 구분해 저장해야 한다
- 가변 데이터를 저장하는 용도로는 레코드보다 객체를 선호하는 편이다.

레코드 구조 특징

- 필드 이름을 노출하는 형태, 필드를 외부로부터 숨겨서 원하는 이름을 쓰는 형태
    - 후자는 주로 해시, 맵, 해시맵, 딕셔너리 등의 이름으로 제공한다

JSON 이나 XML 같은 포맷으로 직렬화 할 때가 있다. 이런 구조 역시 캡슐화할 수 있는데, 그러면 나중에 포맷을 바꾸거나 추적하기 어려운 데이터를 수정하기가 수월해진다.

### 절차

- 레코드를 담은 변수를 캡슐화한다
    - 레코드를 캡슐화하는 함수의 이름은 검색하기 쉽게 지어준다
- 레코드를 감싼 단순한 클래스로 해당 변수의 내용을 교체한다. 이 클래스에 원본 레코드를 반환하는 접근자를 정의하고, 변수를 캡슐화하는 함수들이 이 접근자를 사용하도록 수정
- 테스트
- 원본 레코드 대신 새로 정의한 클래스 타입의 객체를 반환하는 함수들을 새로 만든다
- 레코드를 반환하는 예전 함수를 사용하는 코드를 새 함수를 사용하도록 바꾼다. 필드에 접근할 때는 객체의 접근자를 사용.
- 클래스에서 원본 데이터를 반환하는 접근자와 원본 레코드를 반환하는 함수를 제거
- 테스트

캡슐화에서는 값을 수정하는 부분을 명확하게 드러내고 한 곳에 모아두는 일이 굉장히 중요하다.

## 7.2 컬렉션 캡슐화하기

| 리팩터링 전 | 리팩터링 후 |
| --- | --- |
| public class Person {
private List<Course> courses;
public List<Course> getCourse() {
return courses;
}
public void setCourse(List<Course> aList) {
this.courses = aList;
}
} | public class Person {
private List<Course> courses;
public List<Course> getCourses() {
return List.copyOf(courses);
}
public void addCourse(Course aCourse) {
courses.add(aCourse);
}
public void removeCourse(Course aCourse) {
courses.remove(aCourse);
}
} |

가변 데이터를 캡슐화하면 데이터 구조가 언제 어떻게 변경되는지 파악하기 쉽다. 그래서 필요한 시점에 데이터 구조를 변경하기도 쉬워진다.

캡슐화를 적극 권장하는데 컬렉션을 다룰 때는 실수가 발생하곤 한다. 예를 들어 컬렉션 변수로의 접근을 캡슐화하면서 게터가 컬렉션 자체를 반환하도록 한다면, 그 컬렉션을 감싼 클래스가 눈치채지 못하는 상태에서 컬렉션의 원소들이 바뀌어버릴 수 있다.

이를 방지하기 위해 add(), remove() 라는 이름의 컬렉션 변경자 메서드를 추가한다. 그리고 게터가 원본 컬렉션을 반환하지 않게 만들어서 실수로 컬렉션을 바꿀 가능성을 차단하는게 낫다.

중요한 것은 코드베이스에서 일관성을 주는 것이다. 컬렉션 접근 함수의 동작 방식을 통일해야 문제가 발생하지 않는다.

### 절차

- 변수 캡슐화하기(6.8)
- 컬렉션에 원소 추가/제거 하는 함수 추가
    - 컬렉션을 통째로 바꾸는 세터는 제거한다. 제거할 수 없다면 인수로 받은 컬렉션을 복제해 저장하도록 만든다.
- 정적 검사 수행
- 컬렉션을 참조하는 부분을 찾아 컬렉션의 변경자를 호출하는 코드가 추가/제거 함수를 호출하도록 수정한다
- 테스트
- 컬렉션 게터를 수정해서 원본 내용을 수정할 수 없는 읽기전용 프락시나 복제본을 반환하게 한다
- 테스트

컬렉션에 대해서는 복제본을 만드는 편이, 예상치 못한 수정이 촉발하는 오류를 디버깅하는 것 보다는 낫다.

## 7.3 기본형을 객체로 바꾸기

| 리팩터링 전 | 리팩터링 후 |
| --- | --- |
| public boolean isHigherThan(List<Order> orders) {
return orders.stream()
.anyMatch(o -> "high".equals(o.priority) || "rush".equals(o.priority));
} | public boolean isHigherThan(List<Order> orders) {
return orders.stream()
.anyMatch(o -> o.priority.higherThan(new Priority("normal")));
} |

처음엔 단순한 데이터 항목을 표현했지만 단순해지지 않는 경우가 있다. 예를 들어 전화번호를 문자열로 표현했는데 나중에 포매팅이나 지역 코드 추출 같은 특별한 동작이 필요해질 수 있다. 이런 로직들로 중복 코드가 늘어나 코드가 복잡해질 수 있다.

단순한 출력 이상의 기능이 필요해지는 순간 그 데이터를 표현하는 전용 클래스를 정의한다. 시작은 단순히 데이터를 감싸는 형태이지만, 나중에 특별한 동작이 필요해지면 이 클래스에 추가하면 되니 프로그램이 커질수록 유용한 도구가 된다.

### 절차

- 변수를 캡슐화하지 않았다면 캡슐화를 먼저 한다
- 단순한 값 클래스를 만든다.
- 값 클래스의 인스턴스를 새로 만들어서 필드에 저장하도록 세터를 수정한다.
- 새로 만든 클래스의 게터를 호출한 결과를 반환하도록 게터를 수정한다
- 테스트
- 함수 이름을 바꾸면 동작을 더 잘 드러낼 수 있는지 검토한다

### 예시

캡슐화 한다

```java
public class Order {
    private String priority;
    public String getPriority() {
        return priority;
    }
    public void setPriority(String aString) {
        this.priority = aString;
    }
}

클라이언트에서 사용
public long highPriorityCount(List<Order> orders) {
    long highPriorityCount = orders.stream()
            .filter(o -> "high".equals(o.getPriority()) || "rush".equals(o.getPriority()))
            .count();
    return highPriorityCount;
}

```

우선순위를 표현하는 값 클래스 Priority 를 만든다. 이 클래스는 표현할 값을 받는 생성자와 그 값을 문자열로 반환하는 변환 함수로 구성된다.

```java
public class Priority {
    private String priority;
    public Priority(String priority) {
        this.priority = priority;
    }
    @Override
    public String toString() {
        return priority;
    }
//        return "high".equals(priority) || "rush".equals(o.priority);
//    }
}

```

Order 에서 Priority 를 사용하도록 접근자들을 수정한다. getPriority() 는 Priority 를 반환하는게 아니라 문자열을 반환하므로 메서드명을 변경해주는게 좋아보인다.

```java
public class Order {
    private Priority priority;
    public String priorityString() {
        return priority.toString();
    }
    public void priority(String aString) {
        this.priority = new Priority(aString);
    }
}

```

여기서부터는 추가 리팩터링 이다. Priority 객체를 제공하는 게터를 Order 클래스에 구현해보자

```java
public class Order {
    private Priority priority;
    public String priorityString() {
        return priority.toString();
    }
    public void priority(String aString) {
        this.priority = new Priority(aString);
    }
    public Priority priority() {
        return priority;
    }
}

long highPriorityCount = orders.stream()
        .filter(o -> "high".equals(o.priority().toString()) || "rush".equals(o.priority().toString()))
        .count();

```

이렇게 o.priority() 를 조회해 사용해도 되지만 Order 를 통해 Priority 의 메서드를 호출하는 식으로 변경해볼 수도 있을 것 같다

```java
long highPriorityCount = orders.stream()
        .filter(Order::isLegalPriority)
        .count();

public class Order {
    private Priority priority;
    public boolean isLegalPriority() {
        return priority.isLegalPriority();
    }
}

public class Priority {
    private String priority;
    public Priority(String value) {
        if (isLegalValue(value)) {
            this.priority = value;
        } else {
            throw new RuntimeException("유효하지 않은 우선순위 입니다.");
        }
    }

    private boolean isLegalValue(String value) {
        return Stream.of("low", "normal", "high", "rush")
                .anyMatch( v -> v.equals(value));
    }

    public boolean isLegalPriority() {
        return isLegalValue(priority);
    }
//        return "high".equals(priority) || "rush".equals(o.priority);
//    }
}

```

이처럼 값을 검증하는 로직을 추가할 수도 있고, 다른 여러 동작을 추가할 수 있다. 그렇게 되면 클라이언트 코드를 더 의미있게 작성할 수 있다.

## 7.4 임시 변수를 질의 함수로 바꾸기

| 리팩터링 전 | 리팩터링 후 |
| --- | --- |
| int basePrice = this.quantity * this.item;
if (basePrice > 1000) {
return basePrice * 0.95;
} else {
return basePrice * 0.98;
} | if (basePrice() > 1000) {
return basePrice() * 0.95;
} else {
return basePrice() * 0.98;
}

public Integer basePrice() {return quantity * item;} |

함수 안에서 어떤 코드의 결과값을 뒤에서 참조할 목적으로 임시변수를 쓰기도 한다. 임시 변수를 사용하면 값을 계산하는 코드가 반복되는 걸 줄이고 변수의 이름을 통해 값의 의미를 설명할 수 있어서 유용하다. 그런데 한 걸음 더 나아가 아예 함수로 만들어 사용하는 편이 나을때도 있다.

### 장점

- 비슷한 계산을 수행하는 다른 함수에서 재사용 가능
- 함수간 경계가 분명해진다.

### 절차

- 변수가 사용되기 전 값이 결정되는지, 변수를 사용할 때마다 계산 로직이 매번 다른 결과를 내는지 확인한다
- 읽기전용으로 만들 수 있는 변수는 읽기전용으로 변경
- 테스트
- 변수 대입문을 함수로 추출
- 테스트
- 변수 인라인하기로 임시 변수 제거

### 예시

```java
public double price() {
    final Integer basePrice = this.quantity * this.item;
    double discountFactor = 0.98;

    if (basePrice > 1000) {
        discountFactor -= 0.03;
    }
    return basePrice * discountFactor;
}

```

이 코드에서 임시변수인 basePrice 와 discountFactor 를 메서드로 바꿔보자. basePrice 는 재할당이 안되게 final 을 붙였다. basePrice 대입문의 우변을 게터로 추출한다. 그리고 인라인 까지 진행해보겠다.

```java
public double price() {
    double discountFactor = 0.98;
    if (basePrice() > 1000) {
        discountFactor -= 0.03;
    }
    return basePrice() * discountFactor;
}

public Integer basePrice() {return quantity * item;}

```

discountFactor 도 같은 순서로 처리한다.
읽기전용 변수로 변경 -> 함수 추출 -> 테스트 -> 인라인

```java
public double price() {
    return basePrice() * discountFactor();
}

public Integer basePrice() {return quantity * item;}
public double discountFactor() {
    double discountFactor = 0.98;
    if (basePrice() > 1000) {
        discountFactor -= 0.03;
    }
    return discountFactor;
}

```

## 7.5 클래스 추출하기

클래스는 명확하게 추상화하고 소수의 역할만 처리해야 한다. 하지만 실무에서는 시간이 지나면서 클래스가 비대해지곤 한다. 역할이 많아지고 새끼를 치면서 클래스가 복잡해진다.
메서드와 데이터가 너무 많은 클래스는 이해하기가 쉽지 않으니 잘 살펴보고 분리해야 한다. 특히

클래스 추출 타이밍

- 메서드와 데이터가 너무 많은 클래스
- 일부 데이터와 메서드를 따로 묶을 수 있다
- 함께 변경되는 일이 많거나 서로 의존하는 데이터들도 분리한다
- 특정 필드나 메서드를 제거했을 때 논리적으로 문제가 없다면 분리할 수 없다는 뜻이다.

### 절차

- 클래스의 역할을 분리할 방법을 정한다
- 분리될 역할을 담당할 클래스를 만든다
- 원래 클래스의 생성자에서 새로운 클래스의 인스턴스를 생성하여 필드에 저장한다
- 분리될 역할에 필요한 필드들을 새 클래스로 옮긴다
- 테스트
- 메서드를 새 클래스로 옮긴다. 저수준 메서드, 즉 다른 메서드를 호출하기보다는 호출을 당하는 일이 많은 메서드부터 옮긴다
- 테스트
- 양쪽 클래스의 인터페이스를 살펴보면서 불필요 메서드를 지우고 이름도 어울리게 바꾼다
- 새 클래스를 외부로 노출할지 정한다.

## 7.6 클래스 인라인하기

클래스 추출하기를 거꾸로 돌리는 리팩터링이다. 더이상 제 역할을 못해서 그대로 두면 안되는 클래스는 인라인한다. 역할을 옮기는 리팩터링을 하고나니 클래스에 남은 역할이 거의 없을 때 이러한 일이 자주 생긴다.

### 절차

- 각 public 메서드에 대응하는 메서드들을 타깃 클래스에 생성한다. 이 메서드들은 단순히 작업을 소스 클래스로 위임해야 한다
- 소스 클래스 메서드를 사용하는 코드를 모두 타깃 클래스의 위임 메서드를 사용하도록 바꾼다.
- 테스트
- 소스 클래스의 메서드와 필드를 모두 타깃 클래스로 옮긴다.
- 테스트
- 소스 클래스 삭제

## 7.7 위임 숨기기

모듈화 설계를 제대로 하는 핵심은 캡슐화다. 캡슐화가 잘 되어 있다면 무언가를 변경해야 할 때 함께 고려해야 할 모듈 수가 적어져서 코드를 변경하기가 훨씬 쉬워진다.
캡슐화를 처음 배울 땐 필드를 숨기는 것이라고 배운다. 그러다 경험이 쌓이면서 캡슐화의 역할이 그보다 많다는 사실을 깨닫는다.
위임 객체의 인터페이스가 바뀌면 이 인터페이스를 사용하는 모든 클라이언트 코드를 수정해야 한다. 이러한 의존성을 없애려면 서버 자체에 위임 메서드를 만들어서 위임 객체의 존재를 숨겨야 한다. 위임 객체가 수정되더라도 서버 코드만 고치면 되며, 클라이언트는 영향을 받지 않는다.

### 절차

- 위임 객체의 각 메서드에 해당하는 위임 메서드를 서버에 생성한다
- 클라이언트가 위임 객체 대신 서버를 호출하도록 수정.
- 테스트
- 모두 수정했다면, 서버로부터 위임 객체를 얻는 접근자를 제거한다
- 테스트

## 7.8 중개자 제거하기

클라이언트가 위임 객체의 또 다른 기능을 사용하고 싶을 때마다 서버에 위임 메서드를 추가해야 하는데, 이렇게 기능을 추가하다 보면 단순히 전달만 하는 위임 메서드들이 점점 성가셔진다. 이런 클래스는 그저 중개자 역할로 전락하여, 차라리 클라이언트가 위임 객체를 직접 호출하는게 나을 수 있다.

### 절차

- 위임 객체를 얻는 게터를 만든다
- 위임 메서드를 호출하는 클라이언트가 모두 이 게터를 거치도록 수정
- 테스트
- 모두 수정하면 위임 메서드 삭제

## 7.9 알고리즘 교체하기

알고리즘은 더 적절한, 간단한 방법을 찾아내면 복잡한 기존 코드를 고칠 수 있다. 리팩터링하면 복잡한 대상을 단순한 단위로 나눌 수 있지만 때로는 알고리즘 전체를 걷어내고 간결한 알고리즘으로 바꿔야 할 때가 있다. 문제를 더 확실히 이해하고 쉽게 해결하는 방법을 발견했을 때 이렇게 한다.

이 작업을 하려면 메서드를 가능한 한 잘게 나눴는지 확인해야 한다. 거대하고 복잡한 알고리즘을 교체하기 어려우니 알고리즘을 간소화하는 작업부터 해야 교체가 쉽다

### 절차

- 교체할 코드를 함수 하나에 모은다
- 이 함수만을 이용해 동작을 검증하는 테스트 작성
- 대체 알고리즘 준비
- 정적 검사 수행
- 기존 알고리즘과 새 알고리즘의 결과를 비교하는 테스트 작성
- 두 결과가 같다면 리팩터링 끝
- 다르다면 기존 알고리즘을 참고해 새 알고리즘을 테스트하고 디버깅한다.
