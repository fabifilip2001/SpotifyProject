package app.page.system;

import app.audio.Collections.Album;
import app.audio.Collections.AudioCollection;
import app.audio.Collections.Playlist;
import app.audio.Collections.Podcast;
import app.audio.Files.Song;
import app.utils.Utils;

import javax.swing.plaf.multi.MultiToolTipUI;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public final class PageUtils {
    private PageUtils() { }
    /**
     * function that returns the 5 most liked songs from an input song lists
     * */
    public static List<Song> bestLikedSongs(final List<Song> likedSongs) {
        likedSongs.sort((o1, o2) -> o2.getLikes() - o1.getLikes());

        return likedSongs.stream().limit(Utils.MAXIMUM_RESULT_SIZE).collect(Collectors.toList());
    }

    /**
     * function that returns the 5 most followed playlists from an input playlist lists
     * it returns an audio collection list, so it helps at casting
     * */
    public static List<AudioCollection> mostLikedFollowedPlaylists(
            final List<Playlist> followedPlaylists) {
        followedPlaylists.sort(new Comparator<AudioCollection>() {
            @Override
            public int compare(final AudioCollection o1, final AudioCollection o2) {
                return o2.countLikes() - o1.countLikes();
            }
        });
        return getPlaylists(followedPlaylists);
    }

    /**
     * function that casts a list of playlists to a list of audio collection
     * */
    public static List<AudioCollection> getPlaylists(final List<Playlist> followedPlaylists) {
        List<AudioCollection> result = new ArrayList<>();
        followedPlaylists.forEach(playlist -> result.add(playlist));
        return result;
    }

    /**
     * function that casts a list of albums to a list of audio collection
     * */
    public static List<AudioCollection> getAlbums(final List<Album> albums) {
        List<AudioCollection> result = new ArrayList<>();
        albums.forEach(album -> result.add(album));
        return result;
    }

    /**
     * function that casts a list of podcasts to a list of audio collection
     * */
    public static List<AudioCollection> getPodcasts(final List<Podcast> podcasts) {
        List<AudioCollection> result = new ArrayList<>();
        podcasts.forEach(podcast -> result.add(podcast));
        return result;
    }

    /**
     * function that formats the audio collection output for likedContentPage
     * */
    public static List<String> collectionLikedContentPagePrint(
            final List<AudioCollection> audioCollections) {
        List<String> result = new ArrayList<>();
        audioCollections.forEach(audioCollection -> result.add(audioCollection.getName()));
        return result;
    }

    /**
     * function that formats the songs output for likedContentPage
     * */
    public static List<String> songsLikedContentPagePrint(final List<Song> songs) {
        List<String> result = new ArrayList<>();
        songs.forEach(song -> result.add(song.getName()));
        return result;
    }

    /**
     * function that formats the audio collection output for homepage
     * */
    public static List<AudioCollection> homePagePlaylists(
            final List<AudioCollection> audioCollections) {
        List<AudioCollection> homePagePlaylists = new ArrayList<>(audioCollections);
        homePagePlaylists.sort(Comparator.comparingInt(AudioCollection::countLikes).reversed());

        return homePagePlaylists.stream().limit(Utils.MAXIMUM_RESULT_SIZE)
                .collect(Collectors.toList());

    }

    /**
     * function that formats the songs output for homepage
     * */
    public static List<Song> homePageSongs(final List<Song> songs) {
        List<Song> homePageSongs = new ArrayList<>(songs);
        homePageSongs.sort(Comparator.comparingInt(Song::getLikes).reversed());

        return homePageSongs.stream().limit(Utils.MAXIMUM_RESULT_SIZE)
                .collect(Collectors.toList());
    }
}
