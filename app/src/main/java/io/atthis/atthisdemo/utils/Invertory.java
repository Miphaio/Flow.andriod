package io.atthis.atthisdemo.utils;

import android.net.Uri;
import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
/**
 * Created by Jacky on 9/2/17.
 */

public class Invertory {
    private final static String ATTHIS_BASE_URL = "http://atthis.sushithedog.com/src/api/";
    private final static String ATTHIS_CAR_INVENTORY = "Car/GetAllCars.php";
    private final static String ATTHIS_CAR_DETAIL = "Detail/GetDetailDetail.php";

    public static class SearchResult implements Serializable {
        public String make;
        public String model;
        public int year;
        public String vin;
        public String mileage;
        public String exteriorColor;
        public String interiorColor;
        public String fuel;
        public String engine;
        public String transmission;
        public String driveType;
        public String bodyStyle;
        public String comments;
        public String picture;

    }

    public static String buildAtthisInventoryURL(String queryValue) {
        return Uri.parse(ATTHIS_BASE_URL).buildUpon()
                .appendQueryParameter(ATTHIS_CAR_DETAIL, queryValue)
                .build()
                .toString();

    }

    public static ArrayList<SearchResult> parseGitHubSearchResultsJSON(String searchResultsJSON) {
        try {
            JSONObject searchResultsObj = new JSONObject(searchResultsJSON);
            JSONArray searchResultsItems = searchResultsObj.getJSONArray("items");

            ArrayList<SearchResult> searchResultsList = new ArrayList<SearchResult>();
            for (int i = 0; i < searchResultsItems.length(); i++) {
                SearchResult searchResult = new SearchResult();
                JSONObject searchResultItem = searchResultsItems.getJSONObject(i);
                searchResult.fullName = searchResultItem.getString("full_name");
                searchResult.description = searchResultItem.getString("description");
                searchResult.htmlURL = searchResultItem.getString("html_url");
                searchResult.stars = searchResultItem.getInt("stargazers_count");
                searchResultsList.add(searchResult);
            }
            return searchResultsList;
        } catch (JSONException e) {
            return null;
        }
    }
}
