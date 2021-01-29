// This is a personal academic project. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: http://www.viva64.com
package com.rt.orponing.repository.soap;

import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;

public class WsSearchAddressElementByFullName2  extends Service
{
    private static final String NAMESPACE_URL = "http://www.informatica.com/dis/ws/ws_";

    public WsSearchAddressElementByFullName2(String url) throws Exception {
        super(new URL(url), new QName(NAMESPACE_URL, "ws_SearchAddressElementByFullName2"));
    }

    @WebEndpoint(name = "WsSearchAddrElByFullNamePortTypeImpl2Port")
    public WsSearchAddrElByFullNamePortType2 getWsSearchAddrElByFullNamePortTypeImpl2Port() {
        return super.getPort(new QName(NAMESPACE_URL, "WsSearchAddrElByFullNamePortTypeImpl2Port"), WsSearchAddrElByFullNamePortType2.class);
    }
}