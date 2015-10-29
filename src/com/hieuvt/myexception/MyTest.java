package com.hieuvt.myexception;

/**
 * Created by hieu.vutrong on 5/21/2015.
 */
public class MyTest {
    public static void testException(String condition) throws MyException {
        if (condition == null) {
            throw new MyException("String val is null");
        }
    }

    public static void main (String[] args) {
        MyTest myTest = new MyTest();
        try {
            myTest.testException(null);
        } catch (MyException e) {
//            e.printStackTrace();
            System.out.println("Inside catch block: "+e.getMessage());
        }
    }
}
