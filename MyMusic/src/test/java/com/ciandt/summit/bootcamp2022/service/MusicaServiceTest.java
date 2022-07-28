package com.ciandt.summit.bootcamp2022.service;

import com.ciandt.summit.bootcamp2022.controller.dto.ResponseDTO;
import com.ciandt.summit.bootcamp2022.entity.Artista;
import com.ciandt.summit.bootcamp2022.entity.Musica;
import com.ciandt.summit.bootcamp2022.exceptions.ErrorException;
import com.ciandt.summit.bootcamp2022.repository.MusicaRepository;
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

class MusicaServiceTest {

    @InjectMocks
    private MusicaService musicaService;

    @Mock
    private MusicaRepository musicaRepository;

    @Mock
    private GenericCache<String, ResponseDTO> cache;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void whenFindMusicThenReturnNotNull() {

        List<Musica> musicaList = new ArrayList<>();

        when(musicaRepository.buscarMusicaArtista("bru")).thenReturn(musicaList);

        when(this.cache.get("bru")).thenReturn(Optional.of(new ResponseDTO(musicaList)));

        ResponseDTO responseDTO = musicaService.buscarMusicas("bru");

        assertNotNull(responseDTO);
    }

    @Test
    void whenFindMusicWithOutObjectsOnListThenReturnTrue(){

        List<Musica> musicaList = new ArrayList<>();

        when(this.cache.get("bru")).thenReturn(Optional.of(new ResponseDTO(musicaList)));

        when(musicaRepository.buscarMusicaArtista("bru")).thenReturn(musicaList);

        ResponseDTO responseDTO = musicaService.buscarMusicas("bru");

        assertTrue(responseDTO.getData().isEmpty());
    }

    @Test
    void whenFindMusicWithObjectsOnListThenReturnTrue(){

        Artista artista = new Artista();

        artista.setNome("Leonardo");
        Musica musica = new Musica();

        musica.setNome("Talking to the moon");
        musica.setArtista(artista);

        List<Musica> musicaList = new ArrayList<>();
        musicaList.add(musica);

        when(musicaRepository.buscarMusicaArtista("bru")).thenReturn(musicaList);
        when(this.cache.get("bru")).thenReturn(Optional.of(new ResponseDTO(musicaList)));
        ResponseDTO responseDTO = musicaService.buscarMusicas("bru");

        assertFalse(responseDTO.getData().isEmpty());
    }

    @Test
    void whenBuscarMusicasWithIvalidFilter(){

        when(musicaRepository.buscarMusicaArtista("b")).thenThrow(new ErrorException("Erro ao filtrar musicas!"));
        Exception exception = assertThrows(ErrorException.class, () -> musicaService.buscarMusicas("b"));

        assertEquals("Erro ao filtrar musicas!", exception.getMessage());
    }

    @Test
    void whenFromRepositoryThenReturnResponseDTO(){
        Artista artista = new Artista("Leonardo Oliveira");
        Musica musica = new Musica("Testando 123", artista);

        List<Musica> musicaList = new ArrayList<>();
        musicaList.add(musica);

        when(musicaRepository.buscarMusicaArtista("bru")).thenReturn(musicaList);

        ResponseDTO response = musicaService.fromRepository("bru");

        assertNotNull(response);
    }
}