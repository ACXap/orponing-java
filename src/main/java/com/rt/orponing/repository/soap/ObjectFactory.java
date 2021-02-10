// This is a personal academic project. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: http://www.viva64.com
package com.rt.orponing.repository.soap;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

@XmlRegistry
public class ObjectFactory {

    private final static QName _ErrorField_QNAME = new QName("http://www.informatica.com/dis/ws/ws_", "ErrorField");

    public ObjectFactory() {
    }

    public AddressElementNameData createAddressElementNameData() {
        return new AddressElementNameData();
    }

    public AddressElementNameResponse2 createAddressElementNameResponse2() {
        return new AddressElementNameResponse2();
    }

    public AddressElementNameResponse2.AddressElementResponseList2 createAddressElementNameResponse2AddressElementResponseList2() {
        return new AddressElementNameResponse2.AddressElementResponseList2();
    }

    public AddressElementNameData.AddressElementFullNameList createAddressElementNameDataAddressElementFullNameList() {
        return new AddressElementNameData.AddressElementFullNameList();
    }

    public AddressElementNameResponse2.AddressElementResponseList2.AddressElementNameGroup2 createAddressElementNameResponse2AddressElementResponseList2AddressElementNameGroup2() {
        return new AddressElementNameResponse2.AddressElementResponseList2.AddressElementNameGroup2();
    }

    public AddressElementNameData.AddressElementFullNameList.AddressElementFullNameGroup createAddressElementNameDataAddressElementFullNameListAddressElementFullNameGroup() {
        return new AddressElementNameData.AddressElementFullNameList.AddressElementFullNameGroup();
    }

    @XmlElementDecl(namespace = "http://www.informatica.com/dis/ws/ws_", name = "ErrorField")
    public JAXBElement<String> createErrorField(String value) {
        return new JAXBElement<>(_ErrorField_QNAME, String.class, null, value);
    }
}