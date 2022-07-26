package com.ciandt.summit.bootcamp2022.service;

import com.ciandt.summit.bootcamp2022.controller.dto.MusicaDto;
import com.ciandt.summit.bootcamp2022.entity.Musica;
import com.ciandt.summit.bootcamp2022.exceptions.FiltroErrorException;
import com.ciandt.summit.bootcamp2022.repository.MusicaRepository;
import com.ciandt.summit.bootcamp2022.utils.cache.GenericCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MusicaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MusicaService.class);

    @Autowired
    private MusicaRepository musicaRepository;

    @Autowired
    private GenericCache<String, MusicaDto> cache;


    public MusicaDto buscarMusicas(String filtro){

        if(filtro.length() < 3){
            LOGGER.error("Erro ao filtrar musicas!");
            throw new FiltroErrorException();
        }

        LOGGER.info("Musicas filtradas com sucesso!");
        return this.cache.get(filtro).orElseGet(() -> this.fromRepository(filtro));
    }

    public MusicaDto fromRepository(String filtro) {
        MusicaDto listaDeMusicas = new MusicaDto(musicaRepository.buscarMusicaArtista(filtro));
        this.cache.put(filtro, listaDeMusicas);
        return listaDeMusicas;
    }
}
