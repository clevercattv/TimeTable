package com.clevercattv.table.models;

import com.clevercattv.table.exceptions.NamingException;

import java.util.Objects;
import java.util.regex.Pattern;

public class Room {

    private String name;
    private Type type;
    public static final int MAX_NAME_LENGTH = 16;
    public static final Pattern NAME_PATTERN = Pattern.compile("^[a-z A-Z0-9]+$");

    private Room() { }

    public static Room build(String name, Type type) {
        Room room = new Room();
        room.setName(name);
        room.setType(type);
        return room;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return Objects.equals(name, room.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Room{" +
                "name='" + name + '\'' +
                ", type=" + type +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name.isEmpty()) throw new NamingException("Auditory name length less than minimum.");
        if (name.length() > MAX_NAME_LENGTH) throw new NamingException("Auditory name length more than maximum.");
        if (!NAME_PATTERN.matcher(name).matches()) {
            throw new NamingException("Auditory name have forbidden symbols. Please use 'a-z A-Z 0-9' ");
        }
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public enum Type{
        AUDITORY("A"),
        LABORATORY("L");

        final String name;

        Type(String name){ this.name = name; }

        public String getName(){
            return name;
        }

    }

}
