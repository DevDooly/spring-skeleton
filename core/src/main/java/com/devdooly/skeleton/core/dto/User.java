package com.devdooly.skeleton.core.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.StringJoiner;

@Getter
@Setter
@RequiredArgsConstructor
public class User {

    private Long id;

    private String userName;

    private String password;

    @Override
    public String toString() {
        return new StringJoiner(",", "User{", "}")
                .add("id=" + id)
                .add("userName=" + userName)
                .add("password=" + password)
                .toString();
    }

}
