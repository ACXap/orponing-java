// This is a personal academic project. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: http://www.viva64.com
package com.rt.orponing.dao;

import com.rt.orponing.dao.data.*;
import com.rt.orponing.repository.data.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Component
@Lazy
public class DbMdmSaveData extends CommonDb implements IDbSaveData {

    public DbMdmSaveData(@Qualifier("dbConnectSaveData") DbConnect dbConnect){
        this.dbConnect = dbConnect;
        queryTestDb = "Select public.nsi_temp_orponing_test_bd();";
    }

    //region PrivateField
//    @Autowired
//    @Qualifier("dbConnectSaveData")
//    private DbConnect dbConnect;

    private static final String _querySelectAddress = "Select * from public.nsi_temp_orponing_select_input_data();";
    private static final String _queryUpdateAddress = "Call public.nsi_temp_orponing_update_output_data(?,?,?,?,?,?,?,?);";
   // private static final String _queryTestDb = "Select public.nsi_temp_orponing_test_bd();";

    //endregion PrivateField

    //region PublicMethod
    @Override
    public List<EntityAddress> GetEntityAddress() throws DaoException {
        List<EntityAddress> list = new ArrayList<>();

        try (Connection con = dbConnect.GetConnection(); PreparedStatement ps = con.prepareStatement(_querySelectAddress)) {
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
        ProcessConnect(con -> AddAddressInfo(con, collectionAddressInfo));
    }
    //endregion PublicMethod

    //region PrivateMethod
//    private void TestBd(Connection con) throws DaoException {
//        try (PreparedStatement ps = con.prepareStatement(_queryTestDb)) {
//            ps.execute();
//        } catch (Exception ex) {
//            throw new DaoException("Ошибка обработки запроса: " + _queryTestDb + " " + ex.getMessage(), ex);
//        }
//    }

    private void AddAddressInfo(Connection con, List<AddressInfo> collectionAddressInfo) throws DaoException {

        try (PreparedStatement ps = con.prepareStatement(_queryUpdateAddress)) {
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
            throw new DaoException("Ошибка обработки запроса: " + _queryUpdateAddress + " " + ex.getMessage());
        }
    }

//    private void ProcessConnect(ICheckedConsumer<Connection> callback) throws DaoException {
//        try {
//
//            Connection con = dbConnect.GetConnection();
//            try {
//                con.setAutoCommit(false);
//
//                callback.accept(con);
//
//                con.commit();
//            } catch (Exception ex) {
//                con.rollback();
//                con.close();
//
//                throw ex;
//            } finally {
//                con.close();
//            }
//        } catch (DaoException d) {
//            throw d;
//        } catch (Exception ex) {
//            throw new DaoException(ex.getMessage());
//        }
//    }
    //endregion PrivateMethod
}