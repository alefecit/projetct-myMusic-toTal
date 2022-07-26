package com.ciandt.summit.bootcamp2022.service;

import com.ciandt.summit.bootcamp2022.controller.dto.MusicaDto;
import com.ciandt.summit.bootcamp2022.entity.Musica;
import com.ciandt.summit.bootcamp2022.entity.PlayList;
import com.ciandt.summit.bootcamp2022.exceptions.MusicNotFoundException;
import com.ciandt.summit.bootcamp2022.exceptions.MusicaNaoEncontradaException;
import com.ciandt.summit.bootcamp2022.exceptions.PlayListNaoEncontradaException;
import com.ciandt.summit.bootcamp2022.repository.MusicaRepository;
import com.ciandt.summit.bootcamp2022.repository.PlayListRepository;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
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

    public MusicaDto adicionarMusicaNaPlayList(Musica musicaAdd, String idPlayList){

        Musica musica = musicaRepository.findById(musicaAdd.getId()).orElseThrow(() -> new MusicaNaoEncontradaException());
        PlayList playList = playListRepository.findById(idPlayList).orElseThrow(() -> new PlayListNaoEncontradaException());

        List<Musica> listaMusicas = new ArrayList<>();
        listaMusicas.add(musica);
        MusicaDto musicaDto = new MusicaDto(listaMusicas);

        playList.setMusicas(listaMusicas);
        playListRepository.save(playList);
        return musicaDto;
    }

    public String removerMusicaNaPlayList(String idPlayList, String musicaRemove ){
        Musica musica = musicaRepository.findById(musicaRemove).orElseThrow(() -> new MusicaNaoEncontradaException());
        PlayList playList = playListRepository.findById(idPlayList).orElseThrow(() -> new PlayListNaoEncontradaException());
        List<Musica> musicas = playList.getMusicas().stream().filter(music -> music.equals(musica)).collect(Collectors.toList());
        if(musicas.size() < 1){
            throw new MusicNotFoundException();
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