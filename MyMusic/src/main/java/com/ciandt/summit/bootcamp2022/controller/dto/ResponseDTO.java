package com.ciandt.summit.bootcamp2022.controller.dto;

import com.ciandt.summit.bootcamp2022.entity.Musica;

import java.util.List;

public class ResponseDTO {

    public ResponseDTO(List<Musica> listMusic) {
        this.data = listMusic;
    }

    private List<Musica> data;

    public List<Musica> getData() {
        return data;
    }

    public void setData(List<Musica> data) {
        this.data = data;
    }
}
