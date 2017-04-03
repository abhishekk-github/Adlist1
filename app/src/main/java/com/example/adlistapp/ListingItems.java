package com.example.adlistapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abhishek on 2/4/17.
 */

public class ListingItems {

  String name;
  int actualprice;
  int specialprice;
  int discountpercent;
  int discountprice;
  int startIn;
  int expireIn;

  String impressiontracking;
  String targeturl;
  String directimageurl;
  String brand;
  String offertype;

  String banner_description;

  boolean impressingTrackingFlag;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getBanner_description() {
    return banner_description;
  }

  public void setBanner_description(String banner_description) {
    this.banner_description = banner_description;
  }

  public int getActualprice() {
    return actualprice;
  }

  public void setActualprice(int actualprice) {
    this.actualprice = actualprice;
  }

  public int getSpecialprice() {
    return specialprice;
  }

  public void setSpecialprice(int specialprice) {
    this.specialprice = specialprice;
  }

  public int getDiscountpercent() {
    return discountpercent;
  }

  public void setDiscountpercent(int discountpercent) {
    this.discountpercent = discountpercent;
  }

  public int getDiscountprice() {
    return discountprice;
  }

  public void setDiscountprice(int discountprice) {
    this.discountprice = discountprice;
  }

  public int getStartIn() {
    return startIn;
  }

  public void setStartIn(int startIn) {
    this.startIn = startIn;
  }

  public int getExpireIn() {
    return expireIn;
  }

  public void setExpireIn(int expireIn) {
    this.expireIn = expireIn;
  }

  public String getImpressiontracking() {
    return impressiontracking;
  }

  public void setImpressiontracking(String impressiontracking) {
    this.impressiontracking = impressiontracking;
  }

  public String getTargeturl() {
    return targeturl;
  }

  public void setTargeturl(String targeturl) {
    this.targeturl = targeturl;
  }

  public String getDirectimageurl() {
    return directimageurl;
  }

  public void setDirectimageurl(String directimageurl) {
    this.directimageurl = directimageurl;
  }

  public String getBrand() {
    return brand;
  }

  public void setBrand(String brand) {
    this.brand = brand;
  }

  public String getOffertype() {
    return offertype;
  }

  public void setOffertype(String offertype) {
    this.offertype = offertype;
  }

  public boolean isImpressingTrackingFlag() {
    return impressingTrackingFlag;
  }

  public void setImpressingTrackingFlag(boolean impressingTrackingFlag) {
    this.impressingTrackingFlag = impressingTrackingFlag;
  }

  public static List<ListingItems> getItemListFromJson(String JsonResult) {
    List<ListingItems> itemList =  new ArrayList<>();
    try {
      JSONObject rootJson = new JSONObject(JsonResult);
      JSONArray arrJson= rootJson.getJSONArray("updatelist");

      for(int i =0 ; i< arrJson.length() ; i++) {
        JSONObject elementJson = arrJson.getJSONObject(i);
        JSONObject elementProductAttribute = elementJson.getJSONObject("adwallproductattributes");
        JSONObject elementImageAttribute = elementJson.getJSONObject("imageattributes");
        ListingItems item =  new ListingItems();
        item.setOffertype(elementJson.optString("offertype"));
        item.setImpressiontracking(elementImageAttribute.optString("impressiontracking"));
        item.setDirectimageurl(elementImageAttribute.optString("directimageurl"));
        item.setName(elementProductAttribute.optString("name"));
        item.setActualprice(elementProductAttribute.optInt("actualprice"));
        item.setSpecialprice(elementProductAttribute.optInt("specialprice"));
        item.setDiscountpercent(elementProductAttribute.optInt("discountpercent"));
        item.setDiscountprice(elementProductAttribute.optInt("discountprice"));
        item.setBrand(elementProductAttribute.optString("brand"));
        item.setTargeturl(elementJson.optString("targeturl"));
        item.setStartIn(elementProductAttribute.optInt("startIn"));
        item.setExpireIn(elementProductAttribute.optInt("expireIn"));
        if("App".equalsIgnoreCase(item.getOffertype()) || "VAS".equalsIgnoreCase(item.getOffertype())){
          JSONObject elementAppAttribute = elementJson.getJSONObject("adwallappattributes");
          item.setName(elementAppAttribute.optString("title"));
          item.setBanner_description(elementAppAttribute.optString("subtitle"));
          item.setImpressingTrackingFlag(false);
        }
        itemList.add(item);
      }
    } catch (JSONException e) {
      e.printStackTrace();
    }
    return itemList;
  }
}
