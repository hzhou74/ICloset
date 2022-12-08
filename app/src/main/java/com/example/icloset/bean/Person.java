package com.example.icloset.bean;


public class Person {

    public String account;
    public String password;
    public String head;

    public Person() {
    }

    public Person(String account, String password) {
        this.account = account;
        this.password = password;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
