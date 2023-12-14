package app;

import app.audio.Collections.*;
import app.audio.Files.Episode;
import app.audio.Files.Song;
import app.page.system.PageUtils;
import app.user.Artist;
import app.user.Host;
import app.user.User;
import app.utils.Enums;
import fileio.input.*;
import lombok.Getter;

import javax.sql.ConnectionPoolDataSource;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Admin {
    private static List<User> users = new ArrayList<>();
    private static List<Song> songs = new ArrayList<>();
    private static List<Podcast> podcasts = new ArrayList<>();
    private static List<Album> albums = new ArrayList<>();
    private static List<Artist> artists = new ArrayList<>();
    private static List<Host> hosts = new ArrayList<>();
    private static int timestamp = 0;

    public static void setUsers(List<UserInput> userInputList) {
        users = new ArrayList<>();
        for (UserInput userInput : userInputList) {
            users.add(new User(userInput.getUsername(), userInput.getAge(), userInput.getCity()));
        }
    }

    public static void setSongs(List<SongInput> songInputList) {
        songs = new ArrayList<>();
        for (SongInput songInput : songInputList) {
            songs.add(new Song(songInput.getName(), songInput.getDuration(), songInput.getAlbum(),
                    songInput.getTags(), songInput.getLyrics(), songInput.getGenre(),
                    songInput.getReleaseYear(), songInput.getArtist()));
        }
    }

    public static void setPodcasts(List<PodcastInput> podcastInputList) {
        podcasts = new ArrayList<>();
        for (PodcastInput podcastInput : podcastInputList) {
            List<Episode> episodes = new ArrayList<>();
            for (EpisodeInput episodeInput : podcastInput.getEpisodes()) {
                episodes.add(new Episode(episodeInput.getName(), episodeInput.getDuration(), episodeInput.getDescription()));
            }
            podcasts.add(new Podcast(podcastInput.getName(), podcastInput.getOwner(), episodes));
        }
    }

    public static List<Song> getSongs() {
        return new ArrayList<>(songs);
    }

    public static List<Podcast> getPodcasts() {
        return new ArrayList<>(podcasts);
    }

    public static List<User> getUsers() { return new ArrayList<>(users); }
    public static List<Artist> getArtists() { return new ArrayList<>(artists); }

    public static List<Host> getHosts() { return new ArrayList<>(hosts); }
    public static List<Album> getAlbums() { return new ArrayList<>(albums); }

    public static void addSong(Song song) { songs.add(song); }

    public static List<Playlist> getPlaylists() {
        List<Playlist> playlists = new ArrayList<>();
        for (User user : users) {
            playlists.addAll(user.getPlaylists());
        }
        return playlists;
    }

    public static User getUser(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public static Artist getArtist(String username) {
        for (Artist artist : artists) {
            if (artist.getUsername().equals(username)) {
                return artist;
            }
        }
        return null;
    }
    public static Host getHost(String username) {
        for (Host host : hosts) {
            if (host.getUsername().equals(username)) {
                return host;
            }
        }
        return null;
    }

    public static void removeUser(User user) {
        Iterator<User> iterator = users.iterator();
        while (iterator.hasNext()) {
            User user1 = iterator.next();
            if (user1.equals(user)) {
                iterator.remove();
            }
        }
    }

    public static void removeArtist(Artist artist) {
        Iterator<Artist> iterator = artists.iterator();
        while (iterator.hasNext()) {
            Artist artist1 = iterator.next();
            if (artist1.equals(artist)) {
                removeArtistsSongs(artist);
                removeArtistsAlbums(artist);
                updateUserOnRemove(artist);
                iterator.remove();
            }
        }
    }

    public static void removeArtistsSongs(Artist artist) {
        for (Album album : artist.getAlbums()) {
            for (Song song : album.getSongs()) {
                songs.remove(song);
            }
        }
    }

    public static void removeArtistsAlbums(Artist artist) {
        for (Album artistAlbum : artist.getAlbums()) {
            if (albums.contains(artistAlbum)) {
                albums.remove(artistAlbum);
            }
        }
    }
    public static Album getAlbum(String name) {
        for (Album album : albums) {
            if (album.getName().equals(name)) {
                return album;
            }
        }
        return  null;
    }

    public static Podcast getPodcast(String name) {
        for (Podcast podcast : podcasts) {
            if (podcast.getName().equals(name)) {
                return podcast;
            }
        }
        return null;
    }
    public static void removeAlbum(Album album) {
        albums.remove(album);
    }

    public static void removePodcast(Podcast podcast) {
        podcasts.remove(podcast);
    }

    public static void updateUserOnRemove(Artist artist) {
        for (User user : users) {
            for (Album album : artist.getAlbums()) {
                for (Song song : album.getSongs()) {
                    if (user.getLikedSongs().contains(song)) {
                        user.removeFromLikedSongs(song);
                    }
                }
            }
        }
    }
    public static List<String> getAllUsers() {
        ArrayList<User> allUsers = new ArrayList<>(Stream.of(users, artists, hosts)
                        .flatMap(Collection::stream)
                        .collect(Collectors.toList()));
        return allUsers.stream().map(User::getUsername).collect(Collectors.toList());
    }


    public static List<String> getOnlineUsers() {
        ArrayList<User> onlineUser = new ArrayList<>();
        for (User user : users) {
            if (user.getConnectionStatus().equals(Enums.UserConnectionStatus.ONLINE)) {
                onlineUser.add(user);
            }
        }
        return onlineUser.stream().map(User::getUsername).collect(Collectors.toList());
    }

    public static boolean checkIfUserExists(String username) {
        return getAllUsers().contains(username);
    }

    public static void updateTimestamp(int newTimestamp) {
        int elapsed = newTimestamp - timestamp;
        timestamp = newTimestamp;
        if (elapsed == 0) {
            return;
        }

        for (User user : users) {
            if (user.getConnectionStatus().equals(Enums.UserConnectionStatus.ONLINE)) {
                user.simulateTime(elapsed);
            }
        }
    }

    public static List<String> getTop5Songs() {
        List<Song> sortedSongs = new ArrayList<>(songs);
        sortedSongs.sort(Comparator.comparingInt(Song::getLikes).reversed());
        List<String> topSongs = new ArrayList<>();
        int count = 0;
        for (Song song : sortedSongs) {
            if (count >= 5) break;
            topSongs.add(song.getName());
            count++;
        }
        return topSongs;
    }

    public static List<String> getTop5Playlists() {
        List<Playlist> sortedPlaylists = new ArrayList<>(getPlaylists());
        sortedPlaylists.sort(Comparator.comparingInt(Playlist::getFollowers)
                .reversed()
                .thenComparing(Playlist::getTimestamp, Comparator.naturalOrder()));
        List<String> topPlaylists = new ArrayList<>();
        int count = 0;
        for (Playlist playlist : sortedPlaylists) {
            if (count >= 5) break;
            topPlaylists.add(playlist.getName());
            count++;
        }
        return topPlaylists;
    }
    public static void addAlbum(Album album) {
        albums.add(album);
    }

    public static void addPodcast(Podcast podcast) { podcasts.add(podcast); }
    public static String addUser(String username, String userType, Integer age, String city) {
        for (String user : getAllUsers()) {
            if (user.equals(username)) {
                return "The username %s is already taken.".formatted(username);
            }
        }

        switch (userType) {
            case "user" -> {
                User user = new User(username, age, city);
                Admin.users.add(user);
            }
            case "artist" -> {
                Artist artist = new Artist(username, age, city);
                Admin.artists.add(artist);
            }

            case "host" -> {
                Host host = new Host(username, age, city);
                Admin.hosts.add(host);
            }
        }

        return "The username %s has been added successfully.".formatted(username);
    }

    public static String deleteUser(String username) {
        if (!getAllUsers().contains(username))
            return "The username %s doesn't exist.".formatted(username);

        if (getUser(username) != null) {
            User user = getUser(username);
            for (User user1 : users) {
                user1.getFollowedPlaylists().removeIf(playlist -> playlist.getOwner().equals(username));
                user1.getPage().updateAudioCollection(PageUtils.getPlaylists(user1.getFollowedPlaylists()));
            }
            user.getFollowedPlaylists().forEach(playlist -> playlist.decreaseFollowers());
            removeUser(user);
        } else if (getArtist(username) != null) {
            Artist artist = getArtist(username);
            for (User user : users) {
                if (user.getPlayer().getSource() != null) {
                    if (user.getPlayer().getSource().getType().equals(Enums.PlayerSourceType.LIBRARY)
                && artist.getAlbums().stream().anyMatch(album -> album.getSongs().contains(user.getPlayer().getSource().getAudioFile()))
                || user.getPlayer().getSource().getType().equals(Enums.PlayerSourceType.ALBUM)){
                        return "%s can't be deleted.".formatted(username);
                    }
                }
            }
            removeArtist(artist);
        } else if (getHost(username) != null) {
            Host host = getHost(username);
            for (User user : users) {
                if (user.getPage().equals(host.getPage())) {
                    return "%s can't be deleted.".formatted(username);
                }
                if (user.getPlayer().getSource() != null) {
                    if (user.getPlayer().getSource().getType()
                            .equals(Enums.PlayerSourceType.PODCAST) && (host.getPodcasts().stream()
                            .anyMatch(podcast -> podcast.getEpisodes()
                                    .contains(user.getPlayer().getSource().getAudioFile())))){
                        return "%s can't be deleted.".formatted(username);
                    }
                }
            }
        }
        return "%s was successfully deleted.".formatted(username);
    }

    public static List<AlbumOutput> showAlbums(String username) {
        List<AlbumOutput> albumOutputs = new ArrayList<>();

        Artist artist = Admin.getArtist(username);
        for (Album album : artist.getAlbums()) {
            albumOutputs.add(new AlbumOutput(album));
        }

        return albumOutputs;
    }

    public static List<PodcastOutput> showPodcasts(String username) {
        List<PodcastOutput> podcastOutputs = new ArrayList<>();

        Host host = Admin.getHost(username);
        for (Podcast podcast : host.getPodcasts()) {
            podcastOutputs.add(new PodcastOutput(podcast));
        }

        return podcastOutputs;
    }

    public static List<String> getTop5Albums() {
        List<Album> sortedAlbums = new ArrayList<>(getAlbums());
        sortedAlbums.sort(Comparator.comparingInt(Album::countLikes).reversed());

        List<String> topAlbums = new ArrayList<>();
        sortedAlbums.forEach(album -> topAlbums.add(album.getName()));

        return topAlbums.stream().limit(5).collect(Collectors.toList());
    }

    public static void reset() {
        users = new ArrayList<>();
        songs = new ArrayList<>();
        podcasts = new ArrayList<>();
        artists = new ArrayList<>();
        hosts = new ArrayList<>();
        albums = new ArrayList<>();
        timestamp = 0;
    }
}
