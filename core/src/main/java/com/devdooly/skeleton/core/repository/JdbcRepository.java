package com.devdooly.skeleton.core.repository;

import com.devdooly.skeleton.core.dto.User;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;

public interface JdbcRepository {

    //    @SqlQuery("SELECT TO_CHAR(SYSTIMESTAMP, :dateFormat) FROM DUAL")
    @SqlQuery("SELECT DATE_FORMAT(NOW(), :dateFormat)")
    String findNowByDateFormat(@Bind("dateFormat") String dateFormat);

    @SqlQuery("SELECT * FROM USER WHERE id = :id")
    @RegisterBeanMapper(User.class)
    User findUserById(@Bind("id") Long id);

    @SqlUpdate("""
        INSERT INTO USER (ID, USER_NAME, PASSWORD)
                VALUES (:id, :userName, :password)""")
    void saveUser(@BindBean User user);

    @SqlUpdate("""
        UPDATE USER 
        SET ID = :id, USER_NAME = :userName, PASSWORD = :password
        WHERE ID = :id""")
    void updateUser(@BindBean User user);
}
