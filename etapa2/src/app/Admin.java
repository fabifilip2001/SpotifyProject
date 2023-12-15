package app;

import app.audio.Collections.Podcast;
import app.audio.Collections.Playlist;
import app.audio.Collections.Album;
import app.audio.Collections.AlbumOutput;
import app.audio.Collections.PodcastOutput;
import app.audio.Files.Episode;
import app.audio.Files.Song;
import app.page.system.PageUtils;
import app.user.Artist;
import app.user.Host;
import app.user.User;
import app.utils.Enums;
import app.utils.Utils;
import fileio.input.EpisodeInput;
import fileio.input.PodcastInput;
import fileio.input.SongInput;
import fileio.input.UserInput;


import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Comparator;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Admin {
    private static Admin adminInstance;
    private List<User> users = new ArrayList<>();
    private List<Song> songs = new ArrayList<>();
    private List<Podcast> podcasts = new ArrayList<>();
    private List<Album> albums = new ArrayList<>();
    private List<Artist> artists = new ArrayList<>();
    private List<Host> hosts = new ArrayList<>();
    private int timestamp = 0;

    private Admin() { }

    public static Admin getInstance() {
        if (adminInstance == null) {
            adminInstance = new Admin();
        }

        return adminInstance;
    }
    /**
     * method that sets users from database with input users
     * */
    public void setUsers(final List<UserInput> userInputList) {
        users = new ArrayList<>();
        for (UserInput userInput : userInputList) {
            users.add(new User(userInput.getUsername(), userInput.getAge(), userInput.getCity()));
        }
    }

    /**
     * method that sets songs from database with input songs
     * */
    public void setSongs(final List<SongInput> songInputList) {
        songs = new ArrayList<>();
        for (SongInput songInput : songInputList) {
            songs.add(new Song(songInput.getName(), songInput.getDuration(), songInput.getAlbum(),
                    songInput.getTags(), songInput.getLyrics(), songInput.getGenre(),
                    songInput.getReleaseYear(), songInput.getArtist()));
        }
    }

    /**
     * method that sets podcasts from database with input podcasts
     * */
    public void setPodcasts(final List<PodcastInput> podcastInputList) {
        podcasts = new ArrayList<>();
        for (PodcastInput podcastInput : podcastInputList) {
            List<Episode> episodes = new ArrayList<>();
            for (EpisodeInput episodeInput : podcastInput.getEpisodes()) {
                episodes.add(new Episode(episodeInput.getName(), episodeInput.getDuration(),
                        episodeInput.getDescription()));
            }
            podcasts.add(new Podcast(podcastInput.getName(), podcastInput.getOwner(), episodes));
        }
    }

    public List<Song> getSongs() {
        return new ArrayList<>(songs);
    }

    public List<Podcast> getPodcasts() {
        return new ArrayList<>(podcasts);
    }

    public List<User> getUsers() {
        return new ArrayList<>(users);
    }
    public List<Artist> getArtists() {
        return new ArrayList<>(artists);
    }

    public List<Host> getHosts() {
        return new ArrayList<>(hosts);
    }
    public List<Album> getAlbums() {
        return new ArrayList<>(albums);
    }

    /**
     * method that adds a songs in database's songs
     * */
    public static void addSong(final Song song) {
        adminInstance.songs.add(song);
    }

    /**
     * method that returns the playlists from database
     * */
    public static List<Playlist> getPlaylists() {
        List<Playlist> playlists = new ArrayList<>();
        for (User user : adminInstance.users) {
            playlists.addAll(user.getPlaylists());
        }
        return playlists;
    }

    /**
     * method that returns an user from database with a given username or null else
     * */
    public User getUser(final String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    /**
     * method that returns an artist from database with a given username or null else
     * */
    public Artist getArtist(final String username) {
        for (Artist artist : artists) {
            if (artist.getUsername().equals(username)) {
                return artist;
            }
        }
        return null;
    }

    /**
     * method that returns a host from database with a given username or null else
     * */
    public Host getHost(final String username) {
        for (Host host : hosts) {
            if (host.getUsername().equals(username)) {
                return host;
            }
        }
        return null;
    }

    /**
     * function that removes a given user from database's users
     * */
    public void removeUser(final User user) {
        Iterator<User> iterator = users.iterator();
        while (iterator.hasNext()) {
            User user1 = iterator.next();
            if (user1.equals(user)) {
                iterator.remove();
            }
        }
    }

    /**
     * function that removes a given artist from database's artists
     * */
    public void removeArtist(final Artist artist) {
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

    /**
     * function that removes all the songs contained in a given artist's albums
     * */
    public void removeArtistsSongs(final Artist artist) {
        for (Album album : artist.getAlbums()) {
            for (Song song : album.getSongs()) {
                songs.remove(song);
            }
        }
    }

    /**
     * function that removes a given artist's albums
     * */
    public void removeArtistsAlbums(final Artist artist) {
        for (Album artistAlbum : artist.getAlbums()) {
            if (albums.contains(artistAlbum)) {
                albums.remove(artistAlbum);
            }
        }
    }

    /**
     * method that returns an album from database with a given name or null else
     * */
    public Album getAlbum(final String name) {
        for (Album album : albums) {
            if (album.getName().equals(name)) {
                return album;
            }
        }
        return  null;
    }

    /**
     * method that returns a podcast from database with a given name or null else
     * */
    public Podcast getPodcast(final String name) {
        for (Podcast podcast : podcasts) {
            if (podcast.getName().equals(name)) {
                return podcast;
            }
        }
        return null;
    }

    /**
     * method that removes an album with a given name from database's albums
     * */
    public void removeAlbum(final Album album) {
        albums.remove(album);
    }

    /**
     * method that removes a podcast with a given name from database's podcasts
     * */
    public void removePodcast(final Podcast podcast) {
        podcasts.remove(podcast);
    }

    /**
     * function that removes an user's liked songs, when an artist is removed
     * */
    public void updateUserOnRemove(final Artist artist) {
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

    /**
     * function that returns all the users from database
     * */
    public List<String> getAllUsers() {
        ArrayList<User> allUsers = new ArrayList<>(Stream.of(users, artists, hosts)
                        .flatMap(Collection::stream)
                        .collect(Collectors.toList()));
        return allUsers.stream().map(User::getUsername).collect(Collectors.toList());
    }

    /**
     * function that returns the online normal users
     * */
    public List<String> getOnlineUsers() {
        ArrayList<User> onlineUser = new ArrayList<>();
        for (User user : users) {
            if (user.getConnectionStatus().equals(Enums.UserConnectionStatus.ONLINE)) {
                onlineUser.add(user);
            }
        }
        return onlineUser.stream().map(User::getUsername).collect(Collectors.toList());
    }

    /**
     * function that verify an user with a given username exists in database
     * */
    public boolean checkIfUserExists(final String username) {
        return getAllUsers().contains(username);
    }

    /**
     * function that updates timestamp
     * */
    public void updateTimestamp(final int newTimestamp) {
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

    /**
     * function that returns the first most 5 liked songs from the app
     * */
    public List<String> getTop5Songs() {
        List<Song> sortedSongs = new ArrayList<>(songs);
        sortedSongs.sort(Comparator.comparingInt(Song::getLikes).reversed());
        List<String> topSongs = new ArrayList<>();
        int count = 0;
        for (Song song : sortedSongs) {
            if (count >= Utils.MAXIMUM_RESULT_SIZE) {
                break;
            }
            topSongs.add(song.getName());
            count++;
        }
        return topSongs;
    }

    /**
     * function that returns the most 5 followed playlists from the app
     * */
    public List<String> getTop5Playlists() {
        List<Playlist> sortedPlaylists = new ArrayList<>(getPlaylists());
        sortedPlaylists.sort(Comparator.comparingInt(Playlist::getFollowers)
                .reversed()
                .thenComparing(Playlist::getTimestamp, Comparator.naturalOrder()));
        List<String> topPlaylists = new ArrayList<>();
        int count = 0;
        for (Playlist playlist : sortedPlaylists) {
            if (count >= Utils.MAXIMUM_RESULT_SIZE) {
                break;
            }
            topPlaylists.add(playlist.getName());
            count++;
        }
        return topPlaylists;
    }

    /**
     * function that adds a new album in the database
     * */
    public void addAlbum(final Album album) {
        albums.add(album);
    }

    /**
     * function that adds a new podcast in the database
     * */
    public void addPodcast(final Podcast podcast) {
        podcasts.add(podcast);
    }

    /**
     * function that adds a new normal user in database
     * */
    public String addUser(final String username, final String userType, final Integer age,
                                 final String city) {
        for (String user : getAllUsers()) {
            if (user.equals(username)) {
                return "The username %s is already taken.".formatted(username);
            }
        }

        switch (userType) {
            case "user" -> {
                User user = new User(username, age, city);
                adminInstance.users.add(user);
            }
            case "artist" -> {
                Artist artist = new Artist(username, age, city);
                adminInstance.artists.add(artist);
            }

            case "host" -> {
                Host host = new Host(username, age, city);
                adminInstance.hosts.add(host);
            }
            default -> System.out.println("Invalid user type.");
        }

        return "The username %s has been added successfully.".formatted(username);
    }

    /**
     * function that removes a normal user and its connections from app
     * */
    public String deleteUser(final String username) {
        if (!getAllUsers().contains(username)) {
            return "The username %s doesn't exist.".formatted(username);
        }

        if (getUser(username) != null) {
            User user = getUser(username);

            for (User user1 : users) {
                if (user1.getPlayer().getSource() != null
                        && user1.getPlayer().getSource().getAudioCollection() != null
                        && user.getPlaylists().stream().anyMatch(playlist -> playlist
                        .equals(user1.getPlayer().getSource().getAudioCollection()))) {
                    return "%s can't be deleted.".formatted(username);
                }
                user1.getFollowedPlaylists().removeIf(playlist -> playlist.getOwner()
                        .equals(username));
                user1.getPage().updateAudioCollection(PageUtils.getPlaylists(user1
                        .getFollowedPlaylists()));
            }
            user.getFollowedPlaylists().forEach(playlist -> playlist.decreaseFollowers());
            removeUser(user);
        } else if (getArtist(username) != null) {
            Artist artist = getArtist(username);
            for (User user : users) {
                if (user.getPage().equals(artist.getPage())) {
                    return "%s can't be deleted.".formatted(username);
                }

                if (user.getSearchBar() != null) {
                    if ((user.getSearchBar().getUserResults() != null
                            && user.getSearchBar().getUserResults().contains(artist))) {
                        System.out.println("hahahahaha");
                        return "%s can't be deleted.".formatted(username);
                    }
                }
                if (user.getPlayer().getSource() != null) {
                    if ((user.getPlayer().getSource().getType()
                            .equals(Enums.PlayerSourceType.LIBRARY)
                            || user.getPlayer().getSource().getType()
                            .equals(Enums.PlayerSourceType.ALBUM))
                && artist.getAlbums().stream().anyMatch(album -> album.getSongs()
                            .contains(user.getPlayer().getSource().getAudioFile()))) {
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
                                    .contains(user.getPlayer().getSource().getAudioFile())))) {
                        return "%s can't be deleted.".formatted(username);
                    }
                }
            }
        }
        return "%s was successfully deleted.".formatted(username);
    }

    /**
     * function that returns an artist's albums in a specific output format
     * */
    public List<AlbumOutput> showAlbums(final String username) {
        List<AlbumOutput> albumOutputs = new ArrayList<>();

        Artist artist = adminInstance.getArtist(username);
        for (Album album : artist.getAlbums()) {
            albumOutputs.add(new AlbumOutput(album));
        }

        return albumOutputs;
    }

    /**
     * function that returns a host's podcasts in a specific output format
     * */
    public List<PodcastOutput> showPodcasts(final String username) {
        List<PodcastOutput> podcastOutputs = new ArrayList<>();

        Host host = adminInstance.getHost(username);
        for (Podcast podcast : host.getPodcasts()) {
            podcastOutputs.add(new PodcastOutput(podcast));
        }

        return podcastOutputs;
    }

    /**
     * functions that returns most 5 liked albums in the app
     * */
    public List<String> getTop5Albums() {
        List<Album> sortedAlbums = new ArrayList<>(getAlbums());
        sortedAlbums.sort(Comparator.comparingInt(Album::countLikes)
                .reversed()
                .thenComparing(Album::getName));

        List<String> topAlbums = new ArrayList<>();
        sortedAlbums.forEach(album -> topAlbums.add(album.getName()));
        return topAlbums.stream().limit(Utils.MAXIMUM_RESULT_SIZE).collect(Collectors.toList());
    }

    /**
     * function that retruns most 5 appreciate artists after their albums songs likes in the app
     * */
    public List<String> getTop5Artists() {
        List<Artist> sortedArtists = new ArrayList<>(getArtists());
        sortedArtists.sort(Comparator.comparingInt(Artist::countLikes).reversed());

        List<String> mostAppreciateArtists = new ArrayList<>();
        sortedArtists.forEach(artist -> mostAppreciateArtists.add(artist.getUsername()));

        return mostAppreciateArtists.stream()
                .limit(Utils.MAXIMUM_RESULT_SIZE).collect(Collectors.toList());
    }

    /**
     * function that reset all database's entities after each test
     * */
    public void reset() {
        users = new ArrayList<>();
        songs = new ArrayList<>();
        podcasts = new ArrayList<>();
        artists = new ArrayList<>();
        hosts = new ArrayList<>();
        albums = new ArrayList<>();
        timestamp = 0;
    }
}
