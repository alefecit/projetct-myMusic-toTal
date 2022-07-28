package com.ciandt.summit.bootcamp2022.controller;

import com.ciandt.summit.bootcamp2022.controller.dto.MusicaDto;
import com.ciandt.summit.bootcamp2022.entity.Artista;
import com.ciandt.summit.bootcamp2022.entity.Musica;
import com.ciandt.summit.bootcamp2022.service.MusicaService;
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
    private MusicaService musicaService;

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

        Artista artista = new Artista("Bruno Mars");

        Musica musica = new Musica("Talking to the moon", artista);

        List<Musica> listaDeMusicas = new ArrayList<>();
        listaDeMusicas.add(musica);

        MusicaDto musicaDtoReturn = new MusicaDto(listaDeMusicas);

        when(musicaService.buscarMusicas("bru")).thenReturn(musicaDtoReturn);

        ResponseEntity<MusicaDto> response = musicController.buscar("bru");

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void whenBuscarMusicasThenReturnNotNull(){

        Artista artista = new Artista("Bruno Mars");

        Musica musica = new Musica("Talking to the moon", artista);

        List<Musica> listaDeMusicas = new ArrayList<>();
        listaDeMusicas.add(musica);

        MusicaDto musicaDtoReturn = new MusicaDto(listaDeMusicas);

        when(musicaService.buscarMusicas("bru")).thenReturn(musicaDtoReturn);

        ResponseEntity<MusicaDto> response = musicController.buscar("bru");

        assertNotNull(response.getBody());

    }

    @Test
    void whenBuscarMusicasThenReturnTrueForNotEmpty(){

        Artista artista = new Artista("Bruno Mars");

        List<Musica> listaDeMusicas = new ArrayList<>();

        MusicaDto musicaDtoReturn = new MusicaDto(listaDeMusicas);

        when(musicaService.buscarMusicas("bru")).thenReturn(musicaDtoReturn);

        ResponseEntity<MusicaDto> response = musicController.buscar("bru");

        assertTrue(response.getBody().getData().isEmpty());

    }

    @Test
    void whenBuscarMusicasThenReturnFalseForNotEmpty(){

        Artista artista = new Artista("Bruno Mars");

        Musica musica = new Musica("Talking to the moon", artista);

        List<Musica> listaDeMusicas = new ArrayList<>();
        listaDeMusicas.add(musica);

        MusicaDto musicaDtoReturn = new MusicaDto(listaDeMusicas);

        when(musicaService.buscarMusicas("bru")).thenReturn(musicaDtoReturn);

        ResponseEntity<MusicaDto> response = musicController.buscar("bru");

        assertFalse(response.getBody().getData().isEmpty());

    }


}
