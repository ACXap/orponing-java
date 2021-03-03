package com.rt.orponing.repository.data;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ResponseAddressInfo {
    public final EntityAddress RequestAddress;
    public final AddressInfo ResponseAddress;
}