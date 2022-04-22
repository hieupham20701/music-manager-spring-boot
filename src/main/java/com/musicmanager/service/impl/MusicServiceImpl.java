package com.musicmanager.service.impl;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
	public Music update(String name, String generes, Long id) throws IOException {

		Music music = musicRepository.findOne(id);
		if (!name.equals(""))
			music.setName(name);
		if (!generes.equals(""))
			music.setGeneres(generes);

		music.setModifiedDate(new Timestamp(System.currentTimeMillis()));
		return musicRepository.save(music);
	}

	@Override
	public Map<String, Object> getAllMusicPage(int page, int size, String nameSearch) {
		List<Music> musics = new ArrayList<Music>();
//		PageRequest pageRequest = new PageRequest(page, size);
		Pageable paging = new PageRequest(page, size);

		Page<Music> pageTuts;
		if (nameSearch == null || nameSearch.equals("")) {
			pageTuts = musicRepository.findAll(paging);
		} else {
			pageTuts = musicRepository.findByNameContaining(nameSearch, paging);
		}

		musics = pageTuts.getContent();

		Map<String, Object> response = new HashMap<String, Object>();
		response.put("musics", musics);
		response.put("currentPage", pageTuts.getNumber());
		response.put("totalItems", pageTuts.getTotalElements());
		response.put("totalPages", pageTuts.getTotalPages());

		return response;
	}

	@Override
	public Stream<Music> findMusicByName(String name) {
		if (name == null || name.equals(""))
			return musicRepository.findAll().stream();
		else
			return musicRepository.findByNameContaining(name).stream();

	}

}
