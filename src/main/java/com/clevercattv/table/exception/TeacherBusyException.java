package com.clevercattv.table.exception;

public class TeacherBusyException extends BusyException {

    public TeacherBusyException() {
        super("Teacher have another subject. Try another lesson!");
    }

}
