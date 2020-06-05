package com.company;

import java.util.ArrayList;

public class testMain {
    public static void main(String[] args) {
        Calculator c = new CalculatorImpl();
        System.out.println(c.evaluate("(1+38)*4-5")); // Результат: 151
                c = new CalculatorImpl();
        System.out.println(c.evaluate("7*6/2+8")); // Результат: 29
        c = new CalculatorImpl();
        System.out.println(c.evaluate("-12)1//(")); // Результат: null

    }
}
