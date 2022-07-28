package com.ciandt.summit.bootcamp2022.service;

import com.ciandt.summit.bootcamp2022.controller.dto.ResponseDTO;
import com.ciandt.summit.bootcamp2022.exceptions.ErrorException;
import com.ciandt.summit.bootcamp2022.repository.MusicRepository;
import com.ciandt.summit.bootcamp2022.utils.cache.GenericCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MusicService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MusicService.class);

    @Autowired
    private MusicRepository musicRepository;

    @Autowired
    private GenericCache<String, ResponseDTO> cache;


    public ResponseDTO findMusic(String filter){

        if(filter.length() < 2){
            LOGGER.error("Error when filtering songs.");
            throw new ErrorException("Error when filtering songs.");
        }

        LOGGER.info("Songs filtered successfull.");
        return this.cache.get(filter).orElseGet(() -> this.fromRepository(filter));
    }

    public ResponseDTO fromRepository(String filtro) {
        ResponseDTO songList = new ResponseDTO(musicRepository.findSongsAndArtists(filtro));
        this.cache.put(filtro, songList);
        return songList;
    }
}
