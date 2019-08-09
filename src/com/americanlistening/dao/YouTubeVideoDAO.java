package com.americanlistening.dao;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.americanlistening.core.Instance;
import com.americanlistening.core.youtube.YouTubeManager;
import com.americanlistening.core.youtube.YouTubeVideo;
import com.google.gdata.util.ServiceException;

public class YouTubeVideoDAO implements DAO<YouTubeVideo, String> {

	@Override
	public Optional<YouTubeVideo> get(String key) {
		YouTubeManager ytManager = Instance.currentInstance.getYouTubeManager();
		try {
			Collection<YouTubeVideo> videos = ytManager.retrieveVideos(key, 1, true, 2000);
			YouTubeVideo video = null;
			for (YouTubeVideo v : videos) {
				video = v;
				break;
			}
			return Optional.ofNullable(video);
		} catch (IOException | ServiceException e) {
			return Optional.ofNullable(null);
		}
	}

	@Override
	public List<YouTubeVideo> getAll() {
		throw new UnsupportedOperationException("getAll");
	}

	@Override
	public void save(YouTubeVideo t) {
		throw new UnsupportedOperationException("getAll");
	}

	@Override
	public void update(YouTubeVideo t, Map<String, String> params) {
		throw new UnsupportedOperationException("update");
	}

	@Override
	public void delete(YouTubeVideo t) {
		throw new UnsupportedOperationException("delete");
	}

}
