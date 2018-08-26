package com.wangzy.domain;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class User {
    @Id(autoincrement = true)
    private Long id;
    private String name;
    private int age;
    private String intro;
    private int score;
    private int level;
    private int level2;

    @Generated(hash = 586692638)
    public User() {
    }

    @Generated(hash = 2100652449)
    public User(Long id, String name, int age, String intro, int score, int level,
            int level2) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.intro = intro;
        this.score = score;
        this.level = level;
        this.level2 = level2;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getLevel2() {
        return level2;
    }

    public void setLevel2(int level2) {
        this.level2 = level2;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", intro='" + intro + '\'' +
                ", score=" + score +
                ", level=" + level +
                ", level2=" + level2 +
                '}';
    }
}
