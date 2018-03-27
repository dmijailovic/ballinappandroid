package ballinapp.com.ballinapp.data.util;

import com.google.gson.annotations.SerializedName;

/**
 * Created by dusan on 3/25/18.
 */

public class JWTInfo {

    @SerializedName("accessToken")
    private String accessToken;

    @SerializedName("refresh")
    private String refresh;

    @SerializedName("teamId")
    private int teamId;

    public JWTInfo(String accessToken, String refresh, int teamId) {
        this.accessToken = accessToken;
        this.refresh = refresh;
        this.teamId = teamId;
    }

    public JWTInfo() {}

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefresh() {
        return refresh;
    }

    public void setRefresh(String refresh) {
        this.refresh = refresh;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }
}
