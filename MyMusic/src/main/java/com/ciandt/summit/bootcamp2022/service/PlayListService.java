package com.ciandt.summit.bootcamp2022.service;

import com.ciandt.summit.bootcamp2022.controller.dto.ResponseDTO;
import com.ciandt.summit.bootcamp2022.entity.Music;
import com.ciandt.summit.bootcamp2022.entity.PlayList;
import com.ciandt.summit.bootcamp2022.exceptions.*;
import com.ciandt.summit.bootcamp2022.repository.MusicRepository;
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
    private MusicRepository musicRepository;

    @Autowired
    private MusicService musicService;

    public ResponseDTO addSongInPlayList(Music musicAdd, String idPlayList){

        Music music = musicRepository.findById(musicAdd.getId()).orElseThrow(() -> new ErrorException("Song not found."));
        PlayList playList = playListRepository.findById(idPlayList).orElseThrow(() -> new ErrorException("PlayList not found"));

        List<Music> songList = new ArrayList<>();
        songList.add(music);
        ResponseDTO responseDTO = new ResponseDTO(songList);

        playList.setMusicas(songList);
        playListRepository.save(playList);
        return responseDTO;
    }

    public String removeSongFromPlayList(String idPlayList, String songRemove){

        Music musicFind = musicRepository.findById(songRemove).orElseThrow(() -> new ErrorException("Song not found."));
        PlayList playList = playListRepository.findById(idPlayList).orElseThrow(() -> new ErrorException("PlayList not found"));

        List<Music> musics = playList.getMusicas().stream().filter(music -> music.equals(musicFind)).collect(Collectors.toList());

        if(musics.size() < 1){
            throw new ErrorException("PlayList Song not found in playlist.");
        }

        try {
            playList.getMusicas().remove(musicFind);
            playListRepository.save(playList);
            return "Song "+songRemove+" removed from playlist successfull.";
        }catch (Exception e){
            return e.getMessage();
        }
    }
}