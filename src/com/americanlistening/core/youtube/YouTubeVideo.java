package com.americanlistening.core.youtube;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

public class YouTubeVideo {

	private Collection<String> thumbnails;
	private Collection<YouTubeMedia> medias;
	private String webPlayerURL;
	private String embeddedWebPlayerURL;
	
	public YouTubeVideo(Collection<String> thumbnails, Collection<YouTubeMedia> medias, String webPlayerURL, String embeddedWebPlayerURL) {
		this.thumbnails = Collections.unmodifiableCollection(thumbnails);
		this.medias = Collections.unmodifiableCollection(medias);
		this.webPlayerURL = webPlayerURL;
		this.embeddedWebPlayerURL = embeddedWebPlayerURL;
	}
	
	public Collection<String> getThumbnails() {
		return thumbnails;
	}
	
	
	public Collection<YouTubeMedia> getMedias() {
		return medias;
	}
	
	public String getWebPlayerURL() {
		return webPlayerURL;
	}
	
	public String getEmbeddedWebPlayerURL() {
		return embeddedWebPlayerURL;
	}
	
	public String retrieveHttpLocation() {
		if (medias == null || medias.isEmpty())
			return null;
		for (YouTubeMedia media : medias) {
			String location = media.getLocation();
			if (location.startsWith("http"))
				return location;
		}
		return null;
	}
}
