package com.skysearch.itm.skysearch.ListLayout;

public class Program {
    private int type; // for listview version of schedule UI
    private String time, title, channel;
    private boolean isReserved;
    private int image;

    // for gridview version of schedule UI
    public Program(String channel, String title, int image) {
        this.channel = channel;
        this.title = title;
        this.image = image;
    }

    // for listview version of schedule UI
    public Program(int type, String time, String title, boolean isReserved) {
        // 미래
        this.type = type;
        this.time = time;
        this.title = title;
        this.isReserved = isReserved;
    }

    public Program(int type, String title) {
        // 현재
        this.type = type;
        this.title = title;
    }

    public Program(int type, String time, String title) {
        // 과거
        this.type = type;
        this.time = time;
        this.title = title;
    }

    public int getType() {
        return this.type;
    }

    public String getTime() {
        return this.time;
    }

    public String getTitle() {
        return this.title;
    }

    public boolean getReserve() {
        return this.isReserved;
    }

    public int getImage() {
        return this.image;
    }

    public String getChannel() {
        return this.channel;
    }

    public void setReserve(boolean state) {
        isReserved = state;
    }

}
