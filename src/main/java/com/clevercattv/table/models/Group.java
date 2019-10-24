package com.clevercattv.table.models;

import com.clevercattv.table.exceptions.NamingException;
import com.sun.deploy.util.StringUtils;

import java.util.Arrays;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Group {

    private String name;
    private final boolean combined;
    public static final String DIVIDER = "-";
    public static final int MIN_NAME_LENGTH = 3;
    public static final int MAX_NAME_LENGTH = 16;
    public static final Pattern NAME_PATTERN = Pattern.compile("^[a-z A-Z0-9-]+$");

    private Group(){ this.combined = false; }

    private Group(boolean combined) { this.combined = combined; }

    private static Group build(String name, boolean combined) {
        Group group = new Group(combined);
        group.setName(name);
        return group;
    }

    public static Group build(String name) {
        return build(name,false);
    }

    public static Group build(Group... groups) {
        if (groups.length == 0) throw new IllegalArgumentException();
        return build(StringUtils.join(Arrays.stream(groups).map(Group::getName).collect(Collectors.toList()), DIVIDER), true);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return Objects.equals(name, group.name);
    }

    @Override
    public int hashCode() { return Objects.hash(name); }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name.length() < MIN_NAME_LENGTH) throw new NamingException("Group name length less than minimum.");
        if (name.length() > MAX_NAME_LENGTH) throw new NamingException("Group name length more than maximum.");
        if (!NAME_PATTERN.matcher(name).matches()) {
            throw new NamingException("Group name have forbidden symbols. " +
                    "Please use 'a-z A-Z 0-9' and '-' as group divider. ");
        }
        this.name = name;
    }

    public boolean isCombined() {
        return combined;
    }

}