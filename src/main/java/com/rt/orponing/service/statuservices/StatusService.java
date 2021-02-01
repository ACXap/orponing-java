// This is a personal academic project. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: http://www.viva64.com
package com.rt.orponing.service.statuservices;

import com.rt.orponing.service.data.Status;

public abstract class StatusService {

    protected String _name;
    protected String _id;
    protected String _icon;
    protected String _description = "";
    protected Status _status;
    protected boolean _isStartable;

    public String getName() {
        return _name;
    }

    public String getId() {
        return _id;
    }

    public String getIcon() {
        return _icon;
    }

    public String getDescription() {
        return _description;
    }

    public boolean getIsStartable() {
        return _isStartable;
    }

    public abstract Status getStatus();
}