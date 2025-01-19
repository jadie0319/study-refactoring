package chapter01;

import java.util.List;

public record Invoices(
        String customer,
        List<Performance> performances
) {
}
