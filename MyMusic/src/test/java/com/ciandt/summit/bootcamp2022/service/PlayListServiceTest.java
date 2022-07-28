package com.ciandt.summit.bootcamp2022.service;

import com.ciandt.summit.bootcamp2022.controller.dto.MusicaDto;
import com.ciandt.summit.bootcamp2022.entity.Artista;
import com.ciandt.summit.bootcamp2022.entity.Musica;
import com.ciandt.summit.bootcamp2022.entity.PlayList;
import com.ciandt.summit.bootcamp2022.repository.MusicaRepository;
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
    private MusicaRepository musicaRepository;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void whenAddMusicToPlaylistThenReturn201() {

        Musica musica =  new Musica();
        PlayList playList = new PlayList();
        musica.setId("hagsagsha");
        List<Musica> musicaList = new ArrayList<>();
        musicaList.add(musica);
        playList.setId("jashasa");
        when(playListRepository.save(playList)).thenReturn(playList);
        when(musicaRepository.findById("hagsagsha")).thenReturn(Optional.of(musica));
        when(playListRepository.findById("jashasa")).thenReturn(Optional.of(playList));
        MusicaDto addMusicaPlaylist = playListService.adicionarMusicaNaPlayList(musica, "jashasa");

        assertFalse(addMusicaPlaylist.getData().isEmpty());
        for (Musica music: addMusicaPlaylist.getData()) {
            assertEquals("hagsagsha", music.getId());
        }

        assertEquals("jashasa", playList.getId());
    }

    @Test
    void whenAddMusicToPlaylistThenReturnMusicaNaoEncontradaException(){

        PlayList playList = new PlayList();
        Musica musica =  new Musica();
        musica.setId("hagsagsha3");
        List<Musica> musicaList = new ArrayList<>();
        musicaList.add(musica);
        playList.setId("jashasa");

        when(playListRepository.save(playList)).thenReturn(playList);
        when(musicaRepository.findById("hagsagsha")).thenReturn(Optional.of(musica));
        when(playListRepository.findById("jashasa")).thenReturn(Optional.of(playList));

        Exception exception = assertThrows(MusicaNaoEncontradaException.class, () -> playListService.adicionarMusicaNaPlayList(musica,"jashasa"));
        assertEquals(MusicaNaoEncontradaException.class,exception.getClass());
    }

    @Test
    void whenAddMusicToPlaylistThenReturnPlaylistNaoEncontradaException(){

        PlayList playList = new PlayList();
        Musica musica =  new Musica();
        musica.setId("hagsagsha");
        List<Musica> musicaList = new ArrayList<>();
        musicaList.add(musica);
        playList.setId("jashas3");

        when(playListRepository.save(playList)).thenReturn(playList);
        when(musicaRepository.findById("hagsagsha")).thenReturn(Optional.of(musica));
        when(playListRepository.findById("jashasa3")).thenReturn(Optional.of(playList));

        Exception exception = assertThrows(PlayListNaoEncontradaException.class, () -> playListService.adicionarMusicaNaPlayList(musica,"jashasa"));
        assertEquals(PlayListNaoEncontradaException.class,exception.getClass());
    }

    @Test
    void whenRemoveMusicFromPlayListThenReturnMessageSuccess(){

        Artista artista = new Artista("Leonardo Oliveira");
        Musica musica = new Musica("Testando 123", artista);
        when(musicaRepository.findById("123")).thenReturn(Optional.of(musica));

        List<Musica> musicaList = new ArrayList<>();
        musicaList.add(musica);
        PlayList playList = new PlayList(musicaList);
        when(playListRepository.findById("123")).thenReturn(Optional.of(playList));

        String response = playListService.removerMusicaNaPlayList("123", "123");

        assertEquals("Música 123 removida da Playlist com sucesso!", response);
    }

    @Test
    void whenRemoveMusicFromPlayListLookingWithIdMusicNotFoundAtDataBase(){

        Artista artista = new Artista("Leonardo Oliveira");
        Musica musica = new Musica("Testando 123", artista);
        when(musicaRepository.findById("123456")).thenReturn(Optional.of(musica));

        List<Musica> musicaList = new ArrayList<>();
        musicaList.add(musica);
        PlayList playList = new PlayList(musicaList);
        when(playListRepository.findById("654321")).thenReturn(Optional.of(playList));

        assertThrows(MusicaNaoEncontradaException.class, () -> playListService.removerMusicaNaPlayList("654321", "123"));
    }

    @Test
    void whenRemoveMusicFromPlayListLookingWithIdPlayListNotFoundAtDataBase(){

        Artista artista = new Artista("Leonardo Oliveira");
        Musica musica = new Musica("Testando 123", artista);
        when(musicaRepository.findById("123456")).thenReturn(Optional.of(musica));

        List<Musica> musicaList = new ArrayList<>();
        musicaList.add(musica);
        PlayList playList = new PlayList(musicaList);
        when(playListRepository.findById("654321")).thenReturn(Optional.of(playList));

        assertThrows(PlayListNaoEncontradaException.class, () -> playListService.removerMusicaNaPlayList("123", "123456"));
    }

    @Test
    void whenRemoveMusicFromPlayListLookingWithIdMusicaNotFoundAtPlayList(){

        Artista artista = new Artista("Leonardo Oliveira");
        Musica musica = new Musica("Testando 123", artista);
        when(musicaRepository.findById("123456")).thenReturn(Optional.of(musica));

        List<Musica> musicaList = new ArrayList<>();

        PlayList playList = new PlayList(musicaList);
        when(playListRepository.findById("654321")).thenReturn(Optional.of(playList));

        assertThrows(MusicNotFoundException.class, () -> playListService.removerMusicaNaPlayList("654321", "123456"));
    }
}