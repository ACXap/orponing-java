// This is a personal academic project. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: http://www.viva64.com
package com.rt.orponing.repository.data;

public class AddressInfo {

    public AddressInfo(int id, long globalId, String parsingLevelCode, String unparsedParts, String qualityCode, String checkStatus) {
        Id = id;
        GlobalId = globalId;
        ParsingLevelCode = parsingLevelCode;
        UnparsedParts = unparsedParts;
        QualityCode = qualityCode;
        CheckStatus = checkStatus;

        if (globalId < 0) {
            IsValid = false;
            Error = "The address cannot be parsed";
        } else {
            IsValid = true;
            Error = null;
        }
    }

    public AddressInfo(int id, String error) {
        Id = id;
        GlobalId = -1;
        IsValid = false;
        Error = error;

        ParsingLevelCode = null;
        UnparsedParts = null;
        QualityCode = null;
        CheckStatus = null;
    }

    public final int Id;
    public final long GlobalId;
    public final String ParsingLevelCode;
    public final String UnparsedParts;
    public final String QualityCode;
    public final String CheckStatus;
    public final boolean IsValid;
    public final String Error;
    public String AddressOrpon;
}