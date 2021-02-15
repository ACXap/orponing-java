// This is a personal academic project. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: http://www.viva64.com
package com.rt.orponing.dao;

import com.rt.orponing.dao.data.*;
import com.rt.orponing.dao.interfaces.IDbAddress;
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
public class DbOrpon extends CommonDb implements IDbAddress {

    public DbOrpon(@Qualifier("dbConnectAddress") DbConnect dbConnect) {
        super(dbConnect,
                "SELECT 'public.nsi_mrf_geocoding_stat'::regclass",
                LoggerFactory.getLogger(DbOrpon.class));
    }

    //region PrivateField

    private static final String QUERY_SELECT_ADDRESS_BY_ID = "SELECT public.nsi_get_address_by_global_id(?)";
    private static final String QUERY_SELECT_ADDRESS_BY_IDS = "SELECT * from public.nsi_get_address_by_global_ids(?);";

    //endregion PrivateField

    //region PublicMethod

    @Override
    public String getAddress(Long globalId) throws DaoException {

        try (Connection con = dbConnect.GetConnection(); PreparedStatement ps = con.prepareStatement(QUERY_SELECT_ADDRESS_BY_ID)) {
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

        try (Connection con = dbConnect.GetConnection(); PreparedStatement ps = con.prepareStatement(QUERY_SELECT_ADDRESS_BY_IDS)) {
            ps.setArray(1, con.createArrayOf("INT8", ids.toArray()));
            ResultSet resultSet = ps.executeQuery();

            List<AddressGid> list = new ArrayList<>();

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

    //endregion PublicMethod
}