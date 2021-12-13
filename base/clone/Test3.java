package com.lenovo.lcp.core.voiceservice.service;

import java.io.IOException;

public class Test3 {

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        Person person = new Person(21, "LZN");

        Person p2 = (Person) person.deepClone();


        System.out.println(person == p2);
        System.out.println(person.getAddress().hashCode());
        System.out.println(p2.getAddress().hashCode());

    }

}
