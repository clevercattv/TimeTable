package com.clevercattv.table.exceptions;

public class TeacherBusyException extends BusyException {

    public TeacherBusyException() {
        super("Teacher have another subject. Try another lesson!");
    }

}
