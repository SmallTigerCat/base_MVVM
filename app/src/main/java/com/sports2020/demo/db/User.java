package com.sports2020.demo.db;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "users")
public class User {
    @NotNull
    @PrimaryKey
    int id;
    int classId;
    String className;
    String schoolName;
    String subjectName;
    String accountName;
    String userName;
    int active;

    @Ignore
    public User() {
        this(-1, -1, "", "", "", "", "", 0);
    }

    public User(@NotNull int id, int classId, String className, String schoolName, String subjectName, String accountName, String userName, int active) {
        this.id = id;
        this.classId = classId;
        this.className = className;
        this.schoolName = schoolName;
        this.subjectName = subjectName;
        this.accountName = accountName;
        this.userName = userName;
        this.active = active;
    }

    @NotNull
    public int getId() {
        return id;
    }

    public void setId(@NotNull int id) {
        this.id = id;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", classId=" + classId +
                ", className='" + className + '\'' +
                ", schoolName='" + schoolName + '\'' +
                ", subjectName='" + subjectName + '\'' +
                ", accountName='" + accountName + '\'' +
                ", userName='" + userName + '\'' +
                ", active=" + active +
                '}';
    }
}
