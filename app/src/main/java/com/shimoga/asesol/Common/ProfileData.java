package com.shimoga.asesol.Common;

public class ProfileData {

    private String name;
    private String email;
    private String phone;
    private String city;
    private String address;
    private String pincode;
    private String isStaff;

    public ProfileData() {
    }

    public ProfileData(String name, String email, String phone, String city, String address, String pincode, String isStaff) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.city = city;
        this.address = address;
        this.pincode = pincode;
        this.isStaff = isStaff;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getIsStaff() {
        return isStaff;
    }

    public void setIsStaff(String isStaff) {
        this.isStaff = isStaff;
    }
}
