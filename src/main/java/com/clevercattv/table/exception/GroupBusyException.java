package com.clevercattv.table.exception;

public class GroupBusyException extends BusyException {

    public GroupBusyException() {
        super("Group have another subject. Try another lesson!");
    }

}
