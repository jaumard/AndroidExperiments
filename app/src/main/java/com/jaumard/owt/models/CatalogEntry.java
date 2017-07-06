package com.jaumard.owt.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "catalog_entries")
public class CatalogEntry implements Parcelable {
    public static final Parcelable.Creator<CatalogEntry> CREATOR = new Parcelable.Creator<CatalogEntry>() {
        @Override
        public CatalogEntry createFromParcel(Parcel source) {
            return new CatalogEntry(source);
        }

        @Override
        public CatalogEntry[] newArray(int size) {
            return new CatalogEntry[size];
        }
    };

    @SerializedName("id")
    @PrimaryKey
    private long id;

    @SerializedName("vote_count")
    private long nbVotes;

    @SerializedName("vote_average")
    private float note;

    private String title;

    @SerializedName("poster_path")
    private String poster;

    @SerializedName("overview")
    private String description;

    @SerializedName("original_language")
    private String language;

    @SerializedName("release_date")
    private String releaseDate;

    protected CatalogEntry(Parcel in) {
        this.id = in.readLong();
        this.nbVotes = in.readLong();
        this.note = in.readFloat();
        this.title = in.readString();
        this.poster = in.readString();
        this.description = in.readString();
        this.language = in.readString();
        this.releaseDate = in.readString();
    }

    public CatalogEntry(long id, long nbVotes, float note, String title, String poster, String description, String language, String releaseDate) {
        this.id = id;
        this.nbVotes = nbVotes;
        this.note = note;
        this.title = title;
        this.poster = poster;
        this.description = description;
        this.language = language;
        this.releaseDate = releaseDate;
    }

    public long getId() {
        return id;
    }

    public long getNbVotes() {
        return nbVotes;
    }

    public float getNote() {
        return note;
    }

    public String getTitle() {
        return title;
    }

    public String getPoster() {
        return poster;
    }

    public String getDescription() {
        return description;
    }

    public String getLanguage() {
        return language;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeLong(this.nbVotes);
        dest.writeFloat(this.note);
        dest.writeString(this.title);
        dest.writeString(this.poster);
        dest.writeString(this.description);
        dest.writeString(this.language);
        dest.writeString(this.releaseDate);
    }

}
