package com.clevercattv.table.validation;

public class PerformedMessage {

    private String message;
    private boolean performed;

    public PerformedMessage(String message, boolean performed) {
        this.message = message;
        this.performed = performed;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isPerformed() {
        return performed;
    }

    public void setPerformed(boolean performed) {
        this.performed = performed;
    }

}