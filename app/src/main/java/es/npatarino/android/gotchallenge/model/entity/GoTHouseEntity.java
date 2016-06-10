package es.npatarino.android.gotchallenge.model.entity;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class GoTHouseEntity  extends RealmObject {


    String houseImageUrl;
    String houseName;
    String houseId;

    public String getHouseImageUrl() {
        return houseImageUrl;
    }

    public void setHouseImageUrl(final String houseImageUrl) {
        this.houseImageUrl = houseImageUrl;
    }

    public String getHouseName() {
        return houseName;
    }

    public void setHouseName(final String houseName) {
        this.houseName = houseName;
    }

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(final String houseId) {
        this.houseId = houseId;
    }

}
