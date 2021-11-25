package com.test.annotion;

public class UserTest {

    @Before
    public void before(){
        System.out.println("mock junit test before method");
    }
    @After
    public void after(){
        System.out.println("mock junit test after method");
    }
    @Test
    public void test(){
        System.out.println("mock junit test  method");
    }

}
