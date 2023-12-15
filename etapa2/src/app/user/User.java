package app.user;

import app.Admin;
import app.audio.Collections.AudioCollection;
import app.audio.Collections.Playlist;
import app.audio.Collections.PlaylistOutput;
import app.audio.Files.AudioFile;
import app.audio.Files.Song;
import app.audio.LibraryEntry;
import app.page.system.Page;
import app.page.system.PageUtils;
import app.page.system.userPages.HomePage;
import app.page.system.userPages.LikedContentPage;
import app.page.system.userPages.UserPage;
import app.player.Player;
import app.player.PlayerStats;
import app.searchBar.Filters;
import app.searchBar.SearchBar;
import app.utils.Enums;
import fileio.input.CommandInput;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class User {
    @Getter
    private String username;
    @Getter
    private int age;
    @Getter
    private String city;
    @Getter
    private ArrayList<Playlist> playlists;
    @Getter
    private ArrayList<Song> likedSongs;
    @Getter
    private ArrayList<Playlist> followedPlaylists;
    @Getter
    private final Player player;
    @Getter
    private final SearchBar searchBar;
    private boolean lastSearched;
    @Getter
    private Enums.UserConnectionStatus connectionStatus;
    @Getter
    private Page page;
    @Getter
    private Enums.PageType pageType;

    public User(final String username, final int age, final String city) {
        this.username = username;
        this.age = age;
        this.city = city;
        playlists = new ArrayList<>();
        likedSongs = new ArrayList<>();
        followedPlaylists = new ArrayList<>();
        player = new Player();
        searchBar = new SearchBar(username);
        lastSearched = false;
        connectionStatus = Enums.UserConnectionStatus.ONLINE;
        page = new HomePage(this,
                new ArrayList<>(PageUtils.mostLikedFollowedPlaylists(followedPlaylists)),
                new ArrayList<>(PageUtils.bestLikedSongs(likedSongs)));
        pageType = Enums.PageType.HOMEPAGE;
    }

    public boolean matchesUsername(final String name) {
        return getUsername().toLowerCase().startsWith(name.toLowerCase());
    }

    public void setPage(final Page page) {
        this.page = page;
    }

    public ArrayList<String> search(final Filters filters, final String type) {
        ArrayList<String> results = new ArrayList<>();

        if (!isOffline()) {
            searchBar.clearSelection();
            player.stop();

            lastSearched = true;
            switch (type) {
                case ("artist"):
                case ("host"):
                    List<User> users = searchBar.searchUser(filters, type);
                    for (User user : users) {
                        results.add(user.getUsername());
                    }
                    break;

                default:
                    List<LibraryEntry> libraryEntries = searchBar.search(filters, type);

                    for (LibraryEntry libraryEntry : libraryEntries) {
                        results.add(libraryEntry.getName());
                    }
                    break;
            }
        }
        return results;
    }

    public String select(final int itemNumber) {
        if (!lastSearched)
            return "Please conduct a search before making a selection.";

        lastSearched = false;

        switch (searchBar.getLastSearchType()) {
            case ("artist"):
            case ("host"):
                User selectedUser = searchBar.selectUser(itemNumber);

                if (selectedUser == null)
                    return "The selected ID is too high.";

                page = selectedUser.getPage();
                if (searchBar.getLastSearchType().equals("artist")) {
                    pageType = Enums.PageType.ARTIST_PAGE;
                } else {
                    pageType = Enums.PageType.HOST_PAGE;
                }
                return "Successfully selected %s's page.".formatted(selectedUser.getUsername());
            default:
                LibraryEntry selected = searchBar.select(itemNumber);

                if (selected == null)
                    return "The selected ID is too high.";

                return "Successfully selected %s.".formatted(selected.getName());
        }
    }

    public String load() {
        if (searchBar.getLastSelected() == null)
            return "Please select a source before attempting to load.";

        if (!searchBar.getLastSearchType().equals("song") && ((AudioCollection)searchBar.getLastSelected()).getNumberOfTracks() == 0) {
            return "You can't load an empty audio collection!";
        }

        player.setSource(searchBar.getLastSelected(), searchBar.getLastSearchType());
        searchBar.clearSelection();

        player.pause();

        return "Playback loaded successfully.";
    }

    public String playPause() {
        if (player.getCurrentAudioFile() == null)
            return "Please load a source before attempting to pause or resume playback.";

        player.pause();

        if (player.getPaused())
            return "Playback paused successfully.";
        else
            return "Playback resumed successfully.";
    }

    public String repeat() {
        if (player.getCurrentAudioFile() == null)
            return "Please load a source before setting the repeat status.";

        Enums.RepeatMode repeatMode = player.repeat();
        String repeatStatus = "";

        switch(repeatMode) {
            case NO_REPEAT -> repeatStatus = "no repeat";
            case REPEAT_ONCE -> repeatStatus = "repeat once";
            case REPEAT_ALL -> repeatStatus = "repeat all";
            case REPEAT_INFINITE -> repeatStatus = "repeat infinite";
            case REPEAT_CURRENT_SONG -> repeatStatus = "repeat current song";
        }

        return "Repeat mode changed to %s.".formatted(repeatStatus);
    }

    public String shuffle(final Integer seed) {
        if (player.getCurrentAudioFile() == null)
            return "Please load a source before using the shuffle function.";

        if (!player.getType().equals("playlist") && !player.getType().equals("album"))
            return "The loaded source is not a playlist or an album.";

        player.shuffle(seed);

        if (player.getShuffle())
            return "Shuffle function activated successfully.";
        return "Shuffle function deactivated successfully.";
    }

    public String forward() {
        if (player.getCurrentAudioFile() == null)
            return "Please load a source before attempting to forward.";

        if (!player.getType().equals("podcast"))
            return "The loaded source is not a podcast.";

        player.skipNext();

        return "Skipped forward successfully.";
    }

    public String backward() {
        if (player.getCurrentAudioFile() == null)
            return "Please select a source before rewinding.";

        if (!player.getType().equals("podcast"))
            return "The loaded source is not a podcast.";

        player.skipPrev();

        return "Rewound successfully.";
    }

    public String like() {
        if (isOffline())
            return "%s is offline.".formatted(username);

        if (player.getCurrentAudioFile() == null)
            return "Please load a source before liking or unliking.";

        if (!player.getType().equals("song") && !player.getType().equals("playlist") && !player.getType().equals("album"))
            return "Loaded source is not a song.";

        Song song = (Song) player.getCurrentAudioFile();

        if (likedSongs.contains(song)) {
            likedSongs.remove(song);
            song.dislike();
            page.updateLikedSongs(likedSongs);

            return "Unlike registered successfully.";
        }

        likedSongs.add(song);
        song.like();
        page.updateLikedSongs(likedSongs);

        return "Like registered successfully.";
    }

    public String next() {
        if (player.getCurrentAudioFile() == null)
            return "Please load a source before skipping to the next track.";

        player.next();

        if (player.getCurrentAudioFile() == null)
            return "Please load a source before skipping to the next track.";

        return "Skipped to next track successfully. The current track is %s.".formatted(player.getCurrentAudioFile().getName());
    }

    public String prev() {
        if (player.getCurrentAudioFile() == null)
            return "Please load a source before returning to the previous track.";

        player.prev();

        return "Returned to previous track successfully. The current track is %s.".formatted(player.getCurrentAudioFile().getName());
    }

    public String createPlaylist(final String name, final int timestamp) {
        if (playlists.stream().anyMatch(playlist -> playlist.getName().equals(name)))
            return "A playlist with the same name already exists.";

        playlists.add(new Playlist(name, username, timestamp));

        return "Playlist created successfully.";
    }

    public String addRemoveInPlaylist(final int Id) {
        if (player.getCurrentAudioFile() == null)
            return "Please load a source before adding to or removing from the playlist.";

        if (player.getType().equals("podcast"))
            return "The loaded source is not a song.";

        if (Id > playlists.size())
            return "The specified playlist does not exist.";

        Playlist playlist = playlists.get(Id - 1);

        if (playlist.containsSong((Song)player.getCurrentAudioFile())) {
            playlist.removeSong((Song)player.getCurrentAudioFile());
            return "Successfully removed from playlist.";
        }

        playlist.addSong((Song)player.getCurrentAudioFile());
        return "Successfully added to playlist.";
    }

    public String switchPlaylistVisibility(final Integer playlistId) {
        if (playlistId > playlists.size())
            return "The specified playlist ID is too high.";

        Playlist playlist = playlists.get(playlistId - 1);
        playlist.switchVisibility();

        if (playlist.getVisibility() == Enums.Visibility.PUBLIC) {
            return "Visibility status updated successfully to public.";
        }

        return "Visibility status updated successfully to private.";
    }

    public ArrayList<PlaylistOutput> showPlaylists() {
        ArrayList<PlaylistOutput> playlistOutputs = new ArrayList<>();
        for (Playlist playlist : playlists) {
            playlistOutputs.add(new PlaylistOutput(playlist));
        }

        return playlistOutputs;
    }

    public String follow() {
        LibraryEntry selection = searchBar.getLastSelected();
        String type = searchBar.getLastSearchType();

        if (selection == null)
            return "Please select a source before following or unfollowing.";

        if (!type.equals("playlist"))
            return "The selected source is not a playlist.";

        Playlist playlist = (Playlist)selection;

        if (playlist.getOwner().equals(username))
            return "You cannot follow or unfollow your own playlist.";

        if (followedPlaylists.contains(playlist)) {
            followedPlaylists.remove(playlist);
            page.updateAudioCollection(PageUtils.getPlaylists(followedPlaylists));
            playlist.decreaseFollowers();

            return "Playlist unfollowed successfully.";
        }

        followedPlaylists.add(playlist);
        page.updateAudioCollection(PageUtils.getPlaylists(followedPlaylists));
        playlist.increaseFollowers();


        return "Playlist followed successfully.";
    }

    public PlayerStats getPlayerStats() {
        return player.getStats();
    }

    public ArrayList<String> showPreferredSongs() {
        ArrayList<String> results = new ArrayList<>();
        for (AudioFile audioFile : likedSongs) {
            results.add(audioFile.getName());
        }

        return results;
    }

    public String getPreferredGenre() {
        String[] genres = {"pop", "rock", "rap"};
        int[] counts = new int[genres.length];
        int mostLikedIndex = -1;
        int mostLikedCount = 0;

        for (Song song : likedSongs) {
            for (int i = 0; i < genres.length; i++) {
                if (song.getGenre().equals(genres[i])) {
                    counts[i]++;
                    if (counts[i] > mostLikedCount) {
                        mostLikedCount = counts[i];
                        mostLikedIndex = i;
                    }
                    break;
                }
            }
        }

        String preferredGenre = mostLikedIndex != -1 ? genres[mostLikedIndex] : "unknown";
        return "This user's preferred genre is %s.".formatted(preferredGenre);
    }

    public String switchConnectionStatus() {
        if (!Admin.checkIfUserExists(username))
            return "The username %s doesn't exist.".formatted(username);

        if (Admin.getUser(username) == null)
            return "%s is not a normal user.".formatted(username);

        connectionStatus = connectionStatus == Enums.UserConnectionStatus.OFFLINE
                ? Enums.UserConnectionStatus.ONLINE : Enums.UserConnectionStatus.OFFLINE;
        return "%s has changed status successfully.".formatted(username);
    }
    public void removeFromLikedSongs(final Song song) {
        getLikedSongs().remove(song);
        page.updateLikedSongs(getLikedSongs());
    }
    public String printCurrentPage() {
        if (isOffline())
            return "%s is offline.".formatted(username);

        return page.print();
    }
    public String changePage(final CommandInput commandInput) {
        String username = commandInput.getUsername();
        String pageName = commandInput.getNextPage();

        switch (pageName) {
            case ("Home") -> {
                HomePage page = new HomePage(this, PageUtils.getPlaylists(followedPlaylists),
                        getLikedSongs());
                this.setPage(page);
            }

            case "LikedContent" -> {
                LikedContentPage page = new LikedContentPage(this,
                        PageUtils.getPlaylists(this.followedPlaylists), this.likedSongs);
                this.setPage(page);
            }

            default -> "%s is trying to access a non-existent page.".formatted(username);
        }
        return "%s accessed %s successfully.".formatted(username, pageName);
    }
    public Page getPage() {
        return this.page;
    }
    public boolean isOffline() {
        return getConnectionStatus().equals(Enums.UserConnectionStatus.OFFLINE);
    }
    public void simulateTime(final int time) {
        player.simulatePlayer(time);
    }
}
