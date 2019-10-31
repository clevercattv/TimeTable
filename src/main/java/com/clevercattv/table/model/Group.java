package com.clevercattv.table.model;

import com.clevercattv.table.exception.NamingException;
import com.clevercattv.table.validation.PerformedMessage;
import com.clevercattv.table.validation.Validator;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

public class Group implements EntityId<Group> {

    public static final int MIN_NAME_LENGTH = 3;
    public static final int MAX_NAME_LENGTH = 16;
    public static final String NAME_PATTERN = "^[a-z A-Z0-9-]+$";
    public static final String DIVIDER = "-";

    private int id;
    private String name;
    private final boolean combined;

    private Group(){ this.combined = false; }

    private Group(boolean combined) { this.combined = combined; }

    private static Group build(String name, boolean combined) {
        return new Group(combined).setName(name);
    }

    public static Group build(int id, String name, boolean combined) {
        return new Group(combined)
                .setId(id)
                .setName(name);
    }

    public static Group build(int id, String name) {
        return build(id, name,false);
    }

    public static Group build(String name) {
        return build(name,false);
    }

    public static Group build(Group[] groups) {
        if (groups.length < 2) throw new IllegalArgumentException("Combined group builds rom 2 or more groups.");
        return build(Arrays.stream(groups)
                .map(Group::getName)
                .collect(Collectors.joining(DIVIDER)), true);
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

    @Override
    public String toString() {
        return "Group{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", combined=" + combined +
                '}';
    }

    public String getName() {
        return name;
    }

    public Group setName(String name) {
        Validator.filterByPerformedTrueAndResultMessagesToString(new PerformedMessage[]{
                new PerformedMessage("Group name length less than minimum.",
                        name.length() < MIN_NAME_LENGTH),
                new PerformedMessage("Group name length more than maximum.",
                        name.length() > MAX_NAME_LENGTH),
                new PerformedMessage("Group name have forbidden symbols. " +
                        "Please use 'a-z A-Z 0-9' and '-' as group divider. ",
                        !name.matches(NAME_PATTERN)),
        }).ifPresent(e -> {
            throw new NamingException(e);
        });
        this.name = name;
        return this;
    }

    public boolean isCombined() {
        return combined;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public Group setId(int id) {
        this.id = id;
        return this;
    }
}