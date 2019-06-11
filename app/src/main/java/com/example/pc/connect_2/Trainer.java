package com.example.pc.connect_2;

/**
 * Created by PC on 2017/11/10.
 */


/*
教练类
 */

public class Trainer {
    private String id;
    private String password;
    private String name;
    private String telephone;
    private long birthday;
    private String identity;
    private int point;
    private boolean ifidentify;
    private String mac;
    public Trainer(){

    }
    public Trainer(String id, String password, String name, String telephone, long birthday, String identity, int point,
                   boolean ifidentify, String mac) {
        super();
        this.id = id;
        this.password = password;
        this.name = name;
        this.telephone = telephone;
        this.birthday = birthday;
        this.identity = identity;
        this.point = point;
        this.ifidentify = ifidentify;
        this.mac = mac;
    }
    public String getMac() {
        return mac;
    }
    public void setMac(String mac) {
        this.mac = mac;
    }
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
    public int getPoint() {
        return point;
    }
    public void setPoint(int point) {
        this.point = point;
    }
    public boolean isIfidentify() {
        return ifidentify;
    }
    public void setIfidentify(boolean ifidentify) {
        this.ifidentify = ifidentify;
    }
}
