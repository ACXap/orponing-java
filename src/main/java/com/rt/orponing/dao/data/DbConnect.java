// This is a personal academic project. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: http://www.viva64.com
package com.rt.orponing.dao.data;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;

@Component
public class DbConnect{

    @Value("${db.url}")
    private String _url;

    @Value("${db.user}")
    private String _user;

    @Value("${db.password}")
    private String _password;

    public Connection GetConnection() throws DaoException {
        try {
            return DriverManager.getConnection(_url, _user, _password);
        } catch (Exception ex) {
            throw new DaoException(ex.getMessage());
        }
    }
}