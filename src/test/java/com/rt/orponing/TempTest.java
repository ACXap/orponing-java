package com.rt.orponing;

import com.google.common.collect.Lists;
import com.rt.orponing.dao.DbMdmSaveData;
import com.rt.orponing.dao.data.DaoException;
import com.rt.orponing.dao.queryGenerator.QueryGeneratorMdmSaveData;
import com.rt.orponing.repository.data.AddressInfo;
import com.rt.orponing.repository.data.EntityAddress;
import com.rt.orponing.service.PropertyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

public class TempTest {

    @Test
    public void testPartition(){
        int count = 0;
        int max = 100000;

        int countA = max / 400;

        List<Integer> collection = IntStream.rangeClosed(1, 100000) .boxed().collect(Collectors.toList());

       for(List<Integer> list : Lists.partition(collection, 400)){
           count++;
       }

       assertEquals(countA, count);
    }

    @Test
    public void testDate() throws ParseException {
        Date date = new Date();

        Date temp = new Date(System.currentTimeMillis()+1);

        long i = date.getTime() - temp.getTime();
    }

}
