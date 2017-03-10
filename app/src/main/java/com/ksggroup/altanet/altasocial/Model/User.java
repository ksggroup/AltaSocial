package com.ksggroup.altanet.altasocial.Model;


import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {

    Long user_id;
    String first_name;
    String middle_name;
    String last_name;
    String dob;
    String username;
    String password;

    public User () {}

    private User(Parcel in) {
        user_id = in.readLong();
        first_name = in.readString();
        middle_name = in.readString();
        last_name = in.readString();
        dob = in.readString();
        username = in.readString();
        password = in.readString();
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
    public Long getUser_id() {
        return user_id;
    }
    public void setUser_id(Long user_id) {
        this.user_id = user_id;
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
    public String getDob() {
        return dob;
    }
    public void setDob(String dob) {
        this.dob = dob;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(user_id);
        dest.writeString(first_name);
        dest.writeString(middle_name);
        dest.writeString(last_name);
        dest.writeString(dob);
        dest.writeString(username);
        dest.writeString(password);
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {

        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
