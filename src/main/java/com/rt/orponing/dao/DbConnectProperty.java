package com.rt.orponing.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbConnectProperty {

    public DbConnectProperty(String dbServer, int dbPort, String dbName, String dbUser, String dbPassword, String dbSchema) throws Exception {
        if (dbServer == null || dbServer.isEmpty() || dbPort <= 0 || dbName == null || dbName.isEmpty())
            throw new Exception("DbProperty is bad");

        DbSchema = dbSchema;
        _url = String.format("jdbc:postgresql://%s:%s/%s?user=%s&password=%s", dbServer, dbPort, dbName, dbUser, dbPassword);
    }

    //region PrivateField
    public final String DbSchema;
    private final String _url;
    //endregion PrivateField

    //region PublicMethod

    public Connection GetConnection() throws DaoException {
        try {
            return DriverManager.getConnection(_url);
        } catch (Exception ex) {
            throw new DaoException(ex.getMessage());
        }
    }
    //endregion PublicMethod
}