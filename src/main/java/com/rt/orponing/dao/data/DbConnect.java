// This is a personal academic project. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: http://www.viva64.com
package com.rt.orponing.dao.data;

import lombok.AllArgsConstructor;

import java.sql.Connection;
import java.sql.DriverManager;

@AllArgsConstructor
public class DbConnect {

    private final String url;
    private final String user;
    private final String password;

    public Connection GetConnection() throws DaoException {
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (Exception ex) {
            throw new DaoException(ex.getMessage());
        }
    }
}