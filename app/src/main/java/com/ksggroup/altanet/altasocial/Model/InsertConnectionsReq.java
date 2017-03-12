package com.ksggroup.altanet.altasocial.Model;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;

public class InsertConnectionsReq implements KvmSerializable{

    private Long profile_id;
    private Long user_id;

    public InsertConnectionsReq(Long profile_id, Long user_id) {
        this.profile_id = profile_id;
        this.user_id = user_id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Long getProfile_id() {
        return profile_id;
    }

    public void setProfile_id(Long profile_id) {
        this.profile_id = profile_id;
    }

    @Override
    public Object getProperty(int i) {
        switch (i) {
            case 0:
                return profile_id;
            case 1:
                return user_id;
            default:
                return null;
        }
    }

    @Override
    public int getPropertyCount() {
        return 2;
    }

    @Override
    public void setProperty(int i, Object o) {
        switch (i) {
            case 0:
                this.profile_id = Long.valueOf(o.toString());
                break;
            case 1:
                this.user_id = Long.valueOf(o.toString());
        }
    }

    @Override
    public void getPropertyInfo(int i, Hashtable hashtable, PropertyInfo propertyInfo) {
        switch (i) {
            case 0:
                propertyInfo.setName("profile_id");
                propertyInfo.setType(Long.class);
                break;
            case 1:
                propertyInfo.setName("user_id");
                propertyInfo.setType(Long.class);
                break;
            default:
                propertyInfo.setName("undefined");
                propertyInfo.setType(String.class);
        }
    }
}