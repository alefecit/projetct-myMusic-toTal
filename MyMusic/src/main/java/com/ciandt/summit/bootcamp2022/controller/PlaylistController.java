package com.ciandt.summit.bootcamp2022.controller;

import com.ciandt.summit.bootcamp2022.controller.dto.ResponseDTO;
import com.ciandt.summit.bootcamp2022.entity.Musica;
import com.ciandt.summit.bootcamp2022.service.PlayListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/playlists")
public class PlaylistController {

    @Autowired
    private PlayListService playListService;

    @PostMapping("/{playlistId}/musicas")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ResponseDTO> adicionarMusicasNaPLaylist(@RequestBody Musica musica, @PathVariable(name = "playlistId") String idPlayList){
        ResponseDTO responseDTO = playListService.adicionarMusicaNaPlayList(musica, idPlayList);
        URI uri = UriComponentsBuilder.fromPath("Playlist").buildAndExpand(responseDTO).toUri();
        return ResponseEntity.created(uri).body(responseDTO);
    }

    @DeleteMapping("/{playlistId}/musicas/{musicaId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> removerMusicaDaPLaylist(@PathVariable(name = "playlistId") String idPlayList, @PathVariable(name = "musicaId") String musicaId){
        return ResponseEntity.ok().body(playListService.removerMusicaNaPlayList(idPlayList, musicaId));
    }

}
