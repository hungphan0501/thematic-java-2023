package net.javaguides.springboot.web.dto;

public class ChangeUserInforDTO {
    private String name;
    private String email;
    private String phone;
    private String gender;
    private int day;
    private int month;
    private int year;

    public ChangeUserInforDTO(){

    }

    public ChangeUserInforDTO(String name, String email, String phone, String gender, int day, int month, int year) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.gender = gender;
        this.day = day;
        this.month = month;
        this.year = year;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "ChangeUserInforDTO{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", gender='" + gender + '\'' +
                ", day=" + day +
                ", month=" + month +
                ", year=" + year +
                '}';
    }
}
