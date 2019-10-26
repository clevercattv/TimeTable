package com.clevercattv.table.models;

import com.clevercattv.table.services.TimeTableService;

import javax.validation.constraints.Pattern;
import java.util.Objects;


public class Teacher {

    /** Teacher 'First name Last name Middle name' */
    @Pattern(regexp = TimeTableService.VALIDATION)
    private String fullName;
    private Type type;
    private final static int MIN_NAME_LENGTH = 10;
    private final static int MAX_NAME_LENGTH = 48;

    private Teacher() { }

    public static Teacher build(String fullName, Type type) {
        Teacher teacher = new Teacher();
        teacher.setFullName(fullName);
        teacher.setType(type);
        return teacher;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Teacher teacher = (Teacher) o;
        return Objects.equals(fullName, teacher.fullName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fullName);
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public enum Type{
        POST_GRADUATE("P-G"), // аспирант
        DOCENT("DOC"),
        PROFESSOR("PROF");

        final String abbreviation;

        Type(String abbreviation){ this.abbreviation = abbreviation; }

        public String getAbbreviation() {
            return abbreviation;
        }

    }

}
