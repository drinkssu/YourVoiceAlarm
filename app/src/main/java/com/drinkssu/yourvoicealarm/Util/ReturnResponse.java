package com.drinkssu.yourvoicealarm.Util;

import com.drinkssu.yourvoicealarm.AlarmRank.RankInfoModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by FlaShilver on 14. 12. 6..
 */
public class ReturnResponse {

    private int num_results;
    private int page;
    private int total_pages;
    private ArrayList<RankInfoModel> objects;

    public ReturnResponse(int num_results, int page, int total_pages, ArrayList<RankInfoModel> objects) {
        this.num_results = num_results;
        this.page = page;
        this.total_pages = total_pages;
        this.objects = objects;
    }

    public int getNum_results() {
        return num_results;
    }

    public void setNum_results(int num_results) {
        this.num_results = num_results;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public ArrayList<RankInfoModel> getObjects() {
        return objects;
    }

    public void setObjects(ArrayList<RankInfoModel> objects) {
        this.objects = objects;
    }
}
