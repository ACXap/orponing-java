package com.rt.orponing.dao.queryGenerator;


import com.rt.orponing.service.PropertyService;
import org.springframework.stereotype.Component;

@Component
public class QueryGeneratorMdmSaveData {

    public QueryGeneratorMdmSaveData(PropertyService propertyService) {
       String schema = propertyService.DbConnectProperty.DbSchema;

        FUNCTION_GET_ADDRESS = schema + "nsi_temp_orponing_select_input_data";
        PROCEDURE_ADD_ADDRESS_INFO = schema + "nsi_temp_orponing_update_output_data";
        PROCEDURE_ADD_ERROR = schema + "nsi_temp_orponing_update_output_data_error";

        TABLE_ORPONING = schema + "nsi_temp_orponing";
    }

    private final String FUNCTION_GET_ADDRESS;
    private final String PROCEDURE_ADD_ADDRESS_INFO;
    private final String PROCEDURE_ADD_ERROR;

    private final String TABLE_ORPONING;

    public String SelectAddress() {
        return "Select * from " + FUNCTION_GET_ADDRESS + "();";
    }

    public String UpdateAddressInfo() {
        return "Call " + PROCEDURE_ADD_ADDRESS_INFO + "(?,?,?,?,?,?,?)";
    }
    public String UpdateAddressInfoError() {
        return "Call " + PROCEDURE_ADD_ERROR + "(?,?)";
    }
}