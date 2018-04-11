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
    ByteArrayOutputStream outContent;

    @Before
    public void setUp() throws Exception {
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    private String systemOut() {
        return outContent.toString();
    }

    @Test
    public void testjudgeStudentInfo () {
        String s1 = "张三,123,数学:75,语文:95,英语:80,编程:80";
        assertTrue(Library.addStudentInfo(Arrays.asList(s1.split(","))));

        String s2 = "abc,123,数学:75,语文:95,英语:80,编程:80";
        Library.addStudentInfo(Arrays.asList(s2.split(",")));
        assertTrue(Library.addStudentInfo(Arrays.asList(s2.split(","))));

        String s3 = "张三,123,数学:75,语文:95,英语:80";
        Library.addStudentInfo(Arrays.asList(s3.split(",")));
        assertTrue(!Library.addStudentInfo(Arrays.asList(s3.split(","))));

        String s4 = "张三,学号,数学:75,语文:95,英语:80,编程:80";
        Library.addStudentInfo(Arrays.asList(s4.split(",")));
        assertTrue(!Library.addStudentInfo(Arrays.asList(s4.split(","))));

        String s5 = "张三,123,数学:75,语文:95,英语:130,编程:80";
        Library.addStudentInfo(Arrays.asList(s5.split(",")));
        assertTrue(!Library.addStudentInfo(Arrays.asList(s5.split(","))));

    }
/*    @Test
    public void testSomeLibraryMethod() {
        Library classUnderTest = new Library();
        assertTrue("someLibraryMethod should return 'true'", classUnderTest.someLibraryMethod());
    }

    @Test
    public void testMockClass() throws Exception {
        // you can mock concrete classes, not only interfaces
        LinkedList mockedList = mock(LinkedList.class);

        // stubbing appears before the actual execution
        String value = "first";
        when(mockedList.get(0)).thenReturn(value);

        assertEquals(mockedList.get(0), value);

    }*/

}
