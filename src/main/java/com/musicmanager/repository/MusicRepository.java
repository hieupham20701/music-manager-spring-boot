package com.musicmanager.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.musicmanager.entity.Music;


public interface MusicRepository extends JpaRepository<Music, Long> {

	Page<Music> findByNameContaining(String nameSearch, Pageable pageable);
	List<Music> findByNameContaining(String nameSearch);
}
