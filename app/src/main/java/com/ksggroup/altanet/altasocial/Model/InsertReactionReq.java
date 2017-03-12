package com.ksggroup.altanet.altasocial.Model;


import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;

public class InsertReactionReq implements KvmSerializable {

    private Long post_id;
    private Long user_id;
    private Long type;

    public InsertReactionReq(Long post_id, Long user_id, Long type) {
        this.post_id = post_id;
        this.user_id = user_id;
        this.type = type;
    }

    public Long getPost_id() {
        return post_id;
    }
    public void setPost_id(Long post_id) {
        this.post_id = post_id;
    }
    public Long getUser_id() {
        return user_id;
    }
    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }
    public Long getType() {
        return type;
    }
    public void setType(Long type) {
        this.type = type;
    }

    @Override
    public Object getProperty(int i) {

        switch (i) {
            case 0:
                return post_id;
            case 1:
                return user_id;
            case 2:
                return type;
            default:
                return null;
        }

    }

    @Override
    public int getPropertyCount() {
        return 3;
    }

    @Override
    public void setProperty(int i, Object o) {
        switch (i) {
            case 0:
                post_id = Long.valueOf(o.toString());
                break;
            case 1:
                user_id = Long.valueOf(o.toString());
                break;
            case 2:
                type = Long.valueOf(o.toString());
                break;
        }
    }

    @Override
    public void getPropertyInfo(int i, Hashtable hashtable, PropertyInfo propertyInfo) {
        switch (i) {
            case 0:
                propertyInfo.setName("post_id");
                propertyInfo.setType(Long.class);
                break;
            case 1:
                propertyInfo.setName("user_id");
                propertyInfo.setType(Long.class);
                break;
            case 2:
                propertyInfo.setName("type");
                propertyInfo.setType(Long.class);
                break;
        }
    }
}