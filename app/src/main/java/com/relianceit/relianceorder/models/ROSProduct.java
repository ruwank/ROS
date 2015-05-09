package com.relianceit.relianceorder.models;

/**
 * Created by sura on 5/5/15.
 */
public class ROSProduct {

    private String productName = null;
    private String batchName = null;
    private String brandName = null;
    private String agencyName = null;

    public ROSProduct() {
        this.productName = null;
        this.batchName = null;
        this.brandName = null;
        this.agencyName = null;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getBatchName() {
        return batchName;
    }

    public void setBatchName(String batchName) {
        this.batchName = batchName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }
}
