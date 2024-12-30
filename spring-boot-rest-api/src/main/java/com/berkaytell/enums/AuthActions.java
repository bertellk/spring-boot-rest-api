package com.berkaytell.enums;

public enum AuthActions {
    GET(1L, "GET"),
    POST(2L, "POST"),
    UPDATE(3L, "UPDATE"),
    DELETE(4L, "DELETE"),
    PATCH(5L, "PATCH");

    public final Long id;
    public final String description;

    AuthActions(Long id, String description) {
        this.id = id;
        this.description = description;
    }

}
