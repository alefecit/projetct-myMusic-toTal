package com.ciandt.summit.bootcamp2022.service;

import com.ciandt.summit.bootcamp2022.controller.dto.ResponseDTO;
import com.ciandt.summit.bootcamp2022.entity.Musica;
import com.ciandt.summit.bootcamp2022.entity.PlayList;
import com.ciandt.summit.bootcamp2022.exceptions.*;
import com.ciandt.summit.bootcamp2022.repository.MusicaRepository;
import com.ciandt.summit.bootcamp2022.repository.PlayListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlayListService {

    @Autowired
    private PlayListRepository playListRepository;

    @Autowired
    private MusicaRepository musicaRepository;

    @Autowired
    private MusicaService musicaService;

    public ResponseDTO adicionarMusicaNaPlayList(Musica musicaAdd, String idPlayList){

        Musica musica = musicaRepository.findById(musicaAdd.getId()).orElseThrow(() -> new ErrorException("Música não encontrada!"));
        PlayList playList = playListRepository.findById(idPlayList).orElseThrow(() -> new ErrorException("PlayList não encontrada!"));

        List<Musica> listaMusicas = new ArrayList<>();
        listaMusicas.add(musica);
        ResponseDTO responseDTO = new ResponseDTO(listaMusicas);

        playList.setMusicas(listaMusicas);
        playListRepository.save(playList);
        return responseDTO;
    }

    public String removerMusicaNaPlayList(String idPlayList, String musicaRemove){

        Musica musica = musicaRepository.findById(musicaRemove).orElseThrow(() -> new ErrorException("Música não encontrada!"));
        PlayList playList = playListRepository.findById(idPlayList).orElseThrow(() -> new ErrorException("PlayList não encontrada!"));
        List<Musica> musicas = playList.getMusicas().stream().filter(music -> music.equals(musica)).collect(Collectors.toList());

        if(musicas.size() < 1){
            throw new ErrorException("Música não encontrada na playList");
        }

        try {
            playList.getMusicas().remove(musica);
            playListRepository.save(playList);
            return "Música "+musicaRemove+" removida da Playlist com sucesso!";
        }catch (Exception e){
            return e.getMessage();
        }
    }
}