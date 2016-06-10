package es.npatarino.android.gotchallenge.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;


public class GoTCharacterEntity extends RealmObject {

    String name;
    String imageUrl;
    String description;
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

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(final String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

}
