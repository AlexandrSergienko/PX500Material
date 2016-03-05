package com.test.android.a500px.picx500.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Alex on 05.03.2016.
 */
public class Photo implements Parcelable {
    public static final Creator<Photo> CREATOR = new Creator<Photo>() {
        public Photo createFromParcel(Parcel source) {
            return new Photo(source);
        }

        public Photo[] newArray(int size) {
            return new Photo[size];
        }
    };
    private String url;
    @SerializedName("image_url")
    private String imageUrl;
    private String camera;
    private String name;
    @SerializedName("user")
    private User author;

    public Photo() {
    }

    protected Photo(Parcel in) {
        this.url = in.readString();
        this.imageUrl = in.readString();
        this.camera = in.readString();
        this.name = in.readString();
        this.author = in.readParcelable(User.class.getClassLoader());
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCamera() {
        return camera;
    }

    public void setCamera(String camera) {
        this.camera = camera;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.url);
        dest.writeString(this.imageUrl);
        dest.writeString(this.camera);
        dest.writeString(this.name);
        dest.writeParcelable(this.author, 0);
    }
}
