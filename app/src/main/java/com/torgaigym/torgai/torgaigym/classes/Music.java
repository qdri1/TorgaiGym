package com.torgaigym.torgai.torgaigym.classes;

public class Music {

    private String artist;
    private String name;
    private String uri;

    public Music() {
    }

    public Music(String artist, String name, String uri) {
        this.artist = artist;
        this.name = name;
        this.uri = uri;
    }

    public String getArtist() {
        return artist;
    }

    public String getName() {
        return name;
    }

    public String getUri() {
        return uri;
    }
}
