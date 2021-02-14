// This is a personal academic project. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: http://www.viva64.com
package com.rt.orponing.service.data;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class InfoService {
    @Getter
    private final String name;
    @Getter
    private final String id;
    @Getter
    private final String icon;
    @Getter
    private final String description;
    private final boolean isStartable;

    public boolean getIsStartable() {
        return isStartable;
    }
}