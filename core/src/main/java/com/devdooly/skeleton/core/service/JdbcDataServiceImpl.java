package com.devdooly.skeleton.core.service;

import com.devdooly.skeleton.core.dto.User;
import com.devdooly.skeleton.core.repository.JdbcRepository;
import org.springframework.stereotype.Component;

import java.io.Closeable;

@Component
public class JdbcDataServiceImpl implements JdbcDataService, Closeable {

    private final JdbcRepository jdbcRepository;
    private final Runnable destoryer;

    public JdbcDataServiceImpl(JdbcRepository jdbcRepository, Runnable destroyer) {
        this.jdbcRepository = jdbcRepository;
        this.destoryer = destroyer;
    }

    @Override
    public String findNowByDateFormat(String dateFormat) {
        return jdbcRepository.findNowByDateFormat(dateFormat);
    }

    @Override
    public User findUserById(Long id) {
        return jdbcRepository.findUserById(id);
    }

    @Override
    public void saveUser(User user) {
        jdbcRepository.saveUser(user);
    }

    @Override
    public void updateUser(User user) {
        jdbcRepository.updateUser(user);
    }

    @Override
    public void close() {
        destoryer.run();
    }
}
