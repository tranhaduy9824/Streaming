package org.example.server.model;

import java.awt.Image;

public class User {
    private int id;
    private String username;
    private String password;
    private String address;

    private String fullname;
    private int phonenumber;
    private String email;
    private int cic;
    private String birthday;
    private boolean sex;
    private byte[] image;
    private boolean isOnline;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
    public User(String username, String fullname, int phonenumber, String email, int cic, String birthday, boolean sex,  String address,  byte[] image) {
        this.username = username;
        this.fullname = fullname;
        this.phonenumber = phonenumber;
        this.email = email;
        this.cic = cic;
        this.birthday = birthday;
        this.sex = sex;
        this.address = address;
        this.image = image;
    }

    public User(String username, String fullname, int phonenumber, String email, int cic, String birthday, boolean sex, String password, String address, byte[] image) {
        this.username = username;
        this.phonenumber = phonenumber;
        this.email = email;
        this.cic = cic;
        this.birthday = birthday;
        this.sex = sex;
        this.password = password;
        this.address = address;
        this.image = image;
    }


    public User(String username, String fullname, int phonenumber, String email, int cic, String birthday, boolean sex, String password, String address, byte[] image, boolean isOnline) {
        this.username = username;
        this.phonenumber = phonenumber;
        this.email = email;
        this.cic = cic;
        this.birthday = birthday;
        this.sex = sex;
        this.password = password;
        this.address = address;
        this.image = image;
        this.isOnline = isOnline;
    }

    public User(String username,String fullname,boolean isOnline){
        this.username = username;
        this.fullname = fullname;
        this.isOnline = isOnline;
    }

    public User(String username, String password, String fullname, byte[] image) {
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.image = image;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public int getPhonenumber() {
        return phonenumber;
    }

    public String getEmail() {
        return email;
    }

    public int getCic() {
        return cic;
    }

    public String getBirthday() {
        return birthday;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean GioiTinh) {
        this.sex = sex;
    }
    public boolean getSex() {
        return sex;
    }


    public byte[] getImage() {
        return image;
    }

    public boolean isIsOnline() {
        return isOnline;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public void setPhonenumber(int phonenumber) {
        this.phonenumber = phonenumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCic(int cic) {
        this.cic = cic;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }


    public void setImage(byte[] image) {
        this.image = image;
    }

    public void setIsOnline(boolean isOnline) {
        this.isOnline = isOnline;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public static class getUser {

        public getUser() {
        }
    }
}