package com.devdooly.skeleton.core.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class Check {

    @JsonProperty("shutdownable")
    private final boolean shutdownable;

    @JsonCreator
    public Check(@JsonProperty("shutdownable") boolean shutdownable) {
        this.shutdownable = shutdownable;
    }
}
