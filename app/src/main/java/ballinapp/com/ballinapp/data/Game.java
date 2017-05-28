package ballinapp.com.ballinapp.data;

import com.google.gson.annotations.SerializedName;

public class Game {

    @SerializedName("id")
    private int id;

    @SerializedName("message")
    private String message;

    @SerializedName("contact")
    private String contact;

    @SerializedName("state")
    private String state;

    @SerializedName("city")
    private String city;

    @SerializedName("address")
    private String address;

    @SerializedName("date")
    private String date;

    @SerializedName("time")
    private String time;

    public Game(String message, String contact, String state, String city, String address, String date, String time) {
        this.message = message;
        this.contact = contact;
        this.state = state;
        this.city = city;
        this.address = address;
        this.date = date;
        this.time = time;
    }

    public Game(int id, String message, String contact, String state, String city, String address, String date, String time) {
        this.id = id;
        this.message = message;
        this.contact = contact;
        this.state = state;
        this.city = city;
        this.address = address;
        this.date = date;
        this.time = time;
    }

    public Game() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

