package com.devdooly.skeleton.core.service;

import com.devdooly.skeleton.core.dto.User;

public interface JdbcDataService {

    String findNowByDateFormat(String dateFormat);

    User findUserById(Long id);

}
