package com.tw;

import java.util.Map;

public class StudentInfo {
    private String name;
    private Integer number;
    private Map<String, Integer> grades;
    private double average = 0;
    private Integer total = 0;

    public String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name.trim();
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = Integer.parseInt(number.trim());
    }

    public Map<String, Integer> getGrades() {
        return grades;
    }

    public void setGrades(Map<String, Integer> grades) {
        this.grades = grades;
        compute();
    }

    public double getAverage() {
        return average;
    }

    public Integer getTotal() {
        return total;
    }

    private void compute() {
        if (grades != null && grades.size() != 0) {
            int[] sum = {0};
            grades.forEach((k, v) -> {
                sum[0] += v;
            });
            total = sum[0];
            average = (double) sum[0] / grades.size();
        }
    }
}
