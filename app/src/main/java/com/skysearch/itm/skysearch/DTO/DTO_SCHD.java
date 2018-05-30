package com.skysearch.itm.skysearch.DTO;

public class DTO_SCHD {
    int schdId;
    int chId;
    int epId;
    String channelName;
    String actn;
    String title;
    String stTime;
    String enTime;

    public boolean isReserved() {
        return isReserved;
    }

    public void setReserved(boolean reserved) {
        isReserved = reserved;
    }

    int type;
    boolean isReserved;
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getSchdId() {
        return schdId;
    }

    public void setSchdId(int schdId) {
        this.schdId = schdId;
    }

    public int getChId() {
        return chId;
    }

    public void setChId(int chId) {
        this.chId = chId;
    }

    public int getEpId() {
        return epId;
    }

    public void setEpId(int epId) {
        this.epId = epId;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getActn() {
        return actn;
    }

    public void setActn(String actn) {
        this.actn = actn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStTime() {
        return stTime;
    }

    public void setStTime(String stTime) {
        this.stTime = stTime;
    }

    public String getEnTime() {
        return enTime;
    }

    public void setEnTime(String enTime) {
        this.enTime = enTime;
    }
}
