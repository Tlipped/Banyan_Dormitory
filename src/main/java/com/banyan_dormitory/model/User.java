package com.banyan_dormitory.model;

import java.util.Objects;

//User类
public class User {
    private String id;
    private String password;
    private String school;
    private String score;
    private String room;
    private String bed;
    private String name;
    private String user_id;
    private String phone_number;

    //getter和setter****************************
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

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getBed() {
        return bed;
    }

    public void setBed(String bed) {
        this.bed = bed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }
    //getter和setter*******************************

    // 默认构造函数
    public User() {
    }

    // 全参构造函数
    public User(String id, String password, String school, String score, String room, String bed, String name, String user_id, String phone_number) {
        this.id = id;
        this.password = password;
        this.school = school;
        this.score = score;
        this.room = room;
        this.bed = bed;
        this.name=name;
        this.user_id=user_id;
        this.phone_number=phone_number;
    }

    // Getter 和 Setter 方法
    // ...

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                Objects.equals(password, user.password) &&
                Objects.equals(school, user.school) &&
                Objects.equals(score, user.score) &&
                Objects.equals(room, user.room) &&
                Objects.equals(bed, user.bed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, password, school, score, room, bed);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", password='" + password + '\'' +
                ", school='" + school + '\'' +
                ", score='" + score + '\'' +
                ", room='" + room + '\'' +
                ", bed='" + bed + '\'' +
                '}';
    }
}
