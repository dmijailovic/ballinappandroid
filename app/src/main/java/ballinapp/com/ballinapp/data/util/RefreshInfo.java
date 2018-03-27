package ballinapp.com.ballinapp.data.util;

import com.google.gson.annotations.SerializedName;

/**
 * Created by dusan on 3/27/18.
 */

public class RefreshInfo {

    @SerializedName("teamId")
    private int teamId;

    @SerializedName("refresh")
    private String refresh;

    public RefreshInfo(int teamId, String refresh) {
        this.teamId = teamId;
        this.refresh = refresh;
    }

    public RefreshInfo() {}

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public String getRefresh() {
        return refresh;
    }

    public void setRefresh(String refresh) {
        this.refresh = refresh;
    }
}
