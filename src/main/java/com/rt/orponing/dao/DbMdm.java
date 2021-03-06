// This is a personal academic project. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: http://www.viva64.com
package com.rt.orponing.dao;

import com.rt.orponing.dao.data.*;
import com.rt.orponing.dao.interfaces.IDbSaveData;
import com.rt.orponing.repository.data.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
@Lazy
public class DbMdm extends CommonDb implements IDbSaveData {

    public DbMdm(@Qualifier("dbConnectSaveData") DbConnect dbConnect) {
        super(dbConnect,
                "Select public.nsi_temp_orponing_test_bd();",
                LoggerFactory.getLogger(DbMdm.class));
    }

    //region PrivateField

    private static final String QUERY_SELECT_ADDRESS = "Select * from public.nsi_temp_orponing_select_input_data();";
    private static final String QUERY_UPDATE_ADDRESS = "Call public.nsi_temp_orponing_update_output_data(?,?,?,?,?,?,?,?);";

    //endregion PrivateField

    //region PublicMethod

    @Override
    public List<EntityAddress> GetEntityAddress() throws DaoException {
        logger.info("Load address for orponing");
        List<EntityAddress> list = new ArrayList<>();

        try (Connection con = dbConnect.GetConnection(); PreparedStatement ps = con.prepareStatement(QUERY_SELECT_ADDRESS)) {
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String address = resultSet.getString(2);
                list.add(new EntityAddress(id, address));
            }
        } catch (DaoException d) {
            throw d;
        } catch (Exception ex) {
            throw new DaoException(ex.getMessage(), ex);
        }

        return list;
    }

    @Override
    public void UpdateEntityAddress(List<AddressInfo> collectionAddressInfo) throws DaoException {
        logger.info("Write collection address info to bd");
        ProcessConnect(con -> AddAddressInfo(con, collectionAddressInfo));
    }

    //endregion PublicMethod

    //region PrivateMethod

    private void AddAddressInfo(Connection con, List<AddressInfo> collectionAddressInfo) throws DaoException {

        try (PreparedStatement ps = con.prepareStatement(QUERY_UPDATE_ADDRESS)) {
            for (AddressInfo a : collectionAddressInfo) {

                ps.setInt(1, a.Id);
                ps.setLong(2, a.GlobalId);
                ps.setString(3, a.AddressOrpon);
                ps.setString(4, a.ParsingLevelCode);
                ps.setString(5, a.UnparsedParts);
                ps.setString(6, a.QualityCode);
                ps.setString(7, a.CheckStatus);
                ps.setString(8, a.Error);

                ps.addBatch();
            }
            ps.executeBatch();
        } catch (Exception ex) {
            throw new DaoException("Ошибка обработки запроса: " + QUERY_UPDATE_ADDRESS + " " + ex.getMessage());
        }
    }

    //endregion PrivateMethod
}