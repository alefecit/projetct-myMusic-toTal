package com.ciandt.summit.bootcamp2022.controller;

import com.ciandt.summit.bootcamp2022.controller.dto.ResponseDTO;
import com.ciandt.summit.bootcamp2022.service.MusicService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/music")
public class MusicController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MusicController.class);
    @Autowired
    private MusicService service;

    @GetMapping
    public ResponseEntity<String> get() {
        return ResponseEntity.ok("67f5976c-eb1e-404e-8220-2c2a8a23be47");
    }

    @GetMapping("/buscar")
    public ResponseEntity<ResponseDTO> findByFilter(@RequestParam(name = "filter", required = false) String filtro){

        LOGGER.info("accessing find Songs method by filter");
        ResponseDTO data = service.findMusic(filtro);

        if(data.getData().isEmpty()){
            LOGGER.info("Songs not found.");
            return ResponseEntity.noContent().build();
        }

        LOGGER.info("Songs returned successful.");
        return ResponseEntity.ok(data);
    }
}