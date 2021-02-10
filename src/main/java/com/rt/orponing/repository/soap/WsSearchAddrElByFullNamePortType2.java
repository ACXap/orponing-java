// This is a personal academic project. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: http://www.viva64.com
package com.rt.orponing.repository.soap;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

@WebService(name = "wsSearchAddrElByFullNamePortType2", targetNamespace = "http://www.informatica.com/dis/ws/ws_")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@XmlSeeAlso({ObjectFactory.class})
public interface WsSearchAddrElByFullNamePortType2 {
    @WebMethod(operationName = "SearchAddressElementByFullName")
    @WebResult(name = "AddressElementNameResponse2", targetNamespace = "http://www.informatica.com/dis/ws/ws_", partName = "parameter")
    AddressElementNameResponse2 searchAddressElementByFullName(@WebParam(name = "AddressElementNameData", targetNamespace = "http://www.informatica.com/dis/ws/ws_", partName = "parameter")
                                                                       AddressElementNameData parameter) throws FaultMessage;
}