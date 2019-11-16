package com.clevercattv.table.model;

import com.clevercattv.table.validation.PerformedMessage;
import com.clevercattv.table.validation.Validator;

import java.util.Arrays;
import java.util.Objects;


public class Teacher implements EntityId<Teacher>{

    public static final int MIN_NAME_LENGTH = 10;
    public static final int MAX_NAME_LENGTH = 48;

    private int id;
    private String fullName;
    private Type type;

    public Teacher() {
    }

    public Teacher(int id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", type=" + type +
                '}';
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public Teacher setId(int id) {
        this.id = id;
        return this;
    }

    public String getFullName() {
        return fullName;
    }

    public Teacher setFullName(String fullName) {
        Validator.throwExceptionIfPerformedTrue(Arrays.asList(
                new PerformedMessage("Teacher name length less than minimum",
                        fullName.length() < MIN_NAME_LENGTH),
                new PerformedMessage("Teacher name length more than maximum",
                        fullName.length() > MAX_NAME_LENGTH),
                new PerformedMessage("Teacher name have have forbidden symbols",
                        !fullName.matches("^[a-z A-Z]+$"))
        ));
        this.fullName = fullName;
        return this;
    }

    public Type getType() {
        return type;
    }

    public Teacher setType(Type type) {
        if (type == null) {
            throw new NullPointerException();
        }
        this.type = type;
        return this;
    }

    public enum Type{
        POST_GRADUATE("P-G"),
        DOCENT("DOC"),
        PROFESSOR("PROF");

        final String abbreviation;

        Type(String abbreviation){ this.abbreviation = abbreviation; }

        public String getAbbreviation() {
            return abbreviation;
        }

    }

}
