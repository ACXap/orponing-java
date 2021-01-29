// This is a personal academic project. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: http://www.viva64.com
package com.rt.orponing.repository.data;

public class EntityAddress {
    public final int Id;
    public final String Address;

    public EntityAddress(int id, String address) {
        Id = id;
        Address = address;
    }
}