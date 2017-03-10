package com.ksggroup.altanet.altasocial.Model;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import java.util.Hashtable;

public class InsertPostRequest implements KvmSerializable {
    private Long user_id;

    public InsertPostRequest(Long user_id, String content) {
        this.user_id = user_id;
        this.content = content;
    }

    private String content;

    public Long getUser_id() {
        return user_id;
    }
    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public Object getProperty(int i) {
        switch (i) {
            case 0:
                return user_id;
            case 1:
                return content;
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
                user_id = Long.valueOf(o.toString());
                break;
            case 1:
                content = o.toString();
                break;
        }
    }

    @Override
    public void getPropertyInfo(int i, Hashtable hashtable, PropertyInfo propertyInfo) {
        switch(i) {
            case 0:
                propertyInfo.setName("user_id");
                propertyInfo.setType(Long.class);
                break;
            case 1:
                propertyInfo.setName("content");
                propertyInfo.setType(String.class);
                break;
            default:
                propertyInfo.setName("undefined");
                propertyInfo.setType(String.class);
                break;
        }
    }
}