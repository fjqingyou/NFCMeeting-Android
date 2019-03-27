package com.nfcmeeting.nfcmeeting.model;

/**
 * Created_Time by hguang_gj@neusoft.com on 2019/3/20 17:52
 */
public class PageInfo {
    private int pageNum = 1;
    private int pageSize = 8;
    private String orderBy;

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

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
