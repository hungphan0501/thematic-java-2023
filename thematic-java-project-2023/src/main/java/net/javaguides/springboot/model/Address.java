package net.javaguides.springboot.model;

import javax.persistence.*;

@Entity
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "id_customer")
    private int idUser;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "phone")
    private String phone;

    @Column(name = "city")
    private String city;

    @Column(name = "district")
    private String district;

    @Column(name = "ward")
    private String ward;

    @Column(name = "specific_address")
    private String specificAddress;

    @Column(name = "is_default")
    private int isDefault;

    public Address() {
    }

    public Address(int idUser, String userName, String phone, String city, String district, String ward, String specificAddress, int isDefault) {
        this.idUser = idUser;
        this.userName = userName;
        this.phone = phone;
        this.city = city;
        this.district = district;
        this.ward = ward;
        this.specificAddress = specificAddress;
        this.isDefault = isDefault;
    }

    public String getSpecificAddress() {
        return specificAddress + ", " + getWard() + ", " + getDistrict() + ", " + getCity();
    }

    public void setSpecificAddress(String specificAddress) {
        this.specificAddress = specificAddress;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public int getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(int isDefault) {
        this.isDefault = isDefault;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", idUser=" + idUser +
                ", userName='" + userName + '\'' +
                ", phone='" + phone + '\'' +
                ", city='" + city + '\'' +
                ", district='" + district + '\'' +
                ", ward='" + ward + '\'' +
                ", specificAddress='" + specificAddress + '\'' +
                ", isDefault=" + isDefault +
                '}';
    }
}
