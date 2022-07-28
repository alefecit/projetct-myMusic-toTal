package com.ciandt.summit.bootcamp2022.service;

import com.ciandt.summit.bootcamp2022.controller.dto.ResponseDTO;
import com.ciandt.summit.bootcamp2022.exceptions.ErrorException;
import com.ciandt.summit.bootcamp2022.repository.MusicaRepository;
import com.ciandt.summit.bootcamp2022.utils.cache.GenericCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MusicaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MusicaService.class);

    @Autowired
    private MusicaRepository musicaRepository;

    @Autowired
    private GenericCache<String, ResponseDTO> cache;


    public ResponseDTO buscarMusicas(String filtro){

        if(filtro.length() < 2){
            LOGGER.error("Erro ao filtrar musicas!");
            throw new ErrorException("Erro ao filtrar musicas!");
        }

        LOGGER.info("Musicas filtradas com sucesso!");
        return this.cache.get(filtro).orElseGet(() -> this.fromRepository(filtro));
    }

    public ResponseDTO fromRepository(String filtro) {
        ResponseDTO listaDeMusicas = new ResponseDTO(musicaRepository.buscarMusicaArtista(filtro));
        this.cache.put(filtro, listaDeMusicas);
        return listaDeMusicas;
    }
}
