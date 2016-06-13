package com.xiuman.xinjiankang.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class AlbumEntity implements Serializable{
	//图片id
	private String id;
	private String coverUri;
	private String name;
	private ArrayList<ImageEntity> images;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCoverUri() {
		return coverUri;
	}
	public void setCoverUri(String coverUri) {
		this.coverUri = coverUri;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<ImageEntity> getImages() {
		return images;
	}

	public void setImages(ArrayList<ImageEntity> images) {
		this.images = images;
	}

}
