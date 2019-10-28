package com.clevercattv.table.jdbc;

public class SqlBuilder {

    private StringBuilder builder = new StringBuilder();

    public SqlBuilder createTableIfNotExist() {
        builder.append("CREATE TABLE IF NOT EXISTS ");
        return this;
    }

    public SqlBuilder addCustomText(String text) {
        builder.append(text);
        return this;
    }

}
