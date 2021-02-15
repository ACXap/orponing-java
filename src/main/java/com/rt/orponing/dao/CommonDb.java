// This is a personal academic project. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: http://www.viva64.com
package com.rt.orponing.dao;

import com.rt.orponing.dao.data.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;

@RequiredArgsConstructor
public abstract class CommonDb {

    //region PrivateField

    protected final DbConnect dbConnect;
    protected final String queryTestDb;
    protected final Logger logger;

    //endregion PrivateField

    //region PublicMethod
    public boolean TestDb() throws DaoException {
        ProcessConnect(this::TestBd);
        return true;
    }
    //endregion PublicMethod

    //region PrivateMethod
    protected void TestBd(Connection con) throws DaoException {
        try (PreparedStatement ps = con.prepareStatement(queryTestDb)) {
            ps.execute();
        } catch (Exception ex) {
            String error = "Ошибка обработки запроса: " + queryTestDb + " " + ex.getMessage();
            logger.error(error);
            throw new DaoException(error, ex);
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
            logger.error(d.getMessage());
            throw d;
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            throw new DaoException(ex.getMessage());
        }
    }

    //endregion PrivateMethod
}