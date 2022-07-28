package com.ciandt.summit.bootcamp2022.service;

import com.ciandt.summit.bootcamp2022.controller.dto.ResponseDTO;
import com.ciandt.summit.bootcamp2022.entity.Artist;
import com.ciandt.summit.bootcamp2022.entity.Music;
import com.ciandt.summit.bootcamp2022.entity.PlayList;
import com.ciandt.summit.bootcamp2022.exceptions.ErrorException;
import com.ciandt.summit.bootcamp2022.repository.MusicRepository;
import com.ciandt.summit.bootcamp2022.repository.PlayListRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class PlayListServiceTest {

    @InjectMocks
    private PlayListService playListService;

    @Mock
    private PlayListRepository playListRepository;

    @Mock
    private MusicRepository musicRepository;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void whenAddMusicToPlaylistThenReturn201() {

        Music musica =  new Music();
        PlayList playList = new PlayList();
        musica.setId("hagsagsha");
        List<Music> musicList = new ArrayList<>();
        musicList.add(musica);
        playList.setId("jashasa");
        when(playListRepository.save(playList)).thenReturn(playList);
        when(musicRepository.findById("hagsagsha")).thenReturn(Optional.of(musica));
        when(playListRepository.findById("jashasa")).thenReturn(Optional.of(playList));
        ResponseDTO addMusicaPlaylist = playListService.addSongInPlayList(musica, "jashasa");

        assertFalse(addMusicaPlaylist.getData().isEmpty());
        for (Music music: addMusicaPlaylist.getData()) {
            assertEquals("hagsagsha", music.getId());
        }

        assertEquals("jashasa", playList.getId());
    }

    @Test
    void whenAddMusicToPlaylistThenReturnMusicaNaoEncontradaException(){

        PlayList playList = new PlayList();
        Music music =  new Music();
        music.setId("hagsagsha3");
        List<Music> musicList = new ArrayList<>();
        musicList.add(music);
        playList.setId("jashasa");

        when(playListRepository.save(playList)).thenReturn(playList);
        when(musicRepository.findById("hagsagsha")).thenReturn(Optional.of(music));
        when(playListRepository.findById("jashasa")).thenReturn(Optional.of(playList));

        Exception exception = assertThrows(ErrorException.class, () -> playListService.
                addSongInPlayList(music,"jashasa"));
        assertEquals(ErrorException.class,exception.getClass());
    }

    @Test
    void whenAddMusicToPlaylistThenReturnPlaylistNaoEncontradaException(){

        PlayList playList = new PlayList();
        Music music =  new Music();
        music.setId("hagsagsha");
        List<Music> musicList = new ArrayList<>();
        musicList.add(music);
        playList.setId("jashas3");

        when(playListRepository.save(playList)).thenReturn(playList);
        when(musicRepository.findById("hagsagsha")).thenReturn(Optional.of(music));
        when(playListRepository.findById("jashasa3")).thenReturn(Optional.of(playList));

        Exception exception = assertThrows(ErrorException.class, () -> playListService.
                addSongInPlayList(music,"jashasa"));
        assertEquals(ErrorException.class,exception.getClass());
    }

    @Test
    void whenRemoveMusicFromPlayListThenReturnMessageSuccess(){

        Artist artist = new Artist("Leonardo Oliveira");
        Music music = new Music("Testando 123", artist);
        when(musicRepository.findById("123")).thenReturn(Optional.of(music));

        List<Music> musicList = new ArrayList<>();
        musicList.add(music);
        PlayList playList = new PlayList(musicList);
        when(playListRepository.findById("123")).thenReturn(Optional.of(playList));

        String response = playListService.removeSongFromPlayList("123", "123");

        assertEquals("Song 123 removed from playlist successfull.", response);
    }

    @Test
    void whenRemoveMusicFromPlayListLookingWithIdMusicNotFoundAtDataBase(){

        Artist artist = new Artist("Leonardo Oliveira");
        Music music = new Music("Testando 123", artist);
        when(musicRepository.findById("123456")).thenReturn(Optional.of(music));

        List<Music> musicList = new ArrayList<>();
        musicList.add(music);
        PlayList playList = new PlayList(musicList);
        when(playListRepository.findById("654321")).thenReturn(Optional.of(playList));

        assertThrows(ErrorException.class, () -> playListService
                .removeSongFromPlayList("654321", "123"));
    }

    @Test
    void whenRemoveMusicFromPlayListLookingWithIdPlayListNotFoundAtDataBase(){

        Artist artist = new Artist("Leonardo Oliveira");
        Music music = new Music("Testando 123", artist);
        when(musicRepository.findById("123456")).thenReturn(Optional.of(music));

        List<Music> musicList = new ArrayList<>();
        musicList.add(music);
        PlayList playList = new PlayList(musicList);
        when(playListRepository.findById("654321")).thenReturn(Optional.of(playList));

        assertThrows(ErrorException.class, () -> playListService
                .removeSongFromPlayList("123", "123456"));
    }

    @Test
    void whenRemoveMusicFromPlayListLookingWithIdMusicaNotFoundAtPlayList(){

        Artist artist = new Artist("Leonardo Oliveira");
        Music music = new Music("Testando 123", artist);
        when(musicRepository.findById("123456")).thenReturn(Optional.of(music));

        List<Music> musicList = new ArrayList<>();

        PlayList playList = new PlayList(musicList);
        when(playListRepository.findById("654321")).thenReturn(Optional.of(playList));

        assertThrows(ErrorException.class, () -> playListService.
                removeSongFromPlayList("654321", "123456"));
    }


}