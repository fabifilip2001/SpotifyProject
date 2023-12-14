package app.audio.Collections;

import app.audio.Files.Episode;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class PodcastOutput {
    private final String name;
    private final List<String> episodes;

    public PodcastOutput(Podcast podcast) {
        this.episodes = new ArrayList<>();
        this.name = podcast.getName();
        for (Episode episode: podcast.getEpisodes()) {
            episodes.add(episode.getName());
        }
    }
}
