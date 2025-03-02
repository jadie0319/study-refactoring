package com.jadie.chapter06;

public class InvoicePrinter {

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

    private void printDetails(Invoice invoice, Integer outstanding) {
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
