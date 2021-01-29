// This is a personal academic project. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: http://www.viva64.com
package com.rt.orponing.repository.soap;

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

    @XmlElement(name = "AddressElementResponseList2", required = true)
    protected AddressElementResponseList2 addressElementResponseList2;

    /**
     * Gets the value of the addressElementResponseList2 property.
     *
     * @return possible object is
     * {@link AddressElementNameResponse2 .AddressElementResponseList2 }
     */
    public AddressElementResponseList2 getAddressElementResponseList2() {
        return addressElementResponseList2;
    }

    /**
     * Sets the value of the addressElementResponseList2 property.
     *
     * @param value allowed object is
     *              {@link AddressElementNameResponse2 .AddressElementResponseList2 }
     */
    public void setAddressElementResponseList2(AddressElementResponseList2 value) {
        this.addressElementResponseList2 = value;
    }

    /**
     * <p>Java class for anonymous complex type.
     *
     * <p>The following schema fragment specifies the expected content contained within this class.
     *
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="AddressElementNameGroup2" maxOccurs="unbounded">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="QualityCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="CheckStatus" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="UnparsedParts" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="ParsingLevelCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="SystemCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="GlobalID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="KLADRLocalityId" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="FIASLocalityId" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="LocalityGlobalId" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="KLADRStreetId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="FIASStreetId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="StreetGlobalId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="Street" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="StreetKind" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="House" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="HouseLitera" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="CornerHouse" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="BuildingBlock" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="BuildingBlockLitera" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="Building" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="BuildingLitera" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="Ownership" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="OwnershipLitera" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="FIASHouseId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="HouseGlobalId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="Latitude" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="Longitude" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="LocationDescription" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "addressElementNameGroup2"
    })
    public static class AddressElementResponseList2 {

        @XmlElement(name = "AddressElementNameGroup2", required = true)
        protected List<AddressElementNameGroup2> addressElementNameGroup2;

        /**
         * Gets the value of the addressElementNameGroup2 property.
         *
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the addressElementNameGroup2 property.
         *
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getAddressElementNameGroup2().add(newItem);
         * </pre>
         *
         *
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link AddressElementNameResponse2 .AddressElementResponseList2 .AddressElementNameGroup2 }
         */
        public List<AddressElementNameGroup2> getAddressElementNameGroup2() {
            if (addressElementNameGroup2 == null) {
                addressElementNameGroup2 = new ArrayList<AddressElementNameGroup2>();
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

            /**
             * Gets the value of the qualityCode property.
             *
             * @return possible object is
             * {@link String }
             */
            public String getQualityCode() {
                return qualityCode;
            }

            /**
             * Sets the value of the qualityCode property.
             *
             * @param value allowed object is
             *              {@link String }
             */
            public void setQualityCode(String value) {
                this.qualityCode = value;
            }

            /**
             * Gets the value of the checkStatus property.
             *
             * @return possible object is
             * {@link String }
             */
            public String getCheckStatus() {
                return checkStatus;
            }

            /**
             * Sets the value of the checkStatus property.
             *
             * @param value allowed object is
             *              {@link String }
             */
            public void setCheckStatus(String value) {
                this.checkStatus = value;
            }

            /**
             * Gets the value of the unparsedParts property.
             *
             * @return possible object is
             * {@link String }
             */
            public String getUnparsedParts() {
                return unparsedParts;
            }

            /**
             * Sets the value of the unparsedParts property.
             *
             * @param value allowed object is
             *              {@link String }
             */
            public void setUnparsedParts(String value) {
                this.unparsedParts = value;
            }

            /**
             * Gets the value of the parsingLevelCode property.
             *
             * @return possible object is
             * {@link String }
             */
            public String getParsingLevelCode() {
                return parsingLevelCode;
            }

            /**
             * Sets the value of the parsingLevelCode property.
             *
             * @param value allowed object is
             *              {@link String }
             */
            public void setParsingLevelCode(String value) {
                this.parsingLevelCode = value;
            }

            /**
             * Gets the value of the systemCode property.
             *
             * @return possible object is
             * {@link String }
             */
            public String getSystemCode() {
                return systemCode;
            }

            /**
             * Sets the value of the systemCode property.
             *
             * @param value allowed object is
             *              {@link String }
             */
            public void setSystemCode(String value) {
                this.systemCode = value;
            }

            /**
             * Gets the value of the globalID property.
             *
             * @return possible object is
             * {@link String }
             */
            public String getGlobalID() {
                return globalID;
            }

            /**
             * Sets the value of the globalID property.
             *
             * @param value allowed object is
             *              {@link String }
             */
            public void setGlobalID(String value) {
                this.globalID = value;
            }

            /**
             * Gets the value of the kladrLocalityId property.
             *
             * @return possible object is
             * {@link String }
             */
            public String getKLADRLocalityId() {
                return kladrLocalityId;
            }

            /**
             * Sets the value of the kladrLocalityId property.
             *
             * @param value allowed object is
             *              {@link String }
             */
            public void setKLADRLocalityId(String value) {
                this.kladrLocalityId = value;
            }

            /**
             * Gets the value of the fiasLocalityId property.
             *
             * @return possible object is
             * {@link String }
             */
            public String getFIASLocalityId() {
                return fiasLocalityId;
            }

            /**
             * Sets the value of the fiasLocalityId property.
             *
             * @param value allowed object is
             *              {@link String }
             */
            public void setFIASLocalityId(String value) {
                this.fiasLocalityId = value;
            }

            /**
             * Gets the value of the localityGlobalId property.
             *
             * @return possible object is
             * {@link String }
             */
            public String getLocalityGlobalId() {
                return localityGlobalId;
            }

            /**
             * Sets the value of the localityGlobalId property.
             *
             * @param value allowed object is
             *              {@link String }
             */
            public void setLocalityGlobalId(String value) {
                this.localityGlobalId = value;
            }

            /**
             * Gets the value of the kladrStreetId property.
             *
             * @return possible object is
             * {@link String }
             */
            public String getKLADRStreetId() {
                return kladrStreetId;
            }

            /**
             * Sets the value of the kladrStreetId property.
             *
             * @param value allowed object is
             *              {@link String }
             */
            public void setKLADRStreetId(String value) {
                this.kladrStreetId = value;
            }

            /**
             * Gets the value of the fiasStreetId property.
             *
             * @return possible object is
             * {@link String }
             */
            public String getFIASStreetId() {
                return fiasStreetId;
            }

            /**
             * Sets the value of the fiasStreetId property.
             *
             * @param value allowed object is
             *              {@link String }
             */
            public void setFIASStreetId(String value) {
                this.fiasStreetId = value;
            }

            /**
             * Gets the value of the streetGlobalId property.
             *
             * @return possible object is
             * {@link String }
             */
            public String getStreetGlobalId() {
                return streetGlobalId;
            }

            /**
             * Sets the value of the streetGlobalId property.
             *
             * @param value allowed object is
             *              {@link String }
             */
            public void setStreetGlobalId(String value) {
                this.streetGlobalId = value;
            }

            /**
             * Gets the value of the street property.
             *
             * @return possible object is
             * {@link String }
             */
            public String getStreet() {
                return street;
            }

            /**
             * Sets the value of the street property.
             *
             * @param value allowed object is
             *              {@link String }
             */
            public void setStreet(String value) {
                this.street = value;
            }

            /**
             * Gets the value of the streetKind property.
             *
             * @return possible object is
             * {@link String }
             */
            public String getStreetKind() {
                return streetKind;
            }

            /**
             * Sets the value of the streetKind property.
             *
             * @param value allowed object is
             *              {@link String }
             */
            public void setStreetKind(String value) {
                this.streetKind = value;
            }

            /**
             * Gets the value of the house property.
             *
             * @return possible object is
             * {@link String }
             */
            public String getHouse() {
                return house;
            }

            /**
             * Sets the value of the house property.
             *
             * @param value allowed object is
             *              {@link String }
             */
            public void setHouse(String value) {
                this.house = value;
            }

            /**
             * Gets the value of the houseLitera property.
             *
             * @return possible object is
             * {@link String }
             */
            public String getHouseLitera() {
                return houseLitera;
            }

            /**
             * Sets the value of the houseLitera property.
             *
             * @param value allowed object is
             *              {@link String }
             */
            public void setHouseLitera(String value) {
                this.houseLitera = value;
            }

            /**
             * Gets the value of the cornerHouse property.
             *
             * @return possible object is
             * {@link String }
             */
            public String getCornerHouse() {
                return cornerHouse;
            }

            /**
             * Sets the value of the cornerHouse property.
             *
             * @param value allowed object is
             *              {@link String }
             */
            public void setCornerHouse(String value) {
                this.cornerHouse = value;
            }

            /**
             * Gets the value of the buildingBlock property.
             *
             * @return possible object is
             * {@link String }
             */
            public String getBuildingBlock() {
                return buildingBlock;
            }

            /**
             * Sets the value of the buildingBlock property.
             *
             * @param value allowed object is
             *              {@link String }
             */
            public void setBuildingBlock(String value) {
                this.buildingBlock = value;
            }

            /**
             * Gets the value of the buildingBlockLitera property.
             *
             * @return possible object is
             * {@link String }
             */
            public String getBuildingBlockLitera() {
                return buildingBlockLitera;
            }

            /**
             * Sets the value of the buildingBlockLitera property.
             *
             * @param value allowed object is
             *              {@link String }
             */
            public void setBuildingBlockLitera(String value) {
                this.buildingBlockLitera = value;
            }

            /**
             * Gets the value of the building property.
             *
             * @return possible object is
             * {@link String }
             */
            public String getBuilding() {
                return building;
            }

            /**
             * Sets the value of the building property.
             *
             * @param value allowed object is
             *              {@link String }
             */
            public void setBuilding(String value) {
                this.building = value;
            }

            /**
             * Gets the value of the buildingLitera property.
             *
             * @return possible object is
             * {@link String }
             */
            public String getBuildingLitera() {
                return buildingLitera;
            }

            /**
             * Sets the value of the buildingLitera property.
             *
             * @param value allowed object is
             *              {@link String }
             */
            public void setBuildingLitera(String value) {
                this.buildingLitera = value;
            }

            /**
             * Gets the value of the ownership property.
             *
             * @return possible object is
             * {@link String }
             */
            public String getOwnership() {
                return ownership;
            }

            /**
             * Sets the value of the ownership property.
             *
             * @param value allowed object is
             *              {@link String }
             */
            public void setOwnership(String value) {
                this.ownership = value;
            }

            /**
             * Gets the value of the ownershipLitera property.
             *
             * @return possible object is
             * {@link String }
             */
            public String getOwnershipLitera() {
                return ownershipLitera;
            }

            /**
             * Sets the value of the ownershipLitera property.
             *
             * @param value allowed object is
             *              {@link String }
             */
            public void setOwnershipLitera(String value) {
                this.ownershipLitera = value;
            }

            /**
             * Gets the value of the fiasHouseId property.
             *
             * @return possible object is
             * {@link String }
             */
            public String getFIASHouseId() {
                return fiasHouseId;
            }

            /**
             * Sets the value of the fiasHouseId property.
             *
             * @param value allowed object is
             *              {@link String }
             */
            public void setFIASHouseId(String value) {
                this.fiasHouseId = value;
            }

            /**
             * Gets the value of the houseGlobalId property.
             *
             * @return possible object is
             * {@link String }
             */
            public String getHouseGlobalId() {
                return houseGlobalId;
            }

            /**
             * Sets the value of the houseGlobalId property.
             *
             * @param value allowed object is
             *              {@link String }
             */
            public void setHouseGlobalId(String value) {
                this.houseGlobalId = value;
            }

            /**
             * Gets the value of the latitude property.
             *
             * @return possible object is
             * {@link String }
             */
            public String getLatitude() {
                return latitude;
            }

            /**
             * Sets the value of the latitude property.
             *
             * @param value allowed object is
             *              {@link String }
             */
            public void setLatitude(String value) {
                this.latitude = value;
            }

            /**
             * Gets the value of the longitude property.
             *
             * @return possible object is
             * {@link String }
             */
            public String getLongitude() {
                return longitude;
            }

            /**
             * Sets the value of the longitude property.
             *
             * @param value allowed object is
             *              {@link String }
             */
            public void setLongitude(String value) {
                this.longitude = value;
            }

            /**
             * Gets the value of the locationDescription property.
             *
             * @return possible object is
             * {@link String }
             */
            public String getLocationDescription() {
                return locationDescription;
            }

            /**
             * Sets the value of the locationDescription property.
             *
             * @param value allowed object is
             *              {@link String }
             */
            public void setLocationDescription(String value) {
                this.locationDescription = value;
            }
        }
    }
}