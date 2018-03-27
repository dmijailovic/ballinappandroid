package ballinapp.com.ballinapp.data.util;

import com.google.gson.annotations.SerializedName;

/**
 * Created by dusan on 3/26/18.
 */

public class AppearanceUpdateBean {

    @SerializedName("id")
    private int id;

    @SerializedName("updateEnum")
    private AppearanceUpdateEnum updateEnum;

    public AppearanceUpdateBean(int teamId, AppearanceUpdateEnum updateEnum) {
        this.id = teamId;
        this.updateEnum = updateEnum;
    }

    public AppearanceUpdateBean() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public AppearanceUpdateEnum getUpdateEnum() {
        return updateEnum;
    }

    public void setUpdateEnum(AppearanceUpdateEnum updateEnum) {
        this.updateEnum = updateEnum;
    }
}
