package org.example.marketing.activity.order.repository.mapper;

import org.junit.Test;

public class Mytest {


    @Test
    public void test_hash() {

        int n = 1;

        int hashCode = 2;

        int index = (n - 1) & hashCode;

        System.out.println("index = " + index);

    }

    @Test
    public void test_tongyu() {
        long order1 = 12345;
        long order2 = 23455;

        int divisor = 4;

        long result1 = order1 % divisor;
        System.out.println("result1 = " + result1);
        long result2 = order2 % divisor;
        System.out.println("result2 = " + result2);

    }


}
