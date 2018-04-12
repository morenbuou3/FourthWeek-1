package com.tw;

import java.text.DecimalFormat;
import java.util.*;
import java.util.regex.Pattern;

public class Library {

    private static List<String> course = new ArrayList<>();
    private static List<StudentInfo> report = new ArrayList<>();

    private static final Pattern patternName = Pattern.compile("^[\\u4E00-\\u9FA5A-Za-z]+$");
    private static final Pattern patternNumber = Pattern.compile("^[0-9]+$");
    private static final Pattern patternGrade = Pattern.compile("^(\\d{1}|[1-9]\\d{1}|1[0-1]\\d{1}|100)$");

    private static final DecimalFormat decimalFormat = new DecimalFormat("###################.##");

    static {
        course.add("数学");
        course.add("语文");
        course.add("英语");
        course.add("编程");
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        printMenu("menu");
        while (sc.hasNextLine()) {
            String inputStr = sc.nextLine().trim();
            List<String> inputList;
            if (inputStr.equals("3")) {
                break;
            }
            switch (inputStr) {
                case "1":
                    printMenu("add");
                    while (sc.hasNextLine()) {
                        String input = sc.nextLine();
                        inputList = Arrays.asList(input.split(","));
                        if (addStudentInfo(inputList)) {
                            break;
                        }
                        printMenu("falseAdd");
                    }
                    printMenu("menu");
                    break;
                case "2":
                    printMenu("output");
                    inputList = new ArrayList<>();
                    while (sc.hasNextLine()) {
                        String input = sc.nextLine();
                        inputList.clear();
                        inputList.addAll(Arrays.asList(input.split(",")));
                        if (generateGrades(inputList)) {
                            break;
                        }
                        printMenu("falseOutput");
                    }
                    printMenu("menu");
                    break;
                default:
                    break;
            }
        }
    }

    public static boolean addStudentInfo(List<String> inputList) {
        if (!judgeStudentInfo(inputList)) {
            return false;
        }

        Map<String, Integer> grades = new HashMap<>();
        StudentInfo student = new StudentInfo();
        student.setName(inputList.get(0));
        student.setNumber(inputList.get(1));
        inputList.stream().skip(2).forEach(n -> {
            String[] item = n.split(":");
            grades.put(item[0].trim(), Integer.parseInt(item[1].trim()));
        });
        student.setGrades(grades);
        report.add(student);

        System.out.print("学生" + student.getName() + "的成绩被添加\n");

        return true;
    }

    public static boolean generateGrades(List<String> inputList) {
        boolean flag = inputList.stream().allMatch(n -> judgePattern(n, patternNumber));
        if (!flag) {
            return false;
        }
        double classAvg = report.stream().mapToInt(StudentInfo::getTotal).average().orElse(0);
        double classMid = 0;
        int size = report.size();
        if (size != 0) {
            if (size % 2 == 0) {
                classMid = (report.get(size / 2 - 1).getTotal() + report.get(size / 2).getTotal()) / 2.0;
            } else {
                classMid = report.get(size / 2).getTotal();
            }
        }

        System.out.println("成绩单");
        System.out.println("姓名|数学|语文|英语|编程|平均分|总分");
        System.out.println("========================");
        report.stream().filter(n -> inputList.contains(Integer.toString(n.getNumber()))).forEach(
                n -> System.out.println(n.getName() + "|"
                        + n.getGrades().get("数学") + "|"
                        + n.getGrades().get("语文") + "|"
                        + n.getGrades().get("英语") + "|"
                        + n.getGrades().get("编程") + "|"
                        + n.getAverage() + "|"
                        + decimalFormat.format(n.getTotal())));
        System.out.println("========================");
        System.out.println("全班总分平均数：" + decimalFormat.format(classAvg));
        System.out.println("全班总分中位数：" + decimalFormat.format(classMid));

        return true;
    }

    private static void printMenu(String result) {
        switch (result) {
            case "menu":
                System.out.print("1. 添加学生\n" +
                        "2. 生成成绩单\n" +
                        "3. 退出\n" +
                        "请输入你的选择(1~3):\n");
                break;
            case "add":
                System.out.print("请输入学生信息（格式：姓名, 学号, 学科: 成绩, ...），按回车提交：\n");
                break;
            case "output":
                System.out.print("请输入要打印的学生的学号（格式： 学号, 学号,...），按回车提交：\n");
                break;
            case "falseAdd":
                System.out.print("请按正确的格式输入（格式：姓名, 学号, 学科: 成绩, ...），按回车提交：\n");
                break;
            case "falseOutput":
                System.out.print("请按正确的格式输入要打印的学生的学号（格式： 学号, 学号,...），按回车提交：\n");
                break;
            default:
                break;
        }

    }

    private static boolean judgeStudentInfo(List<String> inputList) {
        if (inputList.size() != 6) {
            return false;
        }
        if (!judgePattern(inputList.get(0), patternName)) return false;
        if (!judgePattern(inputList.get(1), patternNumber)) return false;
        boolean flag = true;
        for (int i = 2; i < inputList.size(); i++) {
            String[] item = inputList.get(i).split(":");
            if (!course.contains(item[0])
                    || !judgePattern(item[1], patternGrade)) {
                flag = false;
                break;
            }
        }
        return flag;
    }

    private static boolean judgePattern(String input, Pattern patternName) {
        return patternName.matcher(input).matches();
    }

}
