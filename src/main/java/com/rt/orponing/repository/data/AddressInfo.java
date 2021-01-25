package com.rt.orponing.repository.data;

public class AddressInfo {
    public final int Id;
    public final long GlobalId;
    public final String ParsingLevelCode;
    public final String UnparsedParts;
    public final String QualityCode;
    public final String CheckStatus;

    public String AddressOrpon;

    public AddressInfo(int id, long globalId, String parsingLevelCode, String unparsedParts, String qualityCode, String checkStatus) {
        Id = id;
        GlobalId = globalId;
        ParsingLevelCode = parsingLevelCode;
        UnparsedParts = unparsedParts;
        QualityCode = qualityCode;
        CheckStatus = checkStatus;
    }

    public static AddressInfo GetErrorAddressInfo(){
        return new AddressInfo(0,0, "Error", "Error", "Error", "Error");
    }
}