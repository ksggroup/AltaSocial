package com.ksggroup.altanet.altasocial.Model;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;

/**
 * Created by vfiguracion on 3/7/17.
 */

public class AuthenticateRequest implements KvmSerializable {

    private String username;
    private String password;

    public AuthenticateRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public Object getProperty(int i) {

        switch(i) {
            case 0:
                return this.username;
            case 1:
                return this.password;
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
                this.username = o.toString();
                break;
            case 1:
                this.password = o.toString();
                break;
        }
    }

    @Override
    public void getPropertyInfo(int i, Hashtable hashtable, PropertyInfo propertyInfo) {

        switch(i) {
            case 0:
                propertyInfo.setName("username");
                propertyInfo.setType(String.class);
                break;
            case 1:
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
