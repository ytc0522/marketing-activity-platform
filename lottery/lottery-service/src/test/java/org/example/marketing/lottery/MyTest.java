package org.example.marketing.lottery;

import org.junit.Test;

import java.math.BigDecimal;

public class MyTest
{

    @Test
    public void testBigDecimal() {
        BigDecimal bigDecimal = new BigDecimal("0.000234");
        double v = bigDecimal.doubleValue();
        System.out.println("v = " + v);


        BigDecimal newBigDecimal = new BigDecimal("0.0002340");
        Object result = newBigDecimal.equals(bigDecimal);
        System.out.println("result = " + result);

    }
}
