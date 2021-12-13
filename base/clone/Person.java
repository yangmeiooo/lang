package com.lenovo.lcp.core.voiceservice.service;

import java.io.*;

public class Person implements Cloneable, Serializable {

    private int age;
    private String name;
    private Address address;


    @Override
    protected Object clone() throws CloneNotSupportedException {
        Person person = (Person)super.clone();
        person.address = (Address)address.clone();
        return person;
    }

    public Object deepClone() throws IOException, ClassNotFoundException {

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);

        oos.writeObject(this);

        ByteArrayInputStream ios = new ByteArrayInputStream(bos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(ios);


        return ois.readObject();
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

