// This is a personal academic project. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: http://www.viva64.com
package com.rt.orponing.repository.soap;

import javax.xml.ws.WebFault;

@WebFault(name = "ErrorField", targetNamespace = "http://www.informatica.com/dis/ws/ws_")
public class FaultMessage extends Exception
{
    private final String faultInfo;

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