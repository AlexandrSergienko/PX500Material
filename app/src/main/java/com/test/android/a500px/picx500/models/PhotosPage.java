package com.test.android.a500px.picx500.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Alex on 05.03.2016.
 */
public class PhotosPage implements Parcelable {

    public static final Parcelable.Creator<PhotosPage> CREATOR = new Parcelable.Creator<PhotosPage>() {
        public PhotosPage createFromParcel(Parcel source) {
            return new PhotosPage(source);
        }

        public PhotosPage[] newArray(int size) {
            return new PhotosPage[size];
        }
    };
    private List<Photo> photos;
    @SerializedName("current_page")
    private int currentPage;
    @SerializedName("total_pages")
    private int totalPages;

    public PhotosPage() {
    }

    protected PhotosPage(Parcel in) {
        this.photos = in.createTypedArrayList(Photo.CREATOR);
        this.currentPage = in.readInt();
        this.totalPages = in.readInt();
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(photos);
        dest.writeInt(this.currentPage);
        dest.writeInt(this.totalPages);
    }
}
