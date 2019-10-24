package com.clevercattv.table.exceptions;

public class RoomBusyException extends BusyException {

    public RoomBusyException() {
        super("Auditory is busy. Try another lesson or day!");
    }

}
