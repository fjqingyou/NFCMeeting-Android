

package com.nfcmeeting.nfcmeeting.mvp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;


import java.util.Date;



public class Repository implements Parcelable {

    private Integer meetingId;


    private String title;


    private String content;


    private Date beginTime;


    private Date endTime;


    private Integer moderator;

    protected Repository(Parcel in) {
        if (in.readByte() == 0) {
            meetingId = null;
        } else {
            meetingId = in.readInt();
        }
        title = in.readString();
        content = in.readString();
        if (in.readByte() == 0) {
            moderator = null;
        } else {
            moderator = in.readInt();
        }
    }

    public static final Creator<Repository> CREATOR = new Creator<Repository>() {
        @Override
        public Repository createFromParcel(Parcel in) {
            return new Repository(in);
        }

        @Override
        public Repository[] newArray(int size) {
            return new Repository[size];
        }
    };

    public Integer getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(Integer meetingId) {
        this.meetingId = meetingId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getModerator() {
        return moderator;
    }

    public void setModerator(Integer moderator) {
        this.moderator = moderator;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (meetingId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(meetingId);
        }
        dest.writeString(title);
        dest.writeString(content);
        if (moderator == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(moderator);
        }
    }
}
