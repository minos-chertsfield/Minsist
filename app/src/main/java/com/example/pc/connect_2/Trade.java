package com.example.pc.connect_2;

/**
 * Created by PC on 2017/11/22.
 */

/*
订单类
 */

public class Trade {
    private String id;
    private String userid;
    private String trainerid;
    private int num;
    private int type;
    private long hopetime;
    private String hopeplace;
    private double cost;
    private int state;

    public Trade(int type, String id, String userid, String trainerid, int num, long hopetime, String hopeplace, double cost, int state)
    {
        super();
        this.type = type;
        this.id = id;
        this.userid = userid;
        this.trainerid = trainerid;
        this.num = num;
        this.hopetime = hopetime;
        this.hopeplace = hopeplace;
        this.cost  = cost;
        this.state = state;
    }

    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getUserid() {
        return userid;
    }
    public void setUserid(String userid) {
        this.userid = userid;
    }
    public String getTrainerid() {
        return trainerid;
    }
    public void setTrainerid(String trainerid) {
        this.trainerid = trainerid;
    }
    public int getNum() {
        return num;
    }
    public void setNum(int num) {
        this.num = num;
    }
    public long getHopetime() {
        return hopetime;
    }
    public void setHopetime(long hopetime) {
        this.hopetime = hopetime;
    }
    public String getHopeplace() {
        return hopeplace;
    }
    public void setHopeplace(String hopeplace) {
        this.hopeplace = hopeplace;
    }
    public double getCost() {
        return cost;
    }
    public void setCost(double cost) {
        this.cost = cost;
    }
    public int getState() {
        return state;
    }
    public void setState(int state) {
        this.state = state;
    }
}
