package com.musicmanager.convert;

import org.springframework.stereotype.Component;

import com.musicmanager.dto.MusicDTO;
import com.musicmanager.entity.Music;

@Component
public class MusicConverter {

	public Music toEntity(MusicDTO musicDTO) {

		Music music = new Music();
		music.setName(musicDTO.getName());
		music.setModifiedDate(musicDTO.getModifiedDate());
		music.setFile(musicDTO.getFile());
		music.setDescription(musicDTO.getDescription());
		music.setCreatedDate(musicDTO.getCreatedDate());
		return music;

	}

	public MusicDTO toDTO(Music music) {
		MusicDTO musicDTO = new MusicDTO();
		if (music.getId() != null) {
			musicDTO.setId(music.getId());
		}

		musicDTO.setName(music.getName());
		musicDTO.setModifiedDate(music.getModifiedDate());
		musicDTO.setFile(music.getFile());
		musicDTO.setDescription(music.getDescription());
		musicDTO.setCreatedDate(music.getCreatedDate());
		return musicDTO;
	}

	public Music toEntity(MusicDTO musicDTO, Music music) {
		music.setName(musicDTO.getName());
		music.setModifiedDate(musicDTO.getModifiedDate());
		music.setFile(musicDTO.getFile());
		music.setDescription(musicDTO.getDescription());
		music.setCreatedDate(musicDTO.getCreatedDate());
		return music;
	}
}
