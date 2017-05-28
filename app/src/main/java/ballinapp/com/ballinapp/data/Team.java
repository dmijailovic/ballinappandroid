package ballinapp.com.ballinapp.data;

import com.google.gson.annotations.SerializedName;

public class Team {
    @SerializedName("id")
    private Long team_id;

    @SerializedName("name")
    private String name;

    @SerializedName("state")
    private String state;

    @SerializedName("appearance_plus")
    private int appearance_plus;

    @SerializedName("appearance_minus")
    private int appearance_minus;

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

    public Team(Long team_id, String name, String state, int appearance_plus, int appearance_minus, boolean open,
                String password, String email, String city) {
        this.team_id = team_id;
        this.name = name;
        this.state = state;
        this.appearance_plus = appearance_plus;
        this.appearance_minus = appearance_minus;
        this.open = open;
        this.password = password;
        this.email = email;
        this.city = city;
    }

    public Team() {}

    public Long getTeam_id() {
        return team_id;
    }

    public void setTeam_id(Long team_id) {
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

    public int getAppearance_plus() {
        return appearance_plus;
    }

    public void setAppearance_plus(int appearance_plus) {
        this.appearance_plus = appearance_plus;
    }

    public int getAppearance_minus() {
        return appearance_minus;
    }

    public void setAppearance_minus(int appearance_minus) {
        this.appearance_minus = appearance_minus;
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
