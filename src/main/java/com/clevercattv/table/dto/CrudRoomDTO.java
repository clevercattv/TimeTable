package com.clevercattv.table.dto;

import com.clevercattv.table.model.Room;

import java.util.List;

public class CrudRoomDTO {

    private Room.Type[] types = Room.Type.values();
    private List<Room> rooms;

    public CrudRoomDTO(List<Room> rooms) {
        this.rooms = rooms;
    }

    @Override
    public String toString() {
        return "CrudRoomDTO{" +
                "types=" + types +
                ", rooms=" + rooms +
                '}';
    }

    public Room.Type[] getTypes() {
        return types;
    }

    public List<Room> getRooms() {
        return rooms;
    }

}
