package com.clevercattv.table.validation;

import com.clevercattv.table.exception.NamingException;

import java.util.List;
import java.util.stream.Collectors;

public class Validator {

    private Validator(){}

    public static void throwExceptionIfPerformedTrue(List<PerformedMessage> messages) {
        List<String> list = messages.stream()
                .filter(PerformedMessage::isPerformed)
                .map(PerformedMessage::getMessage)
                .collect(Collectors.toList());
        if (!list.isEmpty()) {
            throw new NamingException(list.toString());
        }
    }

}