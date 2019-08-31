package com.example.demoapplication;

public class HotPepperGourmet {
    private String name; // 飲食店の名前
    private String address; // 住所
    private Double lat; // お店の緯度
    private Double lng; // お店の経度
    private String lunch; // ランチ有無
    private String url; // お店のURL
    private String id; // お店コード
    private String gzUrl; // 画像URL

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public String getLunch() {
        return lunch;
    }

    public void setLunch(String lunch) {
        this.lunch = lunch;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGzUrl(){
        return gzUrl;
    }

    public void setGzUrl(String gzUrl){
        this.gzUrl = gzUrl;
    }
}
