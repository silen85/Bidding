package com.lessomall.bidding.model;

@SuppressWarnings("serial")
public class QuotePrice {

    private String id;
    private String createTime;
    private String lastUpdated;

    /**
     * 供应商编码.
     */
    private String supplierCode;

    /**
     * 供应商名称.
     */
    private String supplierName;

    /**
     * 报价日期.
     */
    private String quotationDate;

    /**
     * 竞价明细ID.
     */
    private String biddingDetailId;

    /**
     * 单价.
     */
    private String price;

    /**
     * 实际供货总数.
     */
    private String actualSupplyTotalNumber;

    /**
     * 备注.
     */
    private String memo;

    /**
     * 报价状态.
     */
    private String biddingStatus;

    /**
     * 退回说明.
     */
    private String returnState;

    /**
     * 计数.
     */
    private String countSupplierBidding;


    public String getCountSupplierBidding() {
        return countSupplierBidding;
    }

    public void setCountSupplierBidding(String countSupplierBidding) {
        this.countSupplierBidding = countSupplierBidding;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getQuotationDate() {
        return quotationDate;
    }

    public void setQuotationDate(String quotationDate) {
        this.quotationDate = quotationDate;
    }

    public String getBiddingDetailId() {
        return biddingDetailId;
    }

    public void setBiddingDetailId(String biddingDetailId) {
        this.biddingDetailId = biddingDetailId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getActualSupplyTotalNumber() {
        return actualSupplyTotalNumber;
    }

    public void setActualSupplyTotalNumber(String actualSupplyTotalNumber) {
        this.actualSupplyTotalNumber = actualSupplyTotalNumber;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getBiddingStatus() {
        return biddingStatus;
    }

    public void setBiddingStatus(String biddingStatus) {
        this.biddingStatus = biddingStatus;
    }

    public String getReturnState() {
        return returnState;
    }

    public void setReturnState(String returnState) {
        this.returnState = returnState;
    }
}
