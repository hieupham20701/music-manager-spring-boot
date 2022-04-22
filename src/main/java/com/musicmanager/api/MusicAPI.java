package com.musicmanager.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.validation.Valid;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.musicmanager.entity.Music;
import com.musicmanager.message.ResponeMessage;
import com.musicmanager.message.ResponeMusic;
import com.musicmanager.service.MusicService;
import com.musicmanager.validation.ValidFile;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@Validated
public class MusicAPI {

	@Autowired
	private MusicService musicService;

	@PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ResponeMessage> uploadMusic(@RequestParam("file") @Valid @ValidFile MultipartFile multipartFile,
			@RequestParam("name") @NotEmpty @Size(max = 45) String name, @RequestParam("generes") @NotEmpty @Size(max = 45) String generes) {
		String message = "";
		try {
			musicService.save(multipartFile, name, generes);
			message = "Upload Success";
			return ResponseEntity.status(HttpStatus.OK).body(new ResponeMessage(message));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			message = "Could not upload the Songs";
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponeMessage(message));
		}
	}

	@PutMapping(value = "/upload/{id}")
	public ResponseEntity<ResponeMessage> updateMusic(@RequestParam("name") @NotEmpty @Size(max = 45) String name,
			@RequestParam("generes") @NotEmpty @Size(max = 45) String generes, @PathVariable String id) {
		String message = "";
//		System.out.printf(name, multipartFile.toString(), generes);
		try {
			musicService.update(name, generes, Long.parseLong(id));
			message = "Update Success";
			return ResponseEntity.status(HttpStatus.OK).body(new ResponeMessage(message));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			message = "Could not update";
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponeMessage(message));
		}
	}

	@DeleteMapping(value = "/delete")
	public ResponseEntity<ResponeMessage> deleteMusic(@RequestBody Long[] id) {
		String message = "";
		try {
			musicService.deleteMusic(id);
			message = "Delete Success";
			return ResponseEntity.status(HttpStatus.OK).body(new ResponeMessage(message));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			message = "Could not Delete!";
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponeMessage(message));
		}
	}

//	String name, String url, String description, long size
	@GetMapping(value = "/files")
	public ResponseEntity<List<ResponeMusic>> getListFiles() {
		List<ResponeMusic> musics = musicService.getAllMusic().map(dbFile -> {
			String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/files/")
					.path(dbFile.getId() + "").toUriString();
			return new ResponeMusic(dbFile.getId(), dbFile.getName(), dbFile.getGeneres(), fileDownloadUri,
					dbFile.getDescription(), dbFile.getFile().length);
		}).collect(Collectors.toList());
		return ResponseEntity.status(HttpStatus.OK).body(musics);
	}

	@GetMapping(value = "/files/{id}")
	public ResponseEntity<byte[]> getFile(@PathVariable String id) {
		Music music = musicService.getMusic(Long.parseLong(id));
////		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/files/")
//				.path(music.getId() + "").toUriString();
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + music.getFileName() + "\"")
				.body(music.getFile());
	}

	@GetMapping(value = "/musicdetail/{id}")
	public ResponseEntity<ResponeMusic> getMusic(@PathVariable String id) {
		Music music = musicService.getMusic(Long.parseLong(id));
		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/files/")
				.path(music.getId() + "").toUriString();
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + music.getFileName() + "\"")
				.body(new ResponeMusic(music.getId(), music.getName(), music.getGeneres(), fileDownloadUri,
						music.getDescription(), music.getFile().length));
	}
	@GetMapping(value = "/files/pages")
	public ResponseEntity<Map<String, Object>> getAllMusicPaging(@RequestParam(required = false) String nameSearch,@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "4") int size){
		List<Music> musics = new ArrayList<Music>();
		Map<String, Object> mapMusic = musicService.getAllMusicPage(page, size, nameSearch);
		for(Map.Entry<String, Object> entry : mapMusic.entrySet()) {
			if(entry.getKey().equals("musics"))
				musics = (List<Music>) entry.getValue();
		}
		Stream<Music> musicStream = musics.stream();
		List<ResponeMusic> musicsRespone = musicStream.map(music -> {
			String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/files/")
					.path(music.getId() + "").toUriString();
			return new ResponeMusic(music.getId(), music.getName(), music.getGeneres(), fileDownloadUri,
					music.getDescription(), music.getFile().length);
		}).collect(Collectors.toList());
	
		mapMusic.remove("musics");
		mapMusic.put("musics", musicsRespone);
		return ResponseEntity.ok().body(mapMusic);
	}
	
	@GetMapping(value = "/files/name")
	public ResponseEntity<List<ResponeMusic>> getListMusicByName(@RequestParam(required = false) String nameSearch){
		List<ResponeMusic> musics = musicService.findMusicByName(nameSearch).map(dbFile -> {
			String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/files/")
					.path(dbFile.getId() + "").toUriString();
			return new ResponeMusic(dbFile.getId(), dbFile.getName(), dbFile.getGeneres(), fileDownloadUri,
					dbFile.getDescription(), dbFile.getFile().length);
		}).collect(Collectors.toList());
		return ResponseEntity.status(HttpStatus.OK).body(musics);
	}
}
