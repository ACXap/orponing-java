package com.rt.orponing.dao;

import com.rt.orponing.dao.data.AddressGid;
import com.rt.orponing.dao.data.DaoException;
import com.rt.orponing.dao.data.DbConnect;
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
public class DbOrponAddress extends CommonDb implements IDbAddress {

    public DbOrponAddress(@Qualifier("dbConnectAddress") DbConnect dbConnect) {
        this.dbConnect = dbConnect;
        queryTestDb = "SELECT 'public.nsi_mrf_geocoding_stat'::regclass";
    }

    private final String querySelectAddressById = "SELECT public.nsi_get_address_by_global_id(?)";
    private final String querySelectAddressByIds = "SELECT * from public.nsi_get_address_by_global_ids(?);";

    @Override
    public String getAddress(Long globalId) throws DaoException {

        try (Connection con = dbConnect.GetConnection(); PreparedStatement ps = con.prepareStatement(querySelectAddressById)) {
            ps.setLong(1, globalId);
            ResultSet resultSet = ps.executeQuery();

            resultSet.next();
            return resultSet.getString(1);

        } catch (DaoException d) {
            throw d;
        } catch (Exception ex) {
            throw new DaoException(ex.getMessage(), ex);
        }
    }

    @Override
    public List<AddressGid> getAddress(List<Long> ids) throws DaoException {

        List<AddressGid> list = new ArrayList<>();

        try (Connection con = dbConnect.GetConnection(); PreparedStatement ps = con.prepareStatement(querySelectAddressByIds)) {
            ps.setArray(1, con.createArrayOf("INT8", ids.toArray()));
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                Long id = resultSet.getLong(1);
                String address = resultSet.getString(2);
                list.add(new AddressGid(id, address));
            }

            return list;

        } catch (DaoException d) {
            throw d;
        } catch (Exception ex) {
            throw new DaoException(ex.getMessage(), ex);
        }
    }

    //endregion PrivateField
}