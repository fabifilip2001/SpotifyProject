package app.audio.Files;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public final class Song extends AudioFile {
    private final String album;
    private final ArrayList<String> tags;
    private final String lyrics;
    private final String genre;
    private final Integer releaseYear;
    private final String artist;
    private Integer likes;

    public Song(final String name, final Integer duration, final String album,
                final ArrayList<String> tags, final String lyrics, final String genre,
                final Integer releaseYear, final String artist) {
        super(name, duration);
        this.album = album;
        this.tags = tags;
        this.lyrics = lyrics;
        this.genre = genre;
        this.releaseYear = releaseYear;
        this.artist = artist;
        this.likes = 0;
    }

    @Override
    public boolean matchesAlbum(final String albumInput) {
        return this.getAlbum().equalsIgnoreCase(albumInput);
    }

    @Override
    public boolean matchesTags(final ArrayList<String> tagsInput) {
        List<String> songTags = new ArrayList<>();
        for (String tag : this.getTags()) {
            songTags.add(tag.toLowerCase());
        }

        for (String tag : tagsInput) {
            if (!songTags.contains(tag.toLowerCase())) {
                return false;
            }
        }
        return true;
    }
    @Override
    public boolean matchesLyrics(final String lyricsInput) {
        return this.getLyrics().toLowerCase().contains(lyricsInput.toLowerCase());
    }

    @Override
    public boolean matchesGenre(final String genreInput) {
        return this.getGenre().equalsIgnoreCase(genreInput);
    }

    @Override
    public boolean matchesArtist(final String artistInput) {
        return this.getArtist().equalsIgnoreCase(artistInput);
    }

    @Override
    public boolean matchesReleaseYear(final String releaseYearInput) {
        return filterByYear(this.getReleaseYear(), releaseYearInput);
    }

    private static boolean filterByYear(final int year, final String query) {
        if (query.startsWith("<")) {
            return year < Integer.parseInt(query.substring(1));
        } else if (query.startsWith(">")) {
            return year > Integer.parseInt(query.substring(1));
        } else {
            return year == Integer.parseInt(query);
        }
    }

    /**
     * function that increases likes count of a song
     * */
    public void like() {
        likes++;
    }

    /**
     * function that decreases likes count of a song
     * */
    public void dislike() {
        likes--;
    }

    /**
     * function that return print format from likedContentPage
     * */
    public String likedContentPagePrint() {
        return getName();
    }
    @Override
    public String toString() {
        return getName()
                +
                " - "
                +
                getArtist();
    }
}
