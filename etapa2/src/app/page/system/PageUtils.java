package app.page.system;

import app.audio.Collections.Album;
import app.audio.Collections.AudioCollection;
import app.audio.Collections.Playlist;
import app.audio.Collections.Podcast;
import app.audio.Files.Song;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class PageUtils {
    public static List<Song> bestLikedSongs(List<Song> likedSongs) {
        likedSongs.sort((o1, o2) -> o2.getLikes() - o1.getLikes());

        return likedSongs.stream().limit(5).collect(Collectors.toList());
    }

    public static List<AudioCollection> mostLikedFollowedPlaylists(List<Playlist> followedPlaylists) {
        followedPlaylists.sort(new Comparator<AudioCollection>() {
            @Override
            public int compare(AudioCollection o1, AudioCollection o2) {
                return o2.countLikes() - o1.countLikes();
            }
        });
        return getPlaylists(followedPlaylists);
    }

    public static List<AudioCollection> getPlaylists(List<Playlist> followedPlaylists) {
        List<AudioCollection> result = new ArrayList<>();
        followedPlaylists.forEach(playlist -> result.add(playlist));
        return result;
    }

    public static List<AudioCollection> getAlbums(List<Album> albums) {
        List<AudioCollection> result = new ArrayList<>();
        albums.forEach(album -> result.add(album));
        return result;
    }

    public static List<AudioCollection> getPodcasts(List<Podcast> podcasts) {
        List<AudioCollection> result = new ArrayList<>();
        podcasts.forEach(podcast -> result.add(podcast));
        return result;
    }

    public static String collectionLikedContentPagePrint(List<AudioCollection> audioCollections) {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        for (AudioCollection audioCollection : audioCollections) {
            builder.append(audioCollection.likedContentPagePrint());
        }
        builder.append("]");
        return builder.toString();
    }

    public static String songsLikedContentPagePrint(List<Song> songs) {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        for (Song song : songs) {
            builder.append(song.likedContentPagePrint());
        }
        builder.append("]");
        return builder.toString();
    }
}
