package app.user;

import app.Admin;
import app.audio.Collections.Album;
import app.audio.Files.Song;
import app.page.system.PageUtils;
import app.page.system.artistPages.ArtistPage;
import app.page.system.artistPages.utils.Event;
import app.page.system.artistPages.utils.Merch;
import app.player.Player;
import app.utils.Utils;
import fileio.input.CommandInput;
import fileio.input.SongInput;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public final class Artist extends User {
    @Getter
    private final List<Album> albums;
    @Getter
    private final List<Event> events;
    @Getter
    private final List<Merch> merches;
    private final ArtistPage page;


    public Artist(final String username, final int age, final String city) {
        super(username, age, city);
        this.albums = new ArrayList<>();
        this.events = new ArrayList<>();
        this.merches = new ArrayList<>();
        page = new ArtistPage(this, PageUtils.getAlbums(albums), events, merches);
    }

    @Override
    public ArtistPage getPage() {
        return this.page;
    }

    /**
     * function that implements the logic for the 'addAlbum' function
     * */
    public String addAlbum(final CommandInput commandInput) {
        String username = commandInput.getUsername();

        if (albums.stream().anyMatch(album -> album.getName().equals(commandInput.getName()))) {
            return "%s has another album with the same name.".formatted(username);
        }

        Set<String> songInputSet = new HashSet<>(commandInput.getSongs().stream()
                        .map(SongInput::getName).collect(Collectors.toList()));

        if (songInputSet.size() < commandInput.getSongs().size()) {
            return "%s has the same song at least twice in this album.".formatted(username);
        }

        List<Song> songs = new ArrayList<>();
        for (SongInput song : commandInput.getSongs()) {
            Song newSong = new Song(song.getName(), song.getDuration(), song.getAlbum(),
                    song.getTags(), song.getLyrics(), song.getGenre(), song.getReleaseYear(),
                    song.getArtist());
            songs.add(newSong);

            if (!Admin.getInstance().getSongs().contains(newSong)) {
                Admin.addSong(newSong);
            }
        }
        Album album = new Album(commandInput.getName(), username, commandInput.getReleaseYear(),
                commandInput.getDescription(), songs,  commandInput.getTimestamp());
        Admin.getInstance().addAlbum(album);
        albums.add(album);
        page.updateAudioCollection(PageUtils.getAlbums(albums));
        return "%s has added new album successfully.".formatted(username);
    }

    /**
     * function that implements the logic for the 'removeAlbum' function
     * */
    public String removeAlbum(final CommandInput commandInput) {
        String username = commandInput.getUsername();

        if (!albums.stream().anyMatch(album -> album.getName().equals(commandInput.getName()))) {
            return "%s doesn't have an album with the given name.".formatted(username);
        }

        for (User user : Admin.getInstance().getUsers()) {
            Player player = user.getPlayer();
            if (user.getPage().equals(page)) {
                return "%s can't delete this album.".formatted(username);
            }
            if (player.getSource() != null) {
                if (player.getSource().getAudioCollection().getName()
                        .equals(commandInput.getName()) || albums.stream()
                        .anyMatch(album -> album.getSongs()
                        .contains(player.getSource().getAudioFile()))) {
                    return "%s can't delete this album.".formatted(username);
                }
            }
        }
        Album album = Admin.getInstance().getAlbum(commandInput.getName());
        albums.remove(album);
        page.updateAudioCollection(PageUtils.getAlbums(albums));
        Admin.getInstance().removeAlbum(album);

        return "%s deleted the album successfully.".formatted(username);
    }

    /**
     * function that implements the logic for the 'addMerch' function
     * */
    public String addMerch(final CommandInput commandInput) {
        String username = commandInput.getUsername();
        if (merches.stream().anyMatch(merch -> merch.getName().equals(commandInput.getName()))) {
            return "%s has merchandise with the same name.".formatted(username);
        }

        if (commandInput.getPrice() < 0) {
            return "Price for merchandise can not be negative.";
        }

        Merch merch = new Merch(commandInput.getName(), commandInput.getDescription(),
                commandInput.getPrice());
        merches.add(merch);
        page.updateMerches(merches);

        return "%s has added new merchandise successfully.".formatted(username);
    }

    /**
     * function that implements the logic for the 'addEvent' function
     * */
    public String addEvent(final CommandInput commandInput) {
        String username = commandInput.getUsername();

        if (events.stream().anyMatch(event -> event.getName().equals(commandInput.getName()))) {
            return "%s has another event with the same name.".formatted(username);
        }

        if (Utils.validateData(commandInput.getDate()).equals("error")) {
            return "Event for %s does not have a valid date.".formatted(username);
        }

        Event event = new Event(commandInput.getName(), commandInput.getDescription(),
                commandInput.getDate());
        events.add(event);
        page.updateEvents(events);

        return "%s has added new event successfully.".formatted(username);
    }

    /**
     * function that implements the logic for the 'removeEvent' function
     * */
    public String removeEvent(final CommandInput commandInput) {
        String username = commandInput.getUsername();

        if (!events.stream().anyMatch(event -> event.getName().equals(commandInput.getName()))) {
            return "%s doesn't have an event withthe given name.".formatted(username);
        }


        events.removeIf(event -> event.getName().equals(commandInput.getName()));
        page.updateEvents(events);

        return "%s deleted the event successfully.".formatted(username);
    }

    /**
     * function that returns total likes that has an artist (sum of every song from every album)
     * */
    public int countLikes() {
        int count = 0;
        for (Album album : albums) {
            count += album.countLikes();
        }

        return count;
    }
}
