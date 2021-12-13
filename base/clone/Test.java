package com.lenovo.lcp.core.voiceservice.service;

import org.checkerframework.checker.units.qual.A;

public class Test {

    static class Person implements Cloneable{

        private int age;
        private String name;
        private Address address;


        @Override
        protected Object clone() throws CloneNotSupportedException {
            return super.clone();
        }

        public Person(int age,String name){
            this.age  = age;
            this.name = name;
            this.address = new Address();
        }

        public Address getAddress() {
            return address;
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }


    static class Address{
        private String city;

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }
    }

    public static void main(String[] args) throws CloneNotSupportedException {

        Person person = new Person(21, "LZN");

        Person p2 = (Person) person.clone();


        System.out.println(person == p2);
        System.out.println(person.getAddress().hashCode());
        System.out.println(p2.getAddress().hashCode());
    }



}
