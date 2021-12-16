package com.lenovo.lcp.core.voiceservice.service;

public class Test2 {


    public static void main(String[] args) throws CloneNotSupportedException {

        Person person = new Person(21, "LZN");

        Person p2 = (Person) person.clone();


        System.out.println(person == p2);
        System.out.println(person.getAddress().hashCode());
        System.out.println(p2.getAddress().hashCode());

    }
}
