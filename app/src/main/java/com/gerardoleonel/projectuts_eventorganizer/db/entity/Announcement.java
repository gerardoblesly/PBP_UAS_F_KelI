package com.gerardoleonel.projectuts_eventorganizer.db.entity;

import java.io.Serializable;

public class Announcement implements Serializable {
    private int id;
    private String judulPengumuman;
    private String isiPengumuman;

    public Announcement(int id, String judulPengumuman, String isiPengumuman) {
        this.id = id;
        this.judulPengumuman = judulPengumuman;
        this.isiPengumuman = isiPengumuman;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJudulPengumuman() {
        return judulPengumuman;
    }

    public void setJudulPengumuman(String judulPengumuman) {
        this.judulPengumuman = judulPengumuman;
    }

    public String getIsiPengumuman() {
        return isiPengumuman;
    }

    public void setIsiPengumuman(String isiPengumuman) {
        this.isiPengumuman = isiPengumuman;
    }
}
