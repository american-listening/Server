package com.americanlistening.core.youtube;

public class YouTubeMedia {

	private String location, type;
	
	public YouTubeMedia(String location, String type) {
		this.location = location;
		this.type = type;
	}
	
	public String getLocation() {
		return location;
	}
	
	public void setLocation(String location) {
		this.location = location;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
}
