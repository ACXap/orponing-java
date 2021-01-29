// This is a personal academic project. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: http://www.viva64.com
package com.rt.orponing.service;

import com.rt.orponing.dao.data.DbConnectProperty;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

@Component
public class PropertyService {

    public PropertyService() throws Exception {
        Initialization();
    }

    //region PrivateField
    private static final String FILE_INI = "settings.ini";
    //endregion PrivateField

    //region PublicProperty
    public String UrlService;
    public DbConnectProperty DbConnectProperty;
    public String PathTempFile;
    public int PartitionSizePars = 200;
    //endregion PublicProperty

    //region PublicMethod
    public void Initialization() throws Exception {
        Properties props = new Properties();

        try (FileInputStream fs = new FileInputStream(FILE_INI);
             InputStreamReader sr = new InputStreamReader(fs, StandardCharsets.UTF_8)) {
            props.load(sr);
        }

        UrlService = props.getProperty("UrlService");
        DbConnectProperty = new DbConnectProperty(props.getProperty("DbServer"),
                Integer.parseInt(props.getProperty("DbPort")),
                props.getProperty("DbName"),
                props.getProperty("DbUser"),
                props.getProperty("DbPassword"),
                props.getProperty("DbSchema"));
        PathTempFile = props.getProperty("PathTempFile");
        PartitionSizePars = ParseInt(props.getProperty("PartitionSizePars"), PartitionSizePars);

        CheckProperty();
    }
    //endregion PublicMethod

    //region PrivateMethod
    private void CheckProperty() throws Exception {
        if (UrlService.isEmpty()) throw new Exception("UrlService empty");
    }

    private static int ParseInt(String content, int defValue) {
        try {
            return Integer.parseInt(content);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            return defValue;
        }
    }
    //endregion PrivateMethod
}