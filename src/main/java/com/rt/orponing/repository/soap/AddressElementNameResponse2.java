// This is a personal academic project. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: http://www.viva64.com
package com.rt.orponing.repository.soap;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"addressElementResponseList2"})
@XmlRootElement(name = "AddressElementNameResponse2")
public class AddressElementNameResponse2 {

    public AddressElementResponseList2 getAddressElementResponseList2() {
        return addressElementResponseList2;
    }

    public void setAddressElementResponseList2(AddressElementResponseList2 value) {
        this.addressElementResponseList2 = value;
    }

    @XmlElement(name = "AddressElementResponseList2", required = true)
    protected AddressElementResponseList2 addressElementResponseList2;

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"addressElementNameGroup2"})
    public static class AddressElementResponseList2 {

        @XmlElement(name = "AddressElementNameGroup2", required = true)
        protected List<AddressElementNameGroup2> addressElementNameGroup2;

        public List<AddressElementNameGroup2> getAddressElementNameGroup2() {
            if (addressElementNameGroup2 == null) {
                addressElementNameGroup2 = new ArrayList<>();
            }
            return this.addressElementNameGroup2;
        }

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
                "qualityCode",
                "checkStatus",
                "unparsedParts",
                "parsingLevelCode",
                "systemCode",
                "globalID",
                "kladrLocalityId",
                "fiasLocalityId",
                "localityGlobalId",
                "kladrStreetId",
                "fiasStreetId",
                "streetGlobalId",
                "street",
                "streetKind",
                "house",
                "houseLitera",
                "cornerHouse",
                "buildingBlock",
                "buildingBlockLitera",
                "building",
                "buildingLitera",
                "ownership",
                "ownershipLitera",
                "fiasHouseId",
                "houseGlobalId",
                "latitude",
                "longitude",
                "locationDescription"
        })

        @Getter
        @Setter
        public static class AddressElementNameGroup2 {
            @XmlElement(name = "QualityCode", required = true)
            protected String qualityCode;
            @XmlElement(name = "CheckStatus", required = true)
            protected String checkStatus;
            @XmlElement(name = "UnparsedParts")
            protected String unparsedParts;
            @XmlElement(name = "ParsingLevelCode")
            protected String parsingLevelCode;
            @XmlElement(name = "SystemCode", required = true)
            protected String systemCode;
            @XmlElement(name = "GlobalID")
            protected String globalID;
            @XmlElement(name = "KLADRLocalityId", required = true)
            protected String kladrLocalityId;
            @XmlElement(name = "FIASLocalityId", required = true)
            protected String fiasLocalityId;
            @XmlElement(name = "LocalityGlobalId", required = true)
            protected String localityGlobalId;
            @XmlElement(name = "KLADRStreetId")
            protected String kladrStreetId;
            @XmlElement(name = "FIASStreetId")
            protected String fiasStreetId;
            @XmlElement(name = "StreetGlobalId")
            protected String streetGlobalId;
            @XmlElement(name = "Street")
            protected String street;
            @XmlElement(name = "StreetKind")
            protected String streetKind;
            @XmlElement(name = "House")
            protected String house;
            @XmlElement(name = "HouseLitera")
            protected String houseLitera;
            @XmlElement(name = "CornerHouse")
            protected String cornerHouse;
            @XmlElement(name = "BuildingBlock")
            protected String buildingBlock;
            @XmlElement(name = "BuildingBlockLitera")
            protected String buildingBlockLitera;
            @XmlElement(name = "Building")
            protected String building;
            @XmlElement(name = "BuildingLitera")
            protected String buildingLitera;
            @XmlElement(name = "Ownership")
            protected String ownership;
            @XmlElement(name = "OwnershipLitera")
            protected String ownershipLitera;
            @XmlElement(name = "FIASHouseId")
            protected String fiasHouseId;
            @XmlElement(name = "HouseGlobalId")
            protected String houseGlobalId;
            @XmlElement(name = "Latitude", required = true)
            protected String latitude;
            @XmlElement(name = "Longitude", required = true)
            protected String longitude;
            @XmlElement(name = "LocationDescription", required = true)
            protected String locationDescription;
        }
    }
}