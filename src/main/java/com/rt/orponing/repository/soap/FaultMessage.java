
package com.rt.orponing.repository.soap;

import javax.xml.ws.WebFault;


@WebFault(name = "ErrorField", targetNamespace = "http://www.informatica.com/dis/ws/ws_")
public class FaultMessage extends Exception
{

    /**
     * Java type that goes as soapenv:Fault detail element.
     * 
     */
    private String faultInfo;

    public FaultMessage(String message, String faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    public FaultMessage(String message, String faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    public String getFaultInfo() {
        return faultInfo;
    }
}