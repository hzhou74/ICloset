package com.example.icloset.bean;

public class RecordBean {
    private int type;//0Photo 1Camera
    private String path;
    private String WTag="";
    private String TTag="";
    private String typeTag="";

    public RecordBean(int type, String path) {
        this.type = type;
        this.path = path;
    }

    public String getTypeTag() {
        return typeTag;
    }

    public void setTypeTag(String typeTag) {
        this.typeTag = typeTag;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getWTag() {
        return WTag;
    }

    public void setWTag(String WTag) {
        this.WTag = WTag;
    }

    public String getTTag() {
        return TTag;
    }

    public void setTTag(String TTag) {
        this.TTag = TTag;
    }
}
