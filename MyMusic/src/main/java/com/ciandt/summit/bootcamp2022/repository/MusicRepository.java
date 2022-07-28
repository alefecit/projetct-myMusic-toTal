package com.ciandt.summit.bootcamp2022.repository;

import com.ciandt.summit.bootcamp2022.entity.Music;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MusicRepository extends JpaRepository<Music, String> {

    @Query("SELECT m FROM Music m JOIN Artist a on a.id = m.artist WHERE a.name like '%'|| :filter ||'%' or m.name like '%'|| :filter ||'%' ORDER by a.name, m.name asc")
    List<Music> findSongsAndArtists(@Param("filter") String filter);

}
