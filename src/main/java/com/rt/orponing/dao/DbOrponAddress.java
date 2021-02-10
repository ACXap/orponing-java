package com.rt.orponing.dao;

import com.rt.orponing.dao.data.DaoException;
import com.rt.orponing.dao.data.DbConnect;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Component
@Lazy
public class DbOrponAddress extends CommonDb implements IDbAddress {

    public DbOrponAddress(@Qualifier("dbConnectAddress") DbConnect dbConnect) {
        this.dbConnect = dbConnect;
        queryTestDb = "Select 'public.nsi_mrf_geocoding_stat'::regclass";
    }

    private final String querySelectAddress = "SELECT * from public.nsi_get_address_by_global_id(?);";

    @Override
    public String getAddress(Long globalId) throws DaoException {

        try (Connection con = dbConnect.GetConnection(); PreparedStatement ps = con.prepareStatement(querySelectAddress)) {
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

    //endregion PrivateField
}