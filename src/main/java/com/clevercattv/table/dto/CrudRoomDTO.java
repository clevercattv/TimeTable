package com.clevercattv.table.dto;

import com.clevercattv.table.model.Room;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CrudRoomDTO {

    private static final List<String> TYPES = Arrays.stream(Room.Type.values())
            .map(Enum::name)
            .collect(Collectors.toList());

    private List<Room> rooms;

    public CrudRoomDTO(List<Room> rooms) {
        this.rooms = rooms;
    }

    @Override
    public String toString() {
        return "CrudRoomDTO{" +
                "types=" + TYPES +
                ", rooms=" + rooms +
                '}';
    }

    public List<String> getTypes() {
        return TYPES;
    }

    public List<Room> getRooms() {
        return rooms;
    }

}
