package com.americanlistening.core.youtube;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import com.google.gdata.client.youtube.YouTubeQuery;
import com.google.gdata.client.youtube.YouTubeService;
import com.google.gdata.data.media.mediarss.MediaThumbnail;
import com.google.gdata.data.youtube.VideoEntry;
import com.google.gdata.data.youtube.VideoFeed;
import com.google.gdata.data.youtube.YouTubeMediaContent;
import com.google.gdata.data.youtube.YouTubeMediaGroup;
import com.google.gdata.util.ServiceException;

public class YouTubeManager {

	private static final String YOUTUBE_URL = "http://gdata.youtube.com/feeds/api/videos";
    private static final String YOUTUBE_EMBEDDED_URL = "http://www.youtube.com/v/";
    
    private String clientID;
    
    public YouTubeManager(String clientID) {
    	this.clientID = clientID;
    }
    
    public Collection<YouTubeVideo> retrieveVideos(String textQuery, int maxResults, boolean filter, int timeout) throws IOException, ServiceException {
    	YouTubeService service = new YouTubeService(clientID);
    	service.setConnectTimeout(timeout);
    	YouTubeQuery query = new YouTubeQuery(new URL(YOUTUBE_URL));
    	
    	query.setOrderBy(YouTubeQuery.OrderBy.VIEW_COUNT);
    	query.setFullTextQuery(textQuery);
    	query.setSafeSearch(YouTubeQuery.SafeSearch.NONE);
    	query.setMaxResults(maxResults);
    	
    	VideoFeed videoFeed = service.query(query, VideoFeed.class);
    	Collection<VideoEntry> videos = videoFeed.getEntries();
    	
    	return convertVideos(videos);
    }
    
    private Collection<YouTubeVideo> convertVideos(Collection<VideoEntry> videos){
    	Collection<YouTubeVideo> youtubeVideosList = new LinkedList<>();
    	
    	for (VideoEntry videoEntry : videos) {

    		YouTubeMediaGroup mediaGroup = videoEntry.getMediaGroup();
    		String webPlayerURL = mediaGroup.getPlayer().getUrl();
    		
    		String query = "?v=";
    		int index = webPlayerURL.indexOf(query);
    		
    		String embeddedWebPlayerURL = webPlayerURL.substring(index + query.length());
    		embeddedWebPlayerURL = YOUTUBE_EMBEDDED_URL + embeddedWebPlayerURL;
    		
    		List<String> thumbnails = new LinkedList<>();
    		for (MediaThumbnail mediaThumbnail : mediaGroup.getThumbnails()) {
    			thumbnails.add(mediaThumbnail.getUrl());
    		}
    		
    		List<YouTubeMedia> medias = new LinkedList<>();
    		for (YouTubeMediaContent mediaContent : mediaGroup.getYouTubeContents()) {
    			medias.add(new YouTubeMedia(mediaContent.getUrl(), mediaContent.getType()));
    		}
    		
    		YouTubeVideo ytv = new YouTubeVideo(thumbnails, medias, webPlayerURL, embeddedWebPlayerURL);
    		
    		youtubeVideosList.add(ytv);
    	}
    	return youtubeVideosList;
    }
}
