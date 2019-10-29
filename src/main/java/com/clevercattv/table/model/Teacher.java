package com.clevercattv.table.model;

import com.clevercattv.table.exception.NamingException;
import com.clevercattv.table.service.TimeTableService;

import java.util.Objects;


public class Teacher {

    /** Teacher 'First name Last name Middle name' */
//    @Pattern(regexp = TimeTableService.VALIDATION)
    private String fullName;
    private Type type;
    public final static int MIN_NAME_LENGTH = 10;
    public final static int MAX_NAME_LENGTH = 48;

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
        if (fullName.length() < MIN_NAME_LENGTH) throw new NamingException("Teacher name length less than minimum.");
        if (fullName.length() > MAX_NAME_LENGTH) throw new NamingException("Teacher name length more than maximum.");
        if (!fullName.matches("^[a-z A-Z]+$")) throw new NamingException("Teacher name have have forbidden symbols.");
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
