package com.example.pc.connect_2;


/**
 * Created by PC on 2017/11/9.
 */

/*
用户类
 */

public class User {
    public User(String id, String password, String name, String telephone, long birthday, String identity,
                double money, String mac) {
        super();
        this.id = id;
        this.password = password;
        this.name = name;
        this.telephone = telephone;
        this.birthday = birthday;
        this.identity = identity;
        this.money = money;
        this.mac = mac;
    }
    private String id;
    private String password;
    private String name;
    private String telephone;
    private long birthday;
    private String identity;
    private double money;
    private String mac;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getTelephone() {
        return telephone;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    public long getBirthday() {
        return birthday;
    }
    public void setBirthday(long birthday) {
        this.birthday = birthday;
    }
    public String getIdentity() {
        return identity;
    }
    public void setIdentity(String identity) {
        this.identity = identity;
    }
    public double getMoney() {
        return money;
    }
    public void setMoney(double money) {
        this.money = money;
    }
}
