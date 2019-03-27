

package com.nfcmeeting.nfcmeeting.mvp.model;

import android.os.Parcel;
import android.os.Parcelable;


import com.nfcmeeting.nfcmeeting.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created_Time by ThirtyDegreesRay on 2017/8/25 14:31:21
 */

public class SearchModel implements Parcelable {

    public enum SearchType{
        Repository, User
    }

    public static final List<Integer> REPO_SORT_ID_LIST = Arrays.asList(
            R.id.action_bast_match,
            R.id.action_recently_created, R.id.action_previously_created
    );

    public static final List<Integer> USER_SORT_ID_LIST = Arrays.asList(
            R.id.action_bast_match,
            R.id.action_most_recently_joined, R.id.action_least_recently_joined
    );

    public static final List<Integer> SORT_ID_LIST = new ArrayList<>();
    static {
        SORT_ID_LIST.addAll(REPO_SORT_ID_LIST);
        SORT_ID_LIST.addAll(USER_SORT_ID_LIST);
    }


    private SearchType type;
    private String query;
    private String sort = "";
    private boolean desc = true;

    public SearchModel(SearchType type) {
        this.type = type;
    }

    public SearchModel(SearchType type, String query) {
        this.type = type;
        this.query = query;
    }

    public String getQuery() {
        return query;
    }

    public SearchModel setQuery(String query) {
        this.query = query;
        return this;
    }

    public String getSort() {
        return sort;
    }

    public SearchModel setSort(String sort) {
        this.sort = sort;
        return this;
    }

    public SearchModel setSortId(int sortId) {
        if(!SORT_ID_LIST.contains(sortId)) {
            return this;
        }
        setSortWithSortId(sortId);
        setOrderWithSortId(sortId);
        return this;
    }

    public boolean isDesc() {
        return desc;
    }

    public SearchModel setDesc(boolean desc) {
        this.desc = desc;
        return this;
    }

    public String getOrder(){
        return desc ? "desc" : "asc";
    }

    public SearchType getType() {
        return type;
    }

    private void setSortWithSortId(int sortId){
        switch (sortId){
            case R.id.action_bast_match:
                sort = "";
                break;


            case R.id.action_recently_created:
            case R.id.action_previously_created:
                sort = "created";
                break;

            case R.id.action_most_recently_joined:
            case R.id.action_least_recently_joined:
                sort = "joined";
                break;

        }
    }

    private void setOrderWithSortId(int sortId){
        switch (sortId){
            case R.id.action_bast_match:
            case R.id.action_recently_created:
            case R.id.action_most_recently_joined:
                desc = true;
                break;
            case R.id.action_previously_created:
            case R.id.action_least_recently_joined:
                desc = false;
                break;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.type == null ? -1 : this.type.ordinal());
        dest.writeString(this.query);
        dest.writeString(this.sort);
        dest.writeByte(this.desc ? (byte) 1 : (byte) 0);
    }

    public SearchModel() {
    }

    protected SearchModel(Parcel in) {
        int tmpType = in.readInt();
        this.type = tmpType == -1 ? null : SearchType.values()[tmpType];
        this.query = in.readString();
        this.sort = in.readString();
        this.desc = in.readByte() != 0;
    }

    public static final Creator<SearchModel> CREATOR = new Creator<SearchModel>() {
        @Override
        public SearchModel createFromParcel(Parcel source) {
            return new SearchModel(source);
        }

        @Override
        public SearchModel[] newArray(int size) {
            return new SearchModel[size];
        }
    };
}
