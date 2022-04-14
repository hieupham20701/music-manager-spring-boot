package com.musicmanager.message;

public class ResponeMusic {

	private Long id;
	private String name;
	private String url;
	private String generes;
	private String description;
	private byte[] file;
	private long size;

	public ResponeMusic(Long id, String name, String generes, String url, String description, long size) {
		super();
		this.id = id;
		this.name = name;
		this.generes = generes;
		this.url = url;
		this.description = description;
		this.size = size;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public String getGeneres() {
		return generes;
	}

	public void setGeneres(String generes) {
		this.generes = generes;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public byte[] getFile() {
		return file;
	}

	public void setFile(byte[] file) {
		this.file = file;
	}

	@Override
	public String toString() {
		return "ResponeMusic [name=" + name + ", url=" + url + ", description=" + description + ", size=" + size + "]";
	}

}
