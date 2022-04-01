package com.musicmanager.message;

public class ResponeMusic {
	
	private String name;
	private String url;
	private String description;
	private long size;
	public ResponeMusic(String name, String url, String description, long size) {
		super();
		this.name = name;
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



	@Override
	public String toString() {
		return "ResponeMusic [name=" + name + ", url=" + url + ", description=" + description + ", size=" + size + "]";
	}
	
	
}
