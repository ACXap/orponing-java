// This is a personal academic project. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: http://www.viva64.com
package com.rt.orponing.dao;

import com.rt.orponing.service.PropertyService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class QueryGeneratorMdmSaveData {

    public QueryGeneratorMdmSaveData(PropertyService propertyService) {
        String schema = propertyService.DbConnectProperty.DbSchema;

        FUNCTION_GET_ADDRESS = schema + "nsi_temp_orponing_select_input_data";
        PROCEDURE_ADD_ADDRESS_INFO = schema + "nsi_temp_orponing_update_output_data";
        FUNCTION_TEST_BD = schema + "nsi_temp_orponing_test_bd";

        TABLE_ORPONING = schema + "nsi_temp_orponing";
    }

    private final String FUNCTION_GET_ADDRESS;
    private final String PROCEDURE_ADD_ADDRESS_INFO;
    private final String FUNCTION_TEST_BD;

    private final String TABLE_ORPONING;

    public String SelectAddress() {
        return "Select * from " + FUNCTION_GET_ADDRESS + "();";
    }

    public String UpdateAddressInfo() {
        return "Call " + PROCEDURE_ADD_ADDRESS_INFO + "(?,?,?,?,?,?,?,?)";
    }

    public String TestBd() {
        return "Select " + FUNCTION_TEST_BD + "()";
    }
}