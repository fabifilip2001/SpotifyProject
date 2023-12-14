package app.audio.Collections;

import app.Admin;
import app.audio.Files.Song;
import lombok.Getter;

import java.util.ArrayList;

@Getter
public class AlbumOutput {
    private final String name;
    private final ArrayList<String> songs;

    public AlbumOutput(Album album) {
        this.name = album.getName();
        this.songs = new ArrayList<>();
        for (Song song : album.getSongs()) {
            songs.add(song.getName());
        }
    }
}
