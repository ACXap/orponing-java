package com.rt.orponing.dao;

import com.rt.orponing.dao.data.ICheckedConsumer;
import com.rt.orponing.dao.queryGenerator.QueryGeneratorMdmSaveData;
import com.rt.orponing.repository.data.AddressInfo;
import com.rt.orponing.repository.data.EntityAddress;
import com.rt.orponing.service.PropertyService;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Component
public class DbMdmSaveData implements IDbSaveData{

    public DbMdmSaveData(PropertyService propertyService, QueryGeneratorMdmSaveData queryGenerator){
        _dbConnectProperty = propertyService.DbConnectProperty;
        _queryGenerator = queryGenerator;
    }

    protected final QueryGeneratorMdmSaveData _queryGenerator;
    protected final DbConnectProperty _dbConnectProperty;

    @Override
    public List<EntityAddress> GetEntityAddress() throws DaoException {
        List<EntityAddress> list = new ArrayList<>();
        String query = _queryGenerator.SelectAddress();

        try (Connection con = _dbConnectProperty.GetConnection(); PreparedStatement ps = con.prepareStatement(query)) {
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String address = resultSet.getString(2);
                list.add(new EntityAddress(id, address));
            }
        } catch (DaoException d) {
            throw d;
        } catch (Exception ex) {
            throw new DaoException(ex.getMessage());
        }

        return list;
    }

    @Override
    public void AddAddressInfo(List<AddressInfo> collectionAddressInfo) throws DaoException {
        ProcessConnect(con -> AddAddressInfo(con, collectionAddressInfo));
    }

    @Override
    public void AddAddressInfoError(List<EntityAddress> collectionAddress, String error) throws DaoException {
        ProcessConnect(con -> AddAddressInfoError(con, collectionAddress, error));
    }

    public void AddAddressInfo(Connection con, List<AddressInfo> collectionAddressInfo) throws DaoException {
        String query = _queryGenerator.UpdateAddressInfo();

        try (PreparedStatement ps = con.prepareStatement(query)) {
            for (AddressInfo a : collectionAddressInfo) {

                ps.setInt(1, a.Id);
                ps.setLong(2, a.GlobalId);
                ps.setString(3, a.AddressOrpon);
                ps.setString(4, a.ParsingLevelCode);
                ps.setString(5, a.UnparsedParts);
                ps.setString(6, a.QualityCode);
                ps.setString(7, a.CheckStatus);

                ps.addBatch();
            }
            ps.executeBatch();
        } catch (Exception ex) {
            throw new DaoException("Ошибка обработки запроса: " + query + " " + ex.getMessage());
        }
    }

    private void AddAddressInfoError(Connection con, List<EntityAddress> collectionAddress, String error) throws DaoException {
        String query = _queryGenerator.UpdateAddressInfoError();

        try (PreparedStatement ps = con.prepareStatement(query)) {
            for (EntityAddress address : collectionAddress) {

                ps.setInt(1, address.Id);
                ps.setString(2, error);

                ps.addBatch();
            }
            ps.executeBatch();
        } catch (Exception ex) {
            throw new DaoException("Ошибка обработки запроса: " + query + " " + ex.getMessage());
        }
    }

    private void ProcessConnect(ICheckedConsumer<Connection> callback) throws DaoException {
        try {

            Connection con = _dbConnectProperty.GetConnection();
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