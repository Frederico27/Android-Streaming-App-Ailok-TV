package co.smallcademy.livetvapp.models;

import java.io.Serializable;

public class CatYoutube implements Serializable {
    private int id;
    private String name;
    private String thumbnail;
    private  String live_url;

    public CatYoutube(int id, String name, String thumbnail, String live_url) {
        this.id = id;
        this.name = name;
        this.thumbnail = thumbnail;
        this.live_url = live_url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail (String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getLive_url() {return live_url;}

    public void setLive_url(String live_url) {this.live_url = live_url;}

    @Override
    public String toString() {
        return "Channel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", live_url='" + live_url + '\'' +
                '}';
    }

    }




