package com.ciandt.summit.bootcamp2022.service;

import com.ciandt.summit.bootcamp2022.controller.dto.MusicaDto;
import com.ciandt.summit.bootcamp2022.entity.Artista;
import com.ciandt.summit.bootcamp2022.entity.Musica;
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
    private GenericCache<String, MusicaDto> cache;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void whenFindMusicThenReturnNotNull() {

        List<Musica> musicaList = new ArrayList<>();

        when(musicaRepository.buscarMusicaArtista("bru")).thenReturn(musicaList);

        when(this.cache.get("bru")).thenReturn(Optional.of(new MusicaDto(musicaList)));

        MusicaDto musicaDto = musicaService.buscarMusicas("bru");

        assertNotNull(musicaDto);
    }

    @Test
    void whenFindMusicWithOutObjectsOnListThenReturnTrue(){

        List<Musica> musicaList = new ArrayList<>();

        when(this.cache.get("bru")).thenReturn(Optional.of(new MusicaDto(musicaList)));

        when(musicaRepository.buscarMusicaArtista("bru")).thenReturn(musicaList);

        MusicaDto musicaDto = musicaService.buscarMusicas("bru");

        assertTrue(musicaDto.getData().isEmpty());

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
        when(this.cache.get("bru")).thenReturn(Optional.of(new MusicaDto(musicaList)));
        MusicaDto musicaDto = musicaService.buscarMusicas("bru");

        assertFalse(musicaDto.getData().isEmpty());
    }

    @Test
    void whenFindMusicWithThreeTwoThenReturnException(){

        assertThrows(FiltroErrorException.class, () -> musicaService.buscarMusicas("br"));

    }
}