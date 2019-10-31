package com.clevercattv.table.validation;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Validator {

    private Validator(){}

    public static Optional<String> filterByPerformedTrueAndResultMessagesToString(
            PerformedMessage[] performedMessages) {
        List<String> list = Arrays.stream(performedMessages)
                .filter(PerformedMessage::isPerformed)
                .map(PerformedMessage::getMessage)
                .collect(Collectors.toList());
        if (list.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(list.toString());
    }


}
