package com.example.adlistapp;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by abhishek on 2/4/17.
 */

public class ListStyleModel {

  String creativeType;
  String placementName;
  String titleFontStyle;
  String adsFontStyle;
  String sectionTitle;
  String searchImage;
  String backImage;
  String loadingImage;
  String aduniturl;
  String color;

  int titleFontSize;
  int adsFontSize;
  boolean isAdUnitUrlFired;

  public boolean isAdUnitUrlFired() {
    return isAdUnitUrlFired;
  }

  public void setAdUnitUrlFired(boolean adUnitUrlFired) {
    isAdUnitUrlFired = adUnitUrlFired;
  }

  public ListStyleModel() {
  }

  public String getCreativeType() {
    return creativeType;
  }

  public void setCreativeType(String creativeType) {
    this.creativeType = creativeType;
  }

  public String getPlacementName() {
    return placementName;
  }

  public void setPlacementName(String placementName) {
    this.placementName = placementName;
  }

  public String getTitleFontStyle() {
    return titleFontStyle;
  }

  public void setTitleFontStyle(String titleFontStyle) {
    this.titleFontStyle = titleFontStyle;
  }

  public String getAdsFontStyle() {
    return adsFontStyle;
  }

  public void setAdsFontStyle(String adsFontStyle) {
    this.adsFontStyle = adsFontStyle;
  }

  public String getSectionTitle() {
    return sectionTitle;
  }

  public void setSectionTitle(String sectionTitle) {
    this.sectionTitle = sectionTitle;
  }

  public String getSearchImage() {
    return searchImage;
  }

  public void setSearchImage(String searchImage) {
    this.searchImage = searchImage;
  }

  public String getBackImage() {
    return backImage;
  }

  public void setBackImage(String backImage) {
    this.backImage = backImage;
  }

  public String getLoadingImage() {
    return loadingImage;
  }

  public void setLoadingImage(String loadingImage) {
    this.loadingImage = loadingImage;
  }

  public String getAduniturl() {
    return aduniturl;
  }

  public void setAduniturl(String aduniturl) {
    this.aduniturl = aduniturl;
  }

  public String getColor() {
    return color;
  }

  public void setColor(String color) {
    this.color = color;
  }

  public int getTitleFontSize() {
    return titleFontSize;
  }

  public void setTitleFontSize(int titleFontSize) {
    this.titleFontSize = titleFontSize;
  }

  public int getAdsFontSize() {
    return adsFontSize;
  }

  public void setAdsFontSize(int adsFontSize) {
    this.adsFontSize = adsFontSize;
  }

  public static ListStyleModel getModelFromJson(String JsonResult) {
    ListStyleModel mListStyleModel = new ListStyleModel();
    try {
      JSONObject rootJson = new JSONObject(JsonResult);
      mListStyleModel.setCreativeType(rootJson.optString("creativeType"));
      mListStyleModel.setPlacementName(rootJson.optString("placementName"));
      mListStyleModel.setColor(rootJson.optString("color"));
      mListStyleModel.setTitleFontStyle(rootJson.optString("titleFontStyle"));
      mListStyleModel.setAdsFontStyle(rootJson.optString("adsFontStyle"));
      mListStyleModel.setSectionTitle(rootJson.optString("sectionTitle"));
      mListStyleModel.setSearchImage(rootJson.optString("searchImage"));
      mListStyleModel.setBackImage(rootJson.optString("backImage"));
      mListStyleModel.setLoadingImage(rootJson.optString("loadingImage"));
      mListStyleModel.setAduniturl(rootJson.optString("aduniturl"));
      mListStyleModel.setAdsFontSize(rootJson.optInt("adsFontSize"));
      mListStyleModel.setTitleFontSize(rootJson.optInt("titleFontSize"));
      mListStyleModel.setAdUnitUrlFired(false);
    } catch (JSONException e) {
      e.printStackTrace();
    }
    return mListStyleModel;
  }
}
