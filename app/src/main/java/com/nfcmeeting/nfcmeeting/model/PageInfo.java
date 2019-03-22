package com.nfcmeeting.nfcmeeting.model;

/**
 * Created by hguang_gj@neusoft.com on 2019/3/20 17:52
 */
public class PageInfo {
    private int pageNum = 1;
    private int pageSize = 8;

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
