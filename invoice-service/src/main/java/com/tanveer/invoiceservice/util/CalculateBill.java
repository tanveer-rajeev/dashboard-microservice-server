package com.tanveer.invoiceservice.util;

public class CalculateBill {

    public static double percentage(double amount, double percentage) {
        return (percentage * amount) / 100;
    }

}
