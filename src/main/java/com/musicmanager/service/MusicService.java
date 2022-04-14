package com.musicmanager.service;

import java.io.IOException;
import java.util.stream.Stream;

import org.springframework.web.multipart.MultipartFile;

import com.musicmanager.entity.Music;

public interface MusicService {
	public Music save(MultipartFile file, String name, String generes) throws IOException;

	public Music getMusic(Long id);

	public Stream<Music> getAllMusic();

	public void deleteMusic(Long[] ids);

	public Music update(String name, String generes, Long id) throws IOException;

}
