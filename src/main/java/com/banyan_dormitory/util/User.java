package com.banyan_dormitory.util;

import java.util.Objects;

//User类
public class User {
    private int id;
    private String password;
    private String school;
    private String score;
    private String room;
    private String bed;

    // 默认构造函数
    public User() {
    }

    // 全参构造函数
    public User(int id, String password, String school, String score, String room, String bed) {
        this.id = id;
        this.password = password;
        this.school = school;
        this.score = score;
        this.room = room;
        this.bed = bed;
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
