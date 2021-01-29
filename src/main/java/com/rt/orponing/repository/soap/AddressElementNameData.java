// This is a personal academic project. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: http://www.viva64.com
package com.rt.orponing.repository.soap;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "addressElementFullNameList"
})
@XmlRootElement(name = "AddressElementNameData")
public class AddressElementNameData {

    @XmlElement(name = "AddressElementFullNameList", required = true)
    protected AddressElementFullNameList addressElementFullNameList;

    /**
     * Gets the value of the addressElementFullNameList property.
     * 
     * @return
     *     possible object is
     *     {@link AddressElementFullNameList }
     *     
     */
    public AddressElementFullNameList getAddressElementFullNameList() {
        return addressElementFullNameList;
    }

    /**
     * Sets the value of the addressElementFullNameList property.
     * 
     * @param value
     *     allowed object is
     *     {@link AddressElementFullNameList }
     *     
     */
    public void setAddressElementFullNameList(AddressElementFullNameList value) {
        this.addressElementFullNameList = value;
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
     *         &lt;element name="AddressElementFullNameGroup" maxOccurs="unbounded">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="FullAddress" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="District" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="City" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="Locality" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="Street" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="House" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="MaxResult" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
     *                   &lt;element name="SystemCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "addressElementFullNameGroup"
    })
    public static class AddressElementFullNameList {

        @XmlElement(name = "AddressElementFullNameGroup", required = true)
        protected List<AddressElementFullNameGroup> addressElementFullNameGroup;

        /**
         * Gets the value of the addressElementFullNameGroup property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the addressElementFullNameGroup property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getAddressElementFullNameGroup().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link AddressElementFullNameGroup }
         * 
         * 
         */
        public List<AddressElementFullNameGroup> getAddressElementFullNameGroup() {
            if (addressElementFullNameGroup == null) {
                addressElementFullNameGroup = new ArrayList<AddressElementFullNameGroup>();
            }
            return this.addressElementFullNameGroup;
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
         *         &lt;element name="FullAddress" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="District" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="City" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="Locality" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="Street" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="House" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="MaxResult" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
         *         &lt;element name="SystemCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "fullAddress",
            "district",
            "city",
            "locality",
            "street",
            "house",
            "maxResult",
            "systemCode"
        })
        public static class AddressElementFullNameGroup {

            @XmlElement(name = "FullAddress", required = true)
            protected String fullAddress;
            @XmlElement(name = "District")
            protected String district;
            @XmlElement(name = "City")
            protected String city;
            @XmlElement(name = "Locality")
            protected String locality;
            @XmlElement(name = "Street")
            protected String street;
            @XmlElement(name = "House")
            protected String house;
            @XmlElement(name = "MaxResult")
            protected BigInteger maxResult;
            @XmlElement(name = "SystemCode", required = true)
            protected String systemCode;

            /**
             * Gets the value of the fullAddress property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getFullAddress() {
                return fullAddress;
            }

            /**
             * Sets the value of the fullAddress property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setFullAddress(String value) {
                this.fullAddress = value;
            }

            /**
             * Gets the value of the district property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDistrict() {
                return district;
            }

            /**
             * Sets the value of the district property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDistrict(String value) {
                this.district = value;
            }

            /**
             * Gets the value of the city property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getCity() {
                return city;
            }

            /**
             * Sets the value of the city property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setCity(String value) {
                this.city = value;
            }

            /**
             * Gets the value of the locality property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getLocality() {
                return locality;
            }

            /**
             * Sets the value of the locality property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setLocality(String value) {
                this.locality = value;
            }

            /**
             * Gets the value of the street property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getStreet() {
                return street;
            }

            /**
             * Sets the value of the street property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setStreet(String value) {
                this.street = value;
            }

            /**
             * Gets the value of the house property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getHouse() {
                return house;
            }

            /**
             * Sets the value of the house property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setHouse(String value) {
                this.house = value;
            }

            /**
             * Gets the value of the maxResult property.
             * 
             * @return
             *     possible object is
             *     {@link BigInteger }
             *     
             */
            public BigInteger getMaxResult() {
                return maxResult;
            }

            /**
             * Sets the value of the maxResult property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigInteger }
             *     
             */
            public void setMaxResult(BigInteger value) {
                this.maxResult = value;
            }

            /**
             * Gets the value of the systemCode property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSystemCode() {
                return systemCode;
            }

            /**
             * Sets the value of the systemCode property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSystemCode(String value) {
                this.systemCode = value;
            }
        }
    }
}