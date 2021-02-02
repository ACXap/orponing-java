// This is a personal academic project. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: http://www.viva64.com
package com.rt.orponing.service.data;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InfoService {
    private final String name;
    private final String id;
    private final String icon;
    private final String description;
    private final boolean isStartable;

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getIcon() {
        return icon;
    }

    public String getDescription() {
        return description;
    }

    public boolean getIsStartable() {
        return isStartable;
    }
}