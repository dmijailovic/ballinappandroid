package ballinapp.com.ballinapp.data;

import com.google.gson.annotations.SerializedName;

public class Player {

    @SerializedName("id")
    private int id;

    @SerializedName("nickname")
    private String nickname;

    @SerializedName("birthyear")
    private int birthyear;

    @SerializedName("contact")
    private String contact;

    public Player() {}

    public Player(String nickname, int birthyear, String contact) {
        this.nickname = nickname;
        this.birthyear = birthyear;
        this.contact = contact;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getBirthyear() {
        return birthyear;
    }

    public void setBirthyear(int birthyear) {
        this.birthyear = birthyear;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}

