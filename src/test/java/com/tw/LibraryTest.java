package com.tw;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.LinkedList;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LibraryTest {
    private ByteArrayOutputStream outContent;

    @Before
    public void setUp() throws Exception {
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        Library.report.clear();
    }

    private String systemOut() {
        return outContent.toString();
    }

    @Test
    public void testjudgeStudentInfo() {
        String s1 = "张三,123,数学:75,语文:95,英语:80,编程:80";
        assertTrue(Library.addStudentInfo(Arrays.asList(s1.split(","))));
        assertTrue(systemOut().endsWith("学生张三的成绩被添加\n"));

        String s2 = "李四,125,数学:85,语文:80,英语:70,编程:90";
        assertTrue(Library.addStudentInfo(Arrays.asList(s2.split(","))));
        assertTrue(systemOut().endsWith("学生李四的成绩被添加\n"));

        String s3 = "张三,121,数学:75,语文:95,英语:80";
        assertTrue(!Library.addStudentInfo(Arrays.asList(s3.split(","))));

        String s4 = "张三,学号,数学:75,语文:95,英语:80,编程:80";
        assertTrue(!Library.addStudentInfo(Arrays.asList(s4.split(","))));

        String s5 = "张三,122,数学:75,语文:95,英语:130,编程:80";
        assertTrue(!Library.addStudentInfo(Arrays.asList(s5.split(","))));

    }

    @Test
    public void testGenerateGrades() {
        String s1 = "张三";
        assertTrue(!Library.generateGrades(Arrays.asList(s1.split(","))));

        String s2 = " 123";
        assertTrue(!Library.generateGrades(Arrays.asList(s2.split(","))));

        String s3 = "123";
        assertTrue(Library.generateGrades(Arrays.asList(s3.split(","))));

        String s4 = "123,ABC";
        assertTrue(!Library.generateGrades(Arrays.asList(s4.split(","))));

        String s5 = "123,125";
        assertTrue(Library.generateGrades(Arrays.asList(s5.split(","))));
    }

    @Test
    public void testReport() {
        String s1 = "张三,123,数学:75,语文:95,英语:80,编程:80";
        assertTrue(Library.addStudentInfo(Arrays.asList(s1.split(","))));
        assertTrue(systemOut().endsWith("学生张三的成绩被添加\n"));
        String s2 = "123,125";
        assertTrue(Library.generateGrades(Arrays.asList(s2.split(","))));
        assertEquals("学生张三的成绩被添加\n" +
                "成绩单\n" +
                "姓名|数学|语文|英语|编程|平均分|总分\n" +
                "========================\n" +
                "张三|75|95|80|80|82.5|330\n" +
                "========================\n" +
                "全班总分平均数：330\n" +
                "全班总分中位数：330\n", systemOut());
    }

    @Test
    public void testReportTwoPeople() {
        String s1 = "张三,123,数学:75,语文:95,英语:80,编程:80";
        String s2 = "李四,125,数学:85,语文:80,英语:70,编程:90";
        assertTrue(Library.addStudentInfo(Arrays.asList(s1.split(","))));
        assertTrue(Library.addStudentInfo(Arrays.asList(s2.split(","))));
        String s3 = "123,125";
        assertTrue(Library.generateGrades(Arrays.asList(s3.split(","))));
        assertEquals("学生张三的成绩被添加\n" +
                "学生李四的成绩被添加\n" +
                "成绩单\n" +
                "姓名|数学|语文|英语|编程|平均分|总分\n" +
                "========================\n" +
                "张三|75|95|80|80|82.5|330\n" +
                "李四|85|80|70|90|81.25|325\n" +
                "========================\n" +
                "全班总分平均数：327.5\n" +
                "全班总分中位数：327.5\n", systemOut());
    }

    @Test
    public void testReportMidAndTotal() {
        String s1 = "张三,123,数学:75,语文:95,英语:80,编程:80";
        String s2 = "李四,125,数学:85,语文:80,英语:70,编程:90";
        String s3 = "赵五,127,数学:85,语文:85,英语:85,编程:85";
        assertTrue(Library.addStudentInfo(Arrays.asList(s1.split(","))));
        assertTrue(Library.addStudentInfo(Arrays.asList(s2.split(","))));
        assertTrue(Library.addStudentInfo(Arrays.asList(s3.split(","))));
        String s4 = "123,125,127";
        assertTrue(Library.generateGrades(Arrays.asList(s4.split(","))));
        assertEquals("学生张三的成绩被添加\n" +
                "学生李四的成绩被添加\n" +
                "学生赵五的成绩被添加\n" +
                "成绩单\n" +
                "姓名|数学|语文|英语|编程|平均分|总分\n" +
                "========================\n" +
                "张三|75|95|80|80|82.5|330\n" +
                "李四|85|80|70|90|81.25|325\n" +
                "赵五|85|85|85|85|85|340\n" +
                "========================\n" +
                "全班总分平均数：331.67\n" +
                "全班总分中位数：330\n", systemOut());
    }

}
