// This is a personal academic project. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: http://www.viva64.com
package com.rt.orponing.repository.soap;

import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"addressElementFullNameList"})
@XmlRootElement(name = "AddressElementNameData")
public class AddressElementNameData {

    @XmlElement(name = "AddressElementFullNameList", required = true)
    protected AddressElementFullNameList addressElementFullNameList;

    public AddressElementFullNameList getAddressElementFullNameList() {
        return addressElementFullNameList;
    }

    public void setAddressElementFullNameList(AddressElementFullNameList value) {
        this.addressElementFullNameList = value;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"addressElementFullNameGroup"})
    public static class AddressElementFullNameList {

        @XmlElement(name = "AddressElementFullNameGroup", required = true)
        protected List<AddressElementFullNameGroup> addressElementFullNameGroup;

        public List<AddressElementFullNameGroup> getAddressElementFullNameGroup() {
            if (addressElementFullNameGroup == null) {
                addressElementFullNameGroup = new ArrayList<>();
            }
            return this.addressElementFullNameGroup;
        }

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {"fullAddress", "systemCode"})
        @Setter
        public static class AddressElementFullNameGroup {
            @XmlElement(name = "FullAddress", required = true)
            protected String fullAddress;
            @XmlElement(name = "SystemCode", required = true)
            protected String systemCode;
        }
    }
}