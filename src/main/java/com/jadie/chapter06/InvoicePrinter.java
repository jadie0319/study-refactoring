package com.jadie.chapter06;

public class InvoicePrinter {

    public void printOwing(Invoice invoice) {
        printBanner();
        var outstanding = calculateOutstanding();

        // 세부 사항 출력
        System.out.println("고객명 : " + invoice.getCustomer());
        System.out.println("채무액 : " + outstanding);
    }

    private Integer calculateOutstanding() {
        return 1;
    }

    public void printBanner() {
        System.out.println("Print banner");
    }
}
