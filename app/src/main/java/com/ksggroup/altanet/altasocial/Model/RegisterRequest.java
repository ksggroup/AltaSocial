package com.ksggroup.altanet.altasocial.Model;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;



public class RegisterRequest implements KvmSerializable {
    private String username;
    private String password;
    private String fName;

    private String mName;
    private  String lName;
    private String dob;
    private  String course;
    private  String yearLevel;

    public RegisterRequest(String username, String password, String fName, String mName, String lName, String dob) {
        this.username = username;
        this.password = password;
        this.fName = fName;
        this.mName = mName;
        this.lName = lName;
        this.dob = dob;
    }

    @Override
    public Object getProperty(int i) {

        switch(i) {
            case 0:
                return this.username;
            case 1:
                return this.password;
            case  2:
                return this.fName;
            case 3:
                return this.mName;
            case 4:
                return this.lName;
            case 5:
                return this.dob;
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
        switch(i) {
            case 0:
                this.fName = o.toString();
                break;
            case 1:
                this.mName = o.toString();
                break;
            case 2:
                this.lName = o.toString();
                break;
            case 3:
                this.course = o.toString();
                break;
            case 4:
                this.yearLevel= o.toString();
                break;
            case 5:
                this.username = o.toString();
                break;
            case 6:
                this.password = o.toString();
                break;

        }
    }

    @Override
    public void getPropertyInfo(int i, Hashtable hashtable, PropertyInfo propertyInfo) {

        switch(i) {
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
                propertyInfo.setName("course");
                propertyInfo.setType(String.class);
                propertyInfo.setName("year_level");
                propertyInfo.setType(String.class);
                break;

            case 5:
            propertyInfo.setName("year_level");
            propertyInfo.setType(String.class);
                break;
            case 6:
                propertyInfo.setName("username");
                propertyInfo.setType(String.class);
                break;
            case 7:
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
