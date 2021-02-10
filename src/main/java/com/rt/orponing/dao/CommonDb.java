// This is a personal academic project. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: http://www.viva64.com
package com.rt.orponing.dao;

import com.rt.orponing.dao.data.*;

import java.sql.Connection;
import java.sql.PreparedStatement;

public abstract class CommonDb {

    protected DbConnect dbConnect;
    protected String queryTestDb;

    public boolean TestDb() throws DaoException {
        ProcessConnect(this::TestBd);
        return true;
    }

    protected void TestBd(Connection con) throws DaoException {
        try (PreparedStatement ps = con.prepareStatement(queryTestDb)) {
            ps.execute();
        } catch (Exception ex) {
            throw new DaoException("Ошибка обработки запроса: " + queryTestDb + " " + ex.getMessage(), ex);
        }
    }

    protected void ProcessConnect(ICheckedConsumer<Connection> callback) throws DaoException {
        try {
            Connection con = dbConnect.GetConnection();
            try {
                con.setAutoCommit(false);

                callback.accept(con);

                con.commit();
            } catch (Exception ex) {
                con.rollback();
                con.close();
                throw ex;
            } finally {
                con.close();
            }
        } catch (DaoException d) {
            throw d;
        } catch (Exception ex) {
            throw new DaoException(ex.getMessage());
        }
    }
}