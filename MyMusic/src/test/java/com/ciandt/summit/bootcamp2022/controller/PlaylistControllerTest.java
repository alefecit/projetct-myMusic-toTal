package com.ciandt.summit.bootcamp2022.controller;

import com.ciandt.summit.bootcamp2022.controller.PlaylistController;
import com.ciandt.summit.bootcamp2022.controller.dto.MusicaDto;
import com.ciandt.summit.bootcamp2022.entity.Artista;
import com.ciandt.summit.bootcamp2022.entity.Musica;
import com.ciandt.summit.bootcamp2022.service.PlayListService;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

        Artista artista = new Artista();
        artista.setNome("Alefe");

        Musica musica = new Musica("Alefe Patrick", artista);

        List<Musica> musicaList = new ArrayList<>();
        musicaList.add(musica);
        MusicaDto musicaDto = new MusicaDto(musicaList);
        when(playListService.adicionarMusicaNaPlayList(musica, "123-456-789")).thenReturn(musicaDto);

        ResponseEntity<MusicaDto> response = playlistController.adicionarMusicasNaPLaylist(musica, "123-456-789");
        assertNotNull(response.getBody());
    }

    @Test
    void whenMusicToPlayListThenReturnWithBodyNotNull(){

        Artista artista = new Artista();
        artista.setNome("Alefe");

        Musica musica = new Musica();

        musica.setId("haghasghhagsas");
        musica.setNome("Alefe Patrick");
        musica.setArtista(artista);
        List<Musica> musicaList = new ArrayList<>();
        musicaList.add(musica);
        MusicaDto musicaDto = new MusicaDto(musicaList);
        when(playListService.adicionarMusicaNaPlayList(musica, "123-456-789")).thenReturn(musicaDto);

        ResponseEntity<MusicaDto> response = playlistController.adicionarMusicasNaPLaylist(musica, "123-456-789");

        assertEquals(HttpStatus.CREATED, response.getStatusCode());


        assertNotNull(response.getBody());
    }

}