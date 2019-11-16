package com.clevercattv.table.model;

import com.clevercattv.table.exception.NamingException;
import com.clevercattv.table.validation.PerformedMessage;
import com.clevercattv.table.validation.Validator;

import java.util.Arrays;
import java.util.Objects;

public class Room implements EntityId<Room>{

    private int id;
    private String name;
    private Type type;

    public static final int MAX_NAME_LENGTH = 16;
    public static final String NAME_PATTERN = "^[a-z A-Z0-9]+$";

    public Room() {
    }

    public Room(int id) {
        this.id = id;
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

    public Room setName(String name) {
        Validator.getMessagesByPerformedTrue(Arrays.asList(
                new PerformedMessage("Room name is empty",
                        name.isEmpty()),
                new PerformedMessage("Room name length more than maximum (" + MAX_NAME_LENGTH + ")",
                        name.length() > MAX_NAME_LENGTH),
                new PerformedMessage("Room name have forbidden symbols. Please use 'a-z A-Z 0-9' ",
                        !name.matches(NAME_PATTERN))
        ));
        this.name = name;
        return this;
    }

    public Type getType() {
        return type;
    }

    public Room setType(Type type) {
        this.type = type;
        return this;
    }

    public Room setType(String type) {
        if (type == null || type.isEmpty()){
            throw new NamingException("Room type wrong!");
        }
        this.type = Type.valueOf(type);
        return this;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public Room setId(int id) {
        this.id = id;
        return this;
    }

    public enum Type{
        AUDITORY("A", 0),
        LABORATORY("L",1);

        final String name;
        final int id;

        Type(String name, int id){
            this.name = name;
            this.id = id;
        }

        public String getName(){
            return name;
        }

        public int getId(){
            return id;
        }



    }

}
