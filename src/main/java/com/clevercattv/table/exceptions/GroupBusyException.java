package com.clevercattv.table.exceptions;

public class GroupBusyException extends BusyException {

    public GroupBusyException() {
        super("Group have another subject. Try another lesson!");
    }

}
