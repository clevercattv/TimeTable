package com.clevercattv.table.exception;

public class RoomBusyException extends BusyException {

    public RoomBusyException() {
        super("Auditory is busy. Try another lesson or day!");
    }

}
