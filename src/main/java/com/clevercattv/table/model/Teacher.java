package com.clevercattv.table.model;

import com.clevercattv.table.exception.NamingException;
import com.clevercattv.table.validation.PerformedMessage;
import com.clevercattv.table.validation.Validator;

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
        return new Teacher()
                .setFullName(fullName)
                .setType(type);
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

    public Teacher setFullName(String fullName) {
        Validator.filterByPerformedTrueAndResultMessagesToString(new PerformedMessage[]{
                new PerformedMessage("Teacher name length less than minimum.",
                        fullName.length() < MIN_NAME_LENGTH),
                new PerformedMessage("Teacher name length more than maximum.",
                        fullName.length() > MAX_NAME_LENGTH),
                new PerformedMessage("Teacher name have have forbidden symbols.",
                        !fullName.matches("^[a-z A-Z]+$")),
        }).ifPresent(e -> {
            throw new NamingException(e);
        });
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
