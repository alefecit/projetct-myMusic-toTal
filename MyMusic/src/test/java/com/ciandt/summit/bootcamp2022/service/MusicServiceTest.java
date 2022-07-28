package com.ciandt.summit.bootcamp2022.service;

import com.ciandt.summit.bootcamp2022.controller.dto.ResponseDTO;
import com.ciandt.summit.bootcamp2022.entity.Artist;
import com.ciandt.summit.bootcamp2022.entity.Music;
import com.ciandt.summit.bootcamp2022.exceptions.ErrorException;
import com.ciandt.summit.bootcamp2022.repository.MusicRepository;
import com.ciandt.summit.bootcamp2022.utils.cache.GenericCache;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class MusicServiceTest {

    @InjectMocks
    private MusicService musicService;

    @Mock
    private MusicRepository musicRepository;

    @Mock
    private GenericCache<String, ResponseDTO> cache;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void whenFindMusicThenReturnNotNull() {

        List<Music> musicList = new ArrayList<>();

        when(musicRepository.findSongsAndArtists("bru")).thenReturn(musicList);

        when(this.cache.get("bru")).thenReturn(Optional.of(new ResponseDTO(musicList)));

        ResponseDTO responseDTO = musicService.findMusic("bru");

        assertNotNull(responseDTO);
    }

    @Test
    void whenFindMusicWithOutObjectsOnListThenReturnTrue(){

        List<Music> musicList = new ArrayList<>();

        when(this.cache.get("bru")).thenReturn(Optional.of(new ResponseDTO(musicList)));

        when(musicRepository.findSongsAndArtists("bru")).thenReturn(musicList);

        ResponseDTO responseDTO = musicService.findMusic("bru");

        assertTrue(responseDTO.getData().isEmpty());
    }

    @Test
    void whenFindMusicWithObjectsOnListThenReturnTrue(){

        Artist artist = new Artist();

        artist.setName("Leonardo");
        Music music = new Music();

        music.setName("Talking to the moon");
        music.setArtist(artist);

        List<Music> musicList = new ArrayList<>();
        musicList.add(music);

        when(musicRepository.findSongsAndArtists("bru")).thenReturn(musicList);
        when(this.cache.get("bru")).thenReturn(Optional.of(new ResponseDTO(musicList)));
        ResponseDTO responseDTO = musicService.findMusic("bru");

        assertFalse(responseDTO.getData().isEmpty());
    }

    @Test
    void whenBuscarMusicasWithIvalidFilter(){

        when(musicRepository.findSongsAndArtists("b")).thenThrow(new ErrorException("Error when filtering songs."));
        Exception exception = assertThrows(ErrorException.class, () -> musicService.findMusic("b"));

        assertEquals("Error when filtering songs.", exception.getMessage());
    }

    @Test
    void whenFromRepositoryThenReturnResponseDTO(){
        Artist artist = new Artist("Leonardo Oliveira");
        Music music = new Music("Testando 123", artist);

        List<Music> musicList = new ArrayList<>();
        musicList.add(music);

        when(musicRepository.findSongsAndArtists("bru")).thenReturn(musicList);

        ResponseDTO response = musicService.fromRepository("bru");

        assertNotNull(response);
    }
}