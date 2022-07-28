package com.ciandt.summit.bootcamp2022.controller;

import com.ciandt.summit.bootcamp2022.controller.dto.ResponseDTO;
import com.ciandt.summit.bootcamp2022.entity.Artist;
import com.ciandt.summit.bootcamp2022.entity.Music;
import com.ciandt.summit.bootcamp2022.exceptions.ErrorException;
import com.ciandt.summit.bootcamp2022.exceptions.ErrorResponse;
import com.ciandt.summit.bootcamp2022.service.PlayListService;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@SpringBootTest
class PlaylistControllerTest {

    @InjectMocks
    private PlaylistController playlistController;

    @Mock
    private PlayListService playListService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void whenAddMusicToPlayListThenReturn201() throws Exception {

        Artist artist = new Artist();
        artist.setName("Alefe");

        Music music = new Music("Alefe Patrick", artist);

        List<Music> musicList = new ArrayList<>();
        musicList.add(music);
        ResponseDTO responseDTO = new ResponseDTO(musicList);
        when(playListService.addSongInPlayList(music, "123-456-789")).thenReturn(responseDTO);

        ResponseEntity<ResponseDTO> response = playlistController
                .addSongInPlayList(music, "123-456-789");
        assertNotNull(response.getBody());
    }

    @Test
    void whenMusicToPlayListThenReturnWithBodyNotNull(){

        Artist artist = new Artist();
        artist.setName("Alefe");

        Music music = new Music();

        music.setId("haghasghhagsas");
        music.setName("Alefe Patrick");
        music.setArtist(artist);
        List<Music> musicList = new ArrayList<>();
        musicList.add(music);
        ResponseDTO responseDTO = new ResponseDTO(musicList);
        when(playListService.addSongInPlayList(music, "123-456-789")).thenReturn(responseDTO);

        ResponseEntity<ResponseDTO> response = playlistController.addSongInPlayList(music, "123-456-789");

        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        assertNotNull(response.getBody());
    }

    @Test
    void whenRemoveMusicFromPlayListThenReturnStatusCodeOK(){

        when(playListService.removeSongFromPlayList("123", "123"))
                .thenReturn("Song 123 removed from playlist successfull.");
        ResponseEntity<String> response = playlistController.removerMusicaDaPLaylist("123", "123");

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void whenRemoveMusicFromPlayListThenReturnStringMessageSuccess(){

        when(playListService.removeSongFromPlayList("123", "123"))
                .thenReturn("Song 123 removed from playlist successfull.");
        ResponseEntity<String> response = playlistController.removerMusicaDaPLaylist("123", "123");

        assertEquals("Song 123 removed from playlist successfull.", response.getBody());
    }

    @Test
    void whenRemoveMusicFromPlayListWithIdMusicNotFoundAtDataBase(){
        when(playListService.removeSongFromPlayList("123456", "123"))
                .thenThrow(new ErrorException("Song not found."));

      Exception exception = assertThrows(ErrorException.class, () -> {
          playlistController.removerMusicaDaPLaylist("123456", "123");
      }, "Song not found.");

        ErrorResponse error = new ErrorResponse(400, exception.getMessage());

        assertEquals("Song not found.", error.getMessage());
    }

    @Test
    void whenRemoveMusicFromPlayListWithIdPlayListNotFoundAtDataBase(){
        when(playListService.removeSongFromPlayList("123", "123456"))
                .thenThrow(new ErrorException("PlayList not found"));

        Exception exception = assertThrows(ErrorException.class, () -> {
            playlistController.removerMusicaDaPLaylist("123", "123456");
        }, "PlayList not found");

        ErrorResponse error = new ErrorResponse(400, exception.getMessage());

        assertEquals("PlayList not found", error.getMessage());
    }

    @Test
    void whenRemoveMusicFromPlayListWithIdMusicaFoundAtPlayList(){
        when(playListService.removeSongFromPlayList("123456", "159"))
                .thenThrow(new ErrorException("PlayList Song not found in playlist."));

        Exception exception = assertThrows(ErrorException.class, () -> {
            playlistController.removerMusicaDaPLaylist("123456", "159");
        }, "PlayList Song not found in playlist.");

        ErrorResponse error = new ErrorResponse(400, exception.getMessage());

        assertEquals("PlayList Song not found in playlist.", error.getMessage());
    }

}