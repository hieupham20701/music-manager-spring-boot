package com.musicmanager.service.impl;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.musicmanager.entity.Music;
import com.musicmanager.repository.MusicRepository;
import com.musicmanager.service.MusicService;

@Service
public class MusicServiceImpl implements MusicService {

	@Autowired
	private MusicRepository musicRepository;

	@Override
	public Music save(MultipartFile file, String name, String generes) throws IOException {
		Music music1 = new Music();
		String fileName = StringUtils.cleanPath(file.getOriginalFilename()); 
		music1.setName(name);
		music1.setDescription(file.getContentType());
		music1.setFile(file.getBytes());
		music1.setGeneres(generes);
		music1.setFileName(fileName);
		music1.setCreatedDate(new Timestamp(System.currentTimeMillis()));
		return musicRepository.save(music1);
	}

	@Override
	public Music getMusic(Long id) {
		return musicRepository.findOne(id);
	}

	@Override
	public Stream<Music> getAllMusic() {
		// TODO Auto-generated method stub
		return musicRepository.findAll().stream();
	}

	@Override
	public void deleteMusic(Long[] ids) {
		for (long item : ids) {
			musicRepository.delete(item);
		}
	}

	@Override
	public Music update( String name, String generes, Long id) throws IOException {

		Music music = musicRepository.findOne(id);
		if (!name.equals(""))
			music.setName(name);
		if (!generes.equals(""))
			music.setGeneres(generes);

		music.setModifiedDate(new Timestamp(System.currentTimeMillis()));
		return musicRepository.save(music);
	}

}
