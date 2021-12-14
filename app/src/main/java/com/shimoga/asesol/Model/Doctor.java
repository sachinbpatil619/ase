package com.shimoga.asesol.Model;

public class Doctor {
    private String name,city,deptId,description,hospitalId,hospitalName,image,dept;

    public Doctor(String name, String city, String deptId, String description, String hospitalId, String hospitalName, String image, String dept) {
        this.name = name;
        this.city = city;
        this.deptId = deptId;
        this.description = description;
        this.hospitalId = hospitalId;
        this.hospitalName = hospitalName;
        this.image = image;
        this.dept = dept;
    }

    public Doctor() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }
}
