package com.clevercattv.table.dto;

public class NameIdDTO {

    private int id;
    private String name;

    public NameIdDTO(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
