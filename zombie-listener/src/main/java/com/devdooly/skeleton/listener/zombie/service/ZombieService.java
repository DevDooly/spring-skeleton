package com.devdooly.skeleton.listener.zombie.service;

import com.devdooly.skeleton.core.dto.User;
import com.devdooly.skeleton.core.service.JdbcDataService;
import org.springframework.beans.factory.annotation.Autowired;

public class ZombieService {

    private final JdbcDataService jdbcDataService;

    @Autowired
    public ZombieService(JdbcDataService jdbcDataService) {
        this.jdbcDataService = jdbcDataService;
    }

    public String findNowByDateFormat(String dateFormat) {
        return jdbcDataService.findNowByDateFormat(dateFormat);
    }

    public User getUserById(Long id) {
        return jdbcDataService.findUserById(id);
    }

}
