package com.ksggroup.altanet.altasocial.Model;


import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;

public class SearchUserReq implements KvmSerializable {
    private String name;

    public String getName() {
            return name;
        }

    public void setName(String name) {
            this.name = name;
        }

    public SearchUserReq(String name) {
        this.name = name;
    }

    @Override
    public Object getProperty(int i) {
        return name;
    }

    @Override
    public int getPropertyCount() {
        return 1;
    }

    @Override
    public void setProperty(int i, Object o) {
        this.name = o.toString();
    }

    @Override
    public void getPropertyInfo(int i, Hashtable hashtable, PropertyInfo propertyInfo) {
        propertyInfo.setName("name");
        propertyInfo.setType(String.class);
    }
}
