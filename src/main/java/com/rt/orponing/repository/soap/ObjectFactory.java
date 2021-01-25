
package com.rt.orponing.repository.soap;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.rt.soap.ws.client.generated package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _ErrorField_QNAME = new QName("http://www.informatica.com/dis/ws/ws_", "ErrorField");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.rt.soap.ws.client.generated
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link AddressElementNameData }
     * 
     */
    public AddressElementNameData createAddressElementNameData() {
        return new AddressElementNameData();
    }

    /**
     * Create an instance of {@link AddressElementNameResponse2 }
     * 
     */
    public AddressElementNameResponse2 createAddressElementNameResponse2() {
        return new AddressElementNameResponse2();
    }

    /**
     * Create an instance of {@link AddressElementNameResponse2 .AddressElementResponseList2 }
     * 
     */
    public AddressElementNameResponse2 .AddressElementResponseList2 createAddressElementNameResponse2AddressElementResponseList2() {
        return new AddressElementNameResponse2 .AddressElementResponseList2();
    }

    /**
     * Create an instance of {@link AddressElementNameData.AddressElementFullNameList }
     * 
     */
    public AddressElementNameData.AddressElementFullNameList createAddressElementNameDataAddressElementFullNameList() {
        return new AddressElementNameData.AddressElementFullNameList();
    }

    /**
     * Create an instance of {@link AddressElementNameResponse2 .AddressElementResponseList2 .AddressElementNameGroup2 }
     * 
     */
    public AddressElementNameResponse2 .AddressElementResponseList2 .AddressElementNameGroup2 createAddressElementNameResponse2AddressElementResponseList2AddressElementNameGroup2() {
        return new AddressElementNameResponse2 .AddressElementResponseList2 .AddressElementNameGroup2();
    }

    /**
     * Create an instance of {@link AddressElementNameData.AddressElementFullNameList.AddressElementFullNameGroup }
     * 
     */
    public AddressElementNameData.AddressElementFullNameList.AddressElementFullNameGroup createAddressElementNameDataAddressElementFullNameListAddressElementFullNameGroup() {
        return new AddressElementNameData.AddressElementFullNameList.AddressElementFullNameGroup();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.informatica.com/dis/ws/ws_", name = "ErrorField")
    public JAXBElement<String> createErrorField(String value) {
        return new JAXBElement<String>(_ErrorField_QNAME, String.class, null, value);
    }
}