package app.searchBar;


import app.Admin;
import app.audio.LibraryEntry;
import app.user.User;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

import static app.searchBar.FilterUtils.*;
import static app.searchBar.FilterUtils.filterByFollowers;

public final class SearchBar {
    private final Admin adminInstance = Admin.getInstance();
    private List<LibraryEntry> results;
    @Getter
    private List<User> userResults;
    private final String user;
    private static final Integer MAX_RESULTS = 5;
    @Getter
    private String lastSearchType;

    @Getter
    private LibraryEntry lastSelected;

    @Getter
    private User lastUserSelected;

    public SearchBar(final String user) {
        this.results = new ArrayList<>();
        this.user = user;
    }

    /**
     * function that resets searchBar fields
     * */
    public void clearSelection() {
        lastSelected = null;
        lastSearchType = null;
    }

    /**
     * function that searches a libraryEntry, conform to the input filters
     * */
    public List<LibraryEntry> search(final Filters filters, final String type) {
        List<LibraryEntry> entries;

        switch (type) {
            case "song":
                entries = new ArrayList<>(adminInstance.getSongs());

                if (filters.getName() != null) {
                    entries = filterByName(entries, filters.getName());
                }

                if (filters.getAlbum() != null) {
                    entries = filterByAlbum(entries, filters.getAlbum());
                }

                if (filters.getTags() != null) {
                    entries = filterByTags(entries, filters.getTags());
                }

                if (filters.getLyrics() != null) {
                    entries = filterByLyrics(entries, filters.getLyrics());
                }

                if (filters.getGenre() != null) {
                    entries = filterByGenre(entries, filters.getGenre());
                }

                if (filters.getReleaseYear() != null) {
                    entries = filterByReleaseYear(entries, filters.getReleaseYear());
                }

                if (filters.getArtist() != null) {
                    entries = filterByArtist(entries, filters.getArtist());
                }

                break;
            case "playlist":
                entries = new ArrayList<>(Admin.getPlaylists());

                entries = filterByPlaylistVisibility(entries, user);

                if (filters.getName() != null) {
                    entries = filterByName(entries, filters.getName());
                }

                if (filters.getOwner() != null) {
                    entries = filterByOwner(entries, filters.getOwner());
                }

                if (filters.getFollowers() != null) {
                    entries = filterByFollowers(entries, filters.getFollowers());
                }

                break;
            case "podcast":
                entries = new ArrayList<>(adminInstance.getPodcasts());

                if (filters.getName() != null) {
                    entries = filterByName(entries, filters.getName());
                }

                if (filters.getOwner() != null) {
                    entries = filterByOwner(entries, filters.getOwner());
                }

                break;

            case "album":
                entries = new ArrayList<>(adminInstance.getAlbums());
                if (filters.getName() != null) {
                    entries = filterByName(entries, filters.getName());
                }

                if (filters.getOwner() != null) {
                    entries = filterByOwner(entries, filters.getOwner());
                }

                if (filters.getDescription() != null) {
                    entries = filterByDescription(entries, filters.getDescription());
                }
                break;
            default:
                entries = new ArrayList<>();
        }

        while (entries.size() > MAX_RESULTS) {
            entries.remove(entries.size() - 1);
        }

        this.results = entries;
        this.lastSearchType = type;
        return this.results;
    }

    /**
     * function that searches an user, which can be artist or host
     * */
    public List<User> searchUser(final Filters filters, final String type) {
        List<User> users;

        switch (type) {
            case ("artist") -> {
                users = new ArrayList<>(adminInstance.getArtists());

                if (filters.getName() != null) {
                    users = filterUsers(users, filters.getName());
                }
            }

            case ("host") -> {
                users = new ArrayList<>(adminInstance.getHosts());

                if (filters.getName() != null) {
                    users = filterUsers(users, filters.getName());
                }
            }
            default -> {
                users = new ArrayList<>();
            }
        }

        while (users.size() > MAX_RESULTS) {
            users.remove(users.size() - 1);
        }

        this.userResults = users;
        this.lastSearchType = type;

        return this.userResults;
    }

    /**
     * function that selects one of the LibraryEntities searched before
     * */
    public LibraryEntry select(final Integer itemNumber) {
        if (this.results.size() < itemNumber) {
            results.clear();

            return null;
        } else {
            lastSelected =  this.results.get(itemNumber - 1);
            results.clear();

            return lastSelected;
        }
    }

    /**
     * function that selects one of the users searched before
     * */
    public User selectUser(final Integer itemNumber) {
        if (this.userResults.size() < itemNumber) {
            userResults.clear();

            return null;
        } else {
            lastUserSelected = this.userResults.get(itemNumber - 1);
            userResults.clear();

            return lastUserSelected;
        }
    }
}
