package ballinapp.com.ballinapp.data.util;

import com.google.gson.annotations.SerializedName;

/**
 * Created by dusan on 3/25/18.
 */

public class LoginInfo {

    @SerializedName("team")
    private String teamName;

    @SerializedName("pass")
    private String password;

    public LoginInfo(String teamName, String password) {
        this.teamName = teamName;
        this.password = password;
    }

    public LoginInfo() {}

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
