package com.dean.springbootbase.domain;

/* Record 타입 사용 */

public record Student(
        String name,
        Integer age,
        Grade grade
) {
    public static Student of(String name, Integer age, Grade grade) {
        return new Student(name, age, grade);
    }
    private enum Grade {
        A,B,C,D,F
    }
}



