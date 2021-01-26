package com.rt.orponing.repository.data;

public class EntityAddressError {

    public EntityAddressError(EntityAddress address, String error) {
        Address = address;
        Error = error;
    }

    public final EntityAddress Address;
    public final String Error;
}