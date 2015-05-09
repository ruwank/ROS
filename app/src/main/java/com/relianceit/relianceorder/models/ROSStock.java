package com.relianceit.relianceorder.models;

import android.util.Log;

import com.google.gson.annotations.Expose;

/**
 * Created by sura on 4/28/15.
 */
public class ROSStock {

    public static final String TAG = ROSStock.class.getSimpleName();

    private int availableQuantity = 0;
    private int status = 0;

    @Expose private int QuntityInStock = 0;
    @Expose private String ProductDescription = null;
    @Expose private String ProductBatchCode = null;
    @Expose private String AgenCode = null;
    @Expose private String AgenName = null;
    @Expose private String BrandCode = null;
    @Expose private String BrandName = null;
    @Expose private String ProductCode = null;
    @Expose private String CompCode = null;
    @Expose private String DistributorCode = null;
    @Expose private double UnitPrice = 0.0;


    public ROSStock() {
        this.availableQuantity = 0;
        this.status = 0;

        this.ProductDescription = null;
        this.ProductBatchCode = null;
        this.QuntityInStock = 0;
        this.AgenCode = null;
        this.AgenName = null;
        this.BrandCode = null;
        this.BrandName = null;
        this.ProductCode = null;
        this.DistributorCode = null;
        this.CompCode = null;
        this.UnitPrice = 0.0;
    }

    public void print() {
        Log.d(TAG, ProductDescription + " " +
                ProductBatchCode + " " +
                QuntityInStock + " " +
                AgenCode + " " +
                AgenName + " " +
                BrandCode + " " +
                BrandName + " " +
                ProductCode + " " +
                DistributorCode + " " +
                status + " " +
                availableQuantity + " " +
                CompCode + " " +
                UnitPrice);
    }

    public String getProductDescription() {
        return ProductDescription;
    }

    public void setProductDescription(String productDescription) {
        this.ProductDescription = productDescription;
    }

    public String getProductBatchCode() {
        return ProductBatchCode;
    }

    public void setProductBatchCode(String productBatchCode) {
        this.ProductBatchCode = productBatchCode;
    }

    public String getAgenName() {
        return AgenName;
    }

    public void setAgenName(String agenName) {
        this.AgenName = agenName;
    }

    public int getQuntityInStock() {
        return QuntityInStock;
    }

    public void setQuntityInStock(int quntityInStock) {
        this.QuntityInStock = quntityInStock;
    }

    public int getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(int availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    //gson fields


    public String getAgenCode() {
        return AgenCode;
    }

    public void setAgenCode(String agenCode) {
        AgenCode = agenCode;
    }

    public String getBrandCode() {
        return BrandCode;
    }

    public void setBrandCode(String brandCode) {
        BrandCode = brandCode;
    }

    public String getBrandName() {
        return BrandName;
    }

    public void setBrandName(String brandName) {
        BrandName = brandName;
    }

    public String getProductCode() {
        return ProductCode;
    }

    public void setProductCode(String productCode) {
        ProductCode = productCode;
    }

    public String getCompCode() {
        return CompCode;
    }

    public void setCompCode(String compCode) {
        CompCode = compCode;
    }

    public String getDistributorCode() {
        return DistributorCode;
    }

    public void setDistributorCode(String distributorCode) {
        DistributorCode = distributorCode;
    }

    public double getUnitPrice() {
        return UnitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        UnitPrice = unitPrice;
    }
}
