package com.drinkssu.yourvoicealarm.AlarmRank;

/**
 * Created by FlaShilver on 14. 12. 5..
 */
public class RankInfoModel {

    private int id;
    private int rankNum;
    private String alarm_text;
    private float lank_point;
    private String nickname;
    private int category;
    private boolean gender;

    public RankInfoModel(int id, int rankNum, String alarm_text, float lank_point, String nickname, int category, boolean gender) {
        this.id = id;
        this.rankNum = rankNum;
        this.alarm_text = alarm_text;
        this.lank_point = lank_point;
        this.nickname = nickname;
        this.category = category;
        this.gender = gender;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRankNum() {
        return rankNum;
    }

    public void setRankNum(int rankNum) {
        this.rankNum = rankNum;
    }

    public String getAlarm_text() {
        return alarm_text;
    }

    public void setAlarm_text(String alarm_text) {
        this.alarm_text = alarm_text;
    }

    public float getLank_point() {
        return lank_point;
    }

    public void setLank_point(float lank_point) {
        this.lank_point = lank_point;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }
}