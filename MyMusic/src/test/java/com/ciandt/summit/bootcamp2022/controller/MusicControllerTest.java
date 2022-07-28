package com.ciandt.summit.bootcamp2022.controller;

import com.ciandt.summit.bootcamp2022.controller.dto.ResponseDTO;
import com.ciandt.summit.bootcamp2022.entity.Artista;
import com.ciandt.summit.bootcamp2022.entity.Musica;
import com.ciandt.summit.bootcamp2022.exceptions.ErrorException;
import com.ciandt.summit.bootcamp2022.exceptions.ErrorResponse;
import com.ciandt.summit.bootcamp2022.service.MusicaService;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
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

        ResponseDTO responseDTOReturn = new ResponseDTO(listaDeMusicas);

        when(musicaService.buscarMusicas("bru")).thenReturn(responseDTOReturn);

        ResponseEntity<ResponseDTO> response = musicController.buscar("bru");

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void whenBuscarMusicasWithIvalidFilter(){

        when(musicaService.buscarMusicas("b")).thenThrow(new ErrorException("Erro ao filtrar musicas!"));

       Exception exception = assertThrows(ErrorException.class, () -> {
            musicController.buscar("b");
        }, "Erro ao filtrar musicas!");

       ErrorResponse errorResponse = new ErrorResponse(400, exception.getMessage());

      assertEquals(HttpStatus.BAD_REQUEST.value(), errorResponse.getStatus());
    }

    @Test
    void whenBuscarMusicasThenReturnNotNull(){

        Artista artista = new Artista("Bruno Mars");

        Musica musica = new Musica("Talking to the moon", artista);

        List<Musica> listaDeMusicas = new ArrayList<>();
        listaDeMusicas.add(musica);

        ResponseDTO responseDTOReturn = new ResponseDTO(listaDeMusicas);

        when(musicaService.buscarMusicas("bru")).thenReturn(responseDTOReturn);

        ResponseEntity<ResponseDTO> response = musicController.buscar("bru");

        assertNotNull(response.getBody());

    }

    @Test
    void whenBuscarMusicasThenReturnErrorException(){

        List<Musica> musicaList = new ArrayList<>();

        ResponseDTO responseDTO = new ResponseDTO(musicaList);

        when(musicaService.buscarMusicas("saiu")).thenReturn(responseDTO);

        ResponseEntity<ResponseDTO> response = musicController.buscar("saiu");

        assertNull(response.getBody());
    }

}
