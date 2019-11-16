package com.clevercattv.table.model;

import com.clevercattv.table.validation.PerformedMessage;
import com.clevercattv.table.validation.Validator;

import java.util.Arrays;
import java.util.Objects;

public class Group implements EntityId<Group>, Comparable<Group> {

    public static final int MIN_NAME_LENGTH = 3;
    public static final int MAX_NAME_LENGTH = 16;
    public static final String NAME_PATTERN = "^[a-z A-Z0-9-]+$";

    private int id;
    private String name;

    public Group() {
    }

    public Group(int id) {
        this.id = id;
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
                '}';
    }

    public String getName() {
        return name;
    }

    public Group setName(String name) {
        Validator.getMessagesByPerformedTrue(Arrays.asList(
                new PerformedMessage("Group name length less than minimum",
                        name.length() < MIN_NAME_LENGTH),
                new PerformedMessage("Group name length more than maximum",
                        name.length() > MAX_NAME_LENGTH),
                new PerformedMessage("Group name have forbidden symbols. " +
                        "Please use 'a-z A-Z 0-9' and '-' as group divider ",
                        !name.matches(NAME_PATTERN))
        ));
        this.name = name;
        return this;
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

    @Override
    public int compareTo(Group o) {
        return name.compareTo(o.name);
    }

}