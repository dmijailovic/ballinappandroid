package ballinapp.com.ballinapp.data;

import com.google.gson.annotations.SerializedName;

public class Team {

    @SerializedName("id")
    private int team_id;

    @SerializedName("name")
    private String name;

    @SerializedName("state")
    private String state;

    @SerializedName("appearancePlus")
    private int appearancePlus;

    @SerializedName("appearanceMinus")
    private int appearanceMinus;

    @SerializedName("open")
    private boolean open;

    @SerializedName("active")
    private boolean active;

    @SerializedName("password")
    private String password;

    @SerializedName("email")
    private String email;

    @SerializedName("city")
    private String city;

    public Team(int team_id, String name, String state, int appearance_plus, int appearance_minus, boolean open,
                String password, String email, String city) {
        this.team_id = team_id;
        this.name = name;
        this.state = state;
        this.appearancePlus = appearance_plus;
        this.appearanceMinus = appearance_minus;
        this.open = open;
        this.password = password;
        this.email = email;
        this.city = city;
    }

    public Team() {}

    public int getTeam_id() {
        return team_id;
    }

    public void setTeam_id(int team_id) {
        this.team_id = team_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getAppearancePlus() {
        return appearancePlus;
    }

    public void setAppearancePlus(int appearancePlus) {
        this.appearancePlus = appearancePlus;
    }

    public int getAppearanceMinus() {
        return appearanceMinus;
    }

    public void setAppearanceMinus(int appearanceMinus) {
        this.appearanceMinus = appearanceMinus;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
