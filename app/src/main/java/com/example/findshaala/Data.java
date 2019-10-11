package com.example.findshaala;

public class Data {

    private String school_name;
    private String imgname;
    private String sid;
    private String address;
    private String contact;

    Data(String school_name, String address, String imgname, String sid, String contact) {
        this.school_name = school_name;
        this.address = address;
        this.imgname = imgname;
        this.sid = sid;
        this.contact=contact;
    }

    public String getSchool_name() {
        return school_name;
    }
    public String getSid(){ return sid; }
    public String getAddress() { return address; }
    public String getContact(){return contact;}
    public String getImgname() { return imgname; }
}
