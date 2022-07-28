package com.ciandt.summit.bootcamp2022.controller;

import com.ciandt.summit.bootcamp2022.controller.dto.ResponseDTO;
import com.ciandt.summit.bootcamp2022.entity.Artist;
import com.ciandt.summit.bootcamp2022.entity.Music;
import com.ciandt.summit.bootcamp2022.exceptions.ErrorException;
import com.ciandt.summit.bootcamp2022.exceptions.ErrorResponse;
import com.ciandt.summit.bootcamp2022.service.MusicService;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;

import java.util.List;

@SpringBootTest
public class MusicControllerTest {

    @InjectMocks
    private MusicController musicController;

    @Mock
    private MusicService musicService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void whenGetThenReturnString() throws Exception {

        ResponseEntity<String> response = musicController.get();
        assertEquals("67f5976c-eb1e-404e-8220-2c2a8a23be47",response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void whenBuscarMusicasThenReturnStatusCodeOk(){

        Artist artist = new Artist("Bruno Mars");

        Music music = new Music("Talking to the moon", artist);

        List<Music> songList = new ArrayList<>();
        songList.add(music);

        ResponseDTO responseDTOReturn = new ResponseDTO(songList);

        when(musicService.findMusic("bru")).thenReturn(responseDTOReturn);

        ResponseEntity<ResponseDTO> response = musicController.findByFilter("bru");

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void whenBuscarMusicasWithIvalidFilter(){

        when(musicService.findMusic("b")).thenThrow(new ErrorException("Error when filtering songs."));

       Exception exception = assertThrows(ErrorException.class, () -> {
            musicController.findByFilter("b");
        }, "Error when filtering songs.");

       ErrorResponse errorResponse = new ErrorResponse(400, exception.getMessage());

      assertEquals(HttpStatus.BAD_REQUEST.value(), errorResponse.getStatus());
    }

    @Test
    void whenBuscarMusicasThenReturnNotNull(){

        Artist artist = new Artist("Bruno Mars");

        Music music = new Music("Talking to the moon", artist);

        List<Music> songList = new ArrayList<>();
        songList.add(music);

        ResponseDTO responseDTOReturn = new ResponseDTO(songList);

        when(musicService.findMusic("bru")).thenReturn(responseDTOReturn);

        ResponseEntity<ResponseDTO> response = musicController.findByFilter("bru");

        assertNotNull(response.getBody());

    }

    @Test
    void whenBuscarMusicasThenReturnErrorException(){

        List<Music> musicList = new ArrayList<>();

        ResponseDTO responseDTO = new ResponseDTO(musicList);

        when(musicService.findMusic("saiu")).thenReturn(responseDTO);

        ResponseEntity<ResponseDTO> response = musicController.findByFilter("saiu");

        assertNull(response.getBody());
    }

}
