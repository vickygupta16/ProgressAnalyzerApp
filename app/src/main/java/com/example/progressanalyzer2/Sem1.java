package com.example.progressanalyzer2;

import java.util.HashMap;

public class Sem1
{
    private String studentid;
    private HashMap<String,Integer> theory;
    private HashMap<String,Integer> practical;
    private Float SGPA;
    private String grade;

    public String getStudentid() {
        return studentid;
    }

    public void setStudentid(String studentid) {
        this.studentid = studentid;
    }

    public HashMap<String, Integer> getTheory() {
        return theory;
    }

    public void setTheory(HashMap<String, Integer> theory) {
        this.theory = theory;
    }

    public HashMap<String, Integer> getPractical() {
        return practical;
    }

    public void setPractical(HashMap<String, Integer> practical) {
        this.practical = practical;
    }

    public Float getSGPA() {
        return SGPA;
    }

    public void setSGPA(Float SGPA) {
        this.SGPA = SGPA;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}