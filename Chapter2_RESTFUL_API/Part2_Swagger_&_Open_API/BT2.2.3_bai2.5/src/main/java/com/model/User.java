package com.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "User resource")
public class User {
    @Schema(description = "Unique user identifier", example = "1")
    private Integer id;

    @Schema(description = "User name", example = "Nguyen Van An")
    private String name;

    public User() {
    }

    public User(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
