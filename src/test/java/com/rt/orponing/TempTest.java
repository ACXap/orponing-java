package com.rt.orponing;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;

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
    public void testDate() {
        Date date = new Date();

        Date temp = new Date(System.currentTimeMillis()+1);

        long i = date.getTime() - temp.getTime();
    }

}
