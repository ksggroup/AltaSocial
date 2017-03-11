package com.ksggroup.altanet.altasocial.Model;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;

/**
 * Created by vfiguracion on 3/11/17.
 */

public class InsertUserRequest implements KvmSerializable {
    private String first_name;
    private String middle_name;

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

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String last_name;
    private String dob;
    private String username;
    public InsertUserRequest() {

    }
    public InsertUserRequest(String first_name, String middle_name, String last_name, String dob, String username, String password) {
        this.first_name = first_name;
        this.middle_name = middle_name;
        this.last_name = last_name;
        this.dob = dob;
        this.username = username;
        this.password = password;
    }


    private String password;




    @Override
    public Object getProperty(int i) {
        switch (i) {
            case 0:
                return first_name;
            case 1:
                return middle_name;
            case 2:
                return last_name;
            case 3:
                return dob;
            case 4:
                return username;
            case 5:
                 return password;
            default:
                return null;
        }
    }

    @Override
    public int getPropertyCount() {
        return 6;
    }

    @Override
    public void setProperty(int i, Object o) {
        switch (i) {
            case 0:
                first_name =o.toString();
                break;
            case 1:
                middle_name = o.toString();
                break;
            case 2:
                last_name = o.toString();
            case 3:
                dob = o.toString();
            case 4:
                username= o.toString();
            case 5:
                password = o.toString();
        }

    }

    @Override
    public void getPropertyInfo(int i, Hashtable hashtable, PropertyInfo propertyInfo) {
        switch (i) {
            case 0:
                propertyInfo.setName("first_name");
                propertyInfo.setType(String.class);
                break;
            case 1:
                propertyInfo.setName("middle_name");
                propertyInfo.setType(String.class);
                break;
            case 2:
                propertyInfo.setName("last_name");
                propertyInfo.setType(String.class);
                break;
            case 3:
                propertyInfo.setName("dob");
                propertyInfo.setType(String.class);
                break;
            case 4:
                propertyInfo.setName("username");
                propertyInfo.setType(String.class);
                break;
            case 5:
                propertyInfo.setName("password");
                propertyInfo.setType(String.class);
                break;
            default:
                propertyInfo.setName("undefined");
                propertyInfo.setType(String.class);
                break;

        }

    }
}
