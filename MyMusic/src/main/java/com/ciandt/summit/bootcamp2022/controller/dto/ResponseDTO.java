package com.ciandt.summit.bootcamp2022.controller.dto;

import com.ciandt.summit.bootcamp2022.entity.Music;

import java.util.List;

public class ResponseDTO {

    public ResponseDTO(List<Music> listMusic) {
        this.data = listMusic;
    }

    private List<Music> data;

    public List<Music> getData() {
        return data;
    }

    public void setData(List<Music> data) {
        this.data = data;
    }
}
