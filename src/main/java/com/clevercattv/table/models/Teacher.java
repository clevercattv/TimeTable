package com.clevercattv.table.models;

import com.clevercattv.table.exceptions.NamingException;
import com.clevercattv.table.services.TimeTableService;

import java.util.Objects;


public class Teacher {

    /** Teacher 'First name Last name Middle name' */
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
        if (fullName.length() < MIN_NAME_LENGTH) throw new NamingException("Teacher full name length less than minimum.");
        if (fullName.length() > MAX_NAME_LENGTH) throw new NamingException("Teacher full name length more than maximum.");
        if (!TimeTableService.VALIDATION.matcher(fullName).matches()) {
            throw new NamingException("Teacher full name have forbidden symbols. Please use 'a-z A-Z'.");
        }
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
