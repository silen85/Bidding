package com.lessomall.bidding.model;

import android.graphics.Bitmap;

import com.lessomall.bidding.common.GetWebImageTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SuppressWarnings("serial")
public class Bidding implements GetWebImageTask.WebImageCacheI {

    //0:竞价单；1：报价单
    private String orderType = "0";

    private String id;
    //单号
    private String biddingCode;
    //主题
    private String biddingTitle;
    //支付方式
    private String paymentMode;
    //税票种类
    private String taxBillType;
    //截止日期
    private String biddingDeadline;
    //期望交货日期
    private String expectDeliveryDate;
    //配送地址
    private String deliveryGoodsPlace;
    //提货方式
    private String deliveryGoodsMode;
    //保证金凭证
    private String depositPaymentVouchers;
    //备注说明
    private String memo;

    //备注说明2
    private String memo2;

    //dto状态
    private String biddingStatus;

    //名称型号
    private String nameType;

    //客户编号
    private String consumerCode;
    //客户名称
    private String consumerName;
    //意向单价
    private String intentPrice;
    //品牌
    private String brand;
    //金额
    private String price;
    //图片地址
    private String pictureURL;
    //计量单位
    private String unit;
    //需求数量
    private String requiredQuantity;
    //佣金比例
    private String commissionRate;

    //发布时间
    private String createtime;

    private String biddingStatusName;

    public Bitmap getImageCache() {
        return getViewHolder().getImageCache();
    }

    public void setImageCache(Bitmap imageCache) {
        getViewHolder().setImageCache(imageCache);
    }

    private ViewHolder viewHolder;

    private ViewHolder getViewHolder() {
        if (viewHolder == null)
            viewHolder = new ViewHolder();
        return viewHolder;
    }

    @Override
    public void putCache(Bitmap bitmap) {
        setImageCache(bitmap);
    }

    private class ViewHolder {

        private Bitmap imageCache;

        public Bitmap getImageCache() {
            return imageCache;
        }

        public void setImageCache(Bitmap imageCache) {
            this.imageCache = imageCache;
        }
    }

    private String trimValue(String value) {
        return (value == null ? "" : value.trim());
    }

    public Bidding map2Bidding(Map map) {

        Bidding bidding = this;

        bidding.setOrderType(trimValue((String) map.get("OrderType")));
        bidding.setId(trimValue((String) map.get("Id")));
        bidding.setBrand(trimValue((String) map.get("Brand")));
        bidding.setBiddingCode(trimValue((String) map.get("BiddingCode")));
        bidding.setBiddingDeadline(trimValue((String) map.get("BiddingDeadline")));
        bidding.setBiddingStatus(trimValue((String) map.get("BiddingStatus")));
        bidding.setBiddingTitle(trimValue((String) map.get("BiddingTitle")));
        bidding.setConsumerCode(trimValue((String) map.get("ConsumerCode")));
        bidding.setCommissionRate(trimValue((String) map.get("CommissionRate")));
        bidding.setConsumerName(trimValue((String) map.get("ConsumerName")));
        bidding.setDeliveryGoodsMode(trimValue((String) map.get("DeliveryGoodsMode")));
        bidding.setDeliveryGoodsPlace(trimValue((String) map.get("DeliveryGoodsPlace")));
        bidding.setDepositPaymentVouchers(trimValue((String) map.get("DepositPaymentVouchers")));
        bidding.setExpectDeliveryDate(trimValue((String) map.get("ExpectDeliveryDate")));
        bidding.setIntentPrice(trimValue((String) map.get("IntentPrice")));
        bidding.setMemo(trimValue((String) map.get("Memo")));
        bidding.setMemo2(trimValue((String) map.get("Memo2")));
        bidding.setNameType(trimValue((String) map.get("NameType")));
        bidding.setPaymentMode(trimValue((String) map.get("PaymentMode")));
        bidding.setPictureURL(trimValue((String) map.get("PictureURL")));
        bidding.setPrice(trimValue((String) map.get("Price")));
        bidding.setRequiredQuantity(trimValue((String) map.get("RequiredQuantity")));
        bidding.setTaxBillType(trimValue((String) map.get("TaxBillType")));
        bidding.setUnit(trimValue((String) map.get("Unit")));
        bidding.setBiddingStatusName(trimValue((String) map.get("BiddingStatusName")));
        bidding.setCreatetime(trimValue((String) map.get("Createtime")));

        List<Map<String, String>> _data = (List<Map<String, String>>) (((Map) map).get("datalist"));

        List<QuotePrice> quotePrices = new ArrayList<QuotePrice>();
        if (_data != null && _data.size() > 0) {
            for (int j = 0; j < _data.size(); j++) {

                QuotePrice quotePrice = new QuotePrice();

                quotePrice.setId((String) map.get("Id"));
                quotePrice.setActualSupplyTotalNumber((String) map.get("ActualSupplyTotalNumber"));
                quotePrice.setBiddingStatus((String) map.get("BiddingStatus"));
                quotePrice.setBiddingDetailId((String) map.get("BiddingDetailCountSupplierBidding"));
                quotePrice.setCountSupplierBidding((String) map.get("CountSupplierBidding"));
                quotePrice.setCreateTime((String) map.get("CreateTime"));
                quotePrice.setLastUpdated((String) map.get("LastUpdated"));
                quotePrice.setMemo((String) map.get("Memo"));
                quotePrice.setPrice((String) map.get("Price"));
                quotePrice.setQuotationDate((String) map.get("QuotationDate"));
                quotePrice.setReturnState((String) map.get("ReturnState"));
                quotePrice.setSupplierCode((String) map.get("SupplierCode"));
                quotePrice.setSupplierName((String) map.get("SupplierName"));

                quotePrices.add(quotePrice);
            }
        }

        bidding.setQuotePriceList(quotePrices);

        return bidding;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBiddingCode() {
        return biddingCode;
    }

    public void setBiddingCode(String biddingCode) {
        this.biddingCode = biddingCode;
    }

    public String getBiddingTitle() {
        return biddingTitle;
    }

    public void setBiddingTitle(String biddingTitle) {
        this.biddingTitle = biddingTitle;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getTaxBillType() {
        return taxBillType;
    }

    public void setTaxBillType(String taxBillType) {
        this.taxBillType = taxBillType;
    }

    public String getBiddingDeadline() {
        return biddingDeadline;
    }

    public void setBiddingDeadline(String biddingDeadline) {
        this.biddingDeadline = biddingDeadline;
    }

    public String getExpectDeliveryDate() {
        return expectDeliveryDate;
    }

    public void setExpectDeliveryDate(String expectDeliveryDate) {
        this.expectDeliveryDate = expectDeliveryDate;
    }

    public String getDeliveryGoodsPlace() {
        return deliveryGoodsPlace;
    }

    public void setDeliveryGoodsPlace(String deliveryGoodsPlace) {
        this.deliveryGoodsPlace = deliveryGoodsPlace;
    }

    public String getDeliveryGoodsMode() {
        return deliveryGoodsMode;
    }

    public void setDeliveryGoodsMode(String deliveryGoodsMode) {
        this.deliveryGoodsMode = deliveryGoodsMode;
    }

    public String getDepositPaymentVouchers() {
        return depositPaymentVouchers;
    }

    public void setDepositPaymentVouchers(String depositPaymentVouchers) {
        this.depositPaymentVouchers = depositPaymentVouchers;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getMemo2() {
        return memo2;
    }

    public void setMemo2(String memo2) {
        this.memo2 = memo2;
    }

    public String getBiddingStatus() {
        return biddingStatus;
    }

    public void setBiddingStatus(String biddingStatus) {
        this.biddingStatus = biddingStatus;
    }

    public String getNameType() {
        return nameType;
    }

    public void setNameType(String nameType) {
        this.nameType = nameType;
    }

    public String getConsumerCode() {
        return consumerCode;
    }

    public void setConsumerCode(String consumerCode) {
        this.consumerCode = consumerCode;
    }

    public String getConsumerName() {
        return consumerName;
    }

    public void setConsumerName(String consumerName) {
        this.consumerName = consumerName;
    }

    public String getIntentPrice() {
        return intentPrice;
    }

    public void setIntentPrice(String intentPrice) {
        this.intentPrice = intentPrice;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPictureURL() {
        return pictureURL;
    }

    public void setPictureURL(String pictureURL) {
        this.pictureURL = pictureURL;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getRequiredQuantity() {
        return requiredQuantity;
    }

    public void setRequiredQuantity(String requiredQuantity) {
        this.requiredQuantity = requiredQuantity;
    }

    public String getCommissionRate() {
        return commissionRate;
    }

    public void setCommissionRate(String commissionRate) {
        this.commissionRate = commissionRate;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getBiddingStatusName() {
        return biddingStatusName;
    }

    public void setBiddingStatusName(String biddingStatusName) {
        this.biddingStatusName = biddingStatusName;
    }

    //明细
    private List<QuotePrice> quotePriceList;

    public List<QuotePrice> getQuotePriceList() {
        return quotePriceList;
    }

    public void setQuotePriceList(List<QuotePrice> quotePriceList) {
        this.quotePriceList = quotePriceList;
    }

}
