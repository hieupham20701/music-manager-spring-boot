package com.musicmanager.api;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

@CrossOrigin
@RestController
public class MusicAPI {

	@Autowired
	private MusicService musicService;

	@PostMapping(value = "/upload")
	public ResponseEntity<ResponeMessage> uploadMusic(@RequestParam("file") MultipartFile multipartFile,
			@RequestParam("name") String name, @RequestParam("generes") String generes) {
		String message = "";
		try {
			musicService.save(multipartFile, name, generes);
			message = "Upload Success";
			return ResponseEntity.status(HttpStatus.OK).body(new ResponeMessage(message));
		} catch (Exception e) {
			// TODO: handle exception
			message = "Could not upload the file";
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponeMessage(message));
		}
	}

	@PutMapping(value = "/upload/{id}")
	public ResponseEntity<ResponeMessage> updateMusic(@RequestParam("file") MultipartFile multipartFile,
			@RequestParam("name") String name, @RequestParam("generes") String generes, @PathVariable String id) {
		String message = "";
//		System.out.printf(name, multipartFile.toString(), generes);
		try {
			musicService.update(multipartFile, name, generes, Long.parseLong(id));
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
	public ResponseEntity<ResponeMessage> deleteMusic(@RequestBody long[] id) {
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
			return new ResponeMusic(dbFile.getName(),dbFile.getGeneres(),fileDownloadUri, dbFile.getDescription(),
					dbFile.getFile().length);
		}).collect(Collectors.toList());
		return ResponseEntity.status(HttpStatus.OK).body(musics);
	}

	@GetMapping(value = "/files/{id}")
	public ResponseEntity<byte[]> getFile(@PathVariable String id) {
		Music music = musicService.getMusic(Long.parseLong(id));
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + music.getFileName() + "\"")
				.body(music.getFile());
	}
}
