package com.ksggroup.altanet.altasocial.Model;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;


public class InsertCommentsReq implements KvmSerializable {
    private Long user_id;
    private Long post_id;
    private String content;
    private String first_name;
    private String middle_name;
    private String last_name;

    public InsertCommentsReq(Long user_id, Long post_id, String content, String first_name, String middle_name, String last_name) {
        this.user_id = user_id;
        this.post_id = post_id;
        this.content = content;
        this.first_name = first_name;
        this.middle_name = middle_name;
        this.last_name = last_name;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Long getPost_id() {
        return post_id;
    }

    public void setPost_id(Long post_id) {
        this.post_id = post_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getMiddle_name() {
        return middle_name;
    }

    public void setMiddle_name(String middle_name) {
        this.middle_name = middle_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    @Override
    public Object getProperty(int i) {

        switch (i) {
            case 0:
                return user_id;
            case 1:
                return post_id;
            case 2:
                return content;
            case 3:
                return first_name;
            case 4:
                return middle_name;
            case 5:
                return last_name;
        }

        return null;
    }

    @Override
    public int getPropertyCount() {
        return 6;
    }

    @Override
    public void setProperty(int i, Object o) {
        switch (i) {
            case 0:
                this.user_id = Long.valueOf(o.toString());
                break;
            case 1:
                this.post_id = Long.valueOf(o.toString());
                break;
            case 2:
                this.content = o.toString();
                break;
            case 3:
                this.first_name = o.toString();
                break;
            case 4:
                this.middle_name = o.toString();
                break;
            case 5:
                this.last_name = o.toString();
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
                propertyInfo.setName("post_id");
                propertyInfo.setType(Long.class);
                break;
            case 2:
                propertyInfo.setName("content");
                propertyInfo.setType(String.class);
                break;
            case 3:
                propertyInfo.setName("first_name");
                propertyInfo.setType(String.class);
                break;
            case 4:
                propertyInfo.setName("middle_name");
                propertyInfo.setType(String.class);
                break;
            case 5:
                propertyInfo.setName("last_name");
                propertyInfo.setType(String.class);
                break;
        }
    }
}