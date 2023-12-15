package app;

import app.audio.Collections.AlbumOutput;
import app.audio.Collections.PlaylistOutput;
import app.audio.Collections.PodcastOutput;
import app.player.PlayerStats;
import app.searchBar.Filters;
import app.user.Artist;
import app.user.Host;
import app.user.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.input.CommandInput;

import java.util.ArrayList;
import java.util.List;

public final class CommandRunner {
    private static ObjectMapper objectMapper = new ObjectMapper();

    private CommandRunner() { }

    /**
     * function that calls the 'search' logical function and format its output
     * */
    public static ObjectNode search(final CommandInput commandInput) {
        String message;
        User user = Admin.getInstance().getUser(commandInput.getUsername());
        Filters filters = new Filters(commandInput.getFilters());
        String type = commandInput.getType();

        ArrayList<String> results = user.search(filters, type);
        if (user.isOffline()) {
            message = "%s is offline.".formatted(user.getUsername());
        } else {
            message = "Search returned " + results.size() + " results";
        }

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);
        objectNode.put("results", objectMapper.valueToTree(results));

        return objectNode;
    }

    /**
     * function that calls the 'select' logical function and format its output
     * */
    public static ObjectNode select(final CommandInput commandInput) {
        User user = Admin.getInstance().getUser(commandInput.getUsername());

        String message = user.select(commandInput.getItemNumber());

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * function that calls the 'load' logical function and format its output
     * */
    public static ObjectNode load(final CommandInput commandInput) {
        User user = Admin.getInstance().getUser(commandInput.getUsername());
        String message = user.load();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * function that calls the 'playpause' logical function and format its output
     * */
    public static ObjectNode playPause(final CommandInput commandInput) {
        User user = Admin.getInstance().getUser(commandInput.getUsername());
        String message = user.playPause();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * function that calls the 'repeat' logical function and format its output
     * */
    public static ObjectNode repeat(final CommandInput commandInput) {
        User user = Admin.getInstance().getUser(commandInput.getUsername());
        String message = user.repeat();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * function that calls the 'shuffle' logical function and format its output
     * */
    public static ObjectNode shuffle(final CommandInput commandInput) {
        User user = Admin.getInstance().getUser(commandInput.getUsername());
        Integer seed = commandInput.getSeed();
        String message = user.shuffle(seed);

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * function that calls the 'forward' logical function and format its output
     * */
    public static ObjectNode forward(final CommandInput commandInput) {
        User user = Admin.getInstance().getUser(commandInput.getUsername());
        String message = user.forward();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * function that calls the 'backward' logical function and format its output
     * */
    public static ObjectNode backward(final CommandInput commandInput) {
        User user = Admin.getInstance().getUser(commandInput.getUsername());
        String message = user.backward();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * function that calls the 'like' logical function and format its output
     * */
    public static ObjectNode like(final CommandInput commandInput) {
        User user = Admin.getInstance().getUser(commandInput.getUsername());
        String message = user.like();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * function that calls the 'next' logical function and format its output
     * */
    public static ObjectNode next(final CommandInput commandInput) {
        User user = Admin.getInstance().getUser(commandInput.getUsername());
        String message = user.next();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * function that calls the 'prev' logical function and format its output
     * */
    public static ObjectNode prev(final CommandInput commandInput) {
        User user = Admin.getInstance().getUser(commandInput.getUsername());
        String message = user.prev();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * function that calls the 'createPlaylist' logical function and format its output
     * */
    public static ObjectNode createPlaylist(final CommandInput commandInput) {
        User user = Admin.getInstance().getUser(commandInput.getUsername());
        String message = user.createPlaylist(commandInput.getPlaylistName(),
                commandInput.getTimestamp());

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * function that calls the 'addRemoveInPlaylist' logical function and format its output
     * */
    public static ObjectNode addRemoveInPlaylist(final CommandInput commandInput) {
        User user = Admin.getInstance().getUser(commandInput.getUsername());
        String message = user.addRemoveInPlaylist(commandInput.getPlaylistId());

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * function that calls the 'switchVisibility' logical function and format its output
     * */
    public static ObjectNode switchVisibility(final CommandInput commandInput) {
        User user = Admin.getInstance().getUser(commandInput.getUsername());
        String message = user.switchPlaylistVisibility(commandInput.getPlaylistId());

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * function that calls the 'showPlaylists' logical function and format its output
     * */
    public static ObjectNode showPlaylists(final CommandInput commandInput) {
        User user = Admin.getInstance().getUser(commandInput.getUsername());
        ArrayList<PlaylistOutput> playlists = user.showPlaylists();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(playlists));

        return objectNode;
    }

    /**
     * function that calls the 'follow' logical function and format its output
     * */
    public static ObjectNode follow(final CommandInput commandInput) {
        User user = Admin.getInstance().getUser(commandInput.getUsername());
        String message = user.follow();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * function that calls the 'status' logical function and format its output
     * */
    public static ObjectNode status(final CommandInput commandInput) {
        User user = Admin.getInstance().getUser(commandInput.getUsername());
        PlayerStats stats = user.getPlayerStats();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("stats", objectMapper.valueToTree(stats));

        return objectNode;
    }

    /**
     * function that calls the 'showLikedSongs' logical function and format its output
     * */
    public static ObjectNode showLikedSongs(final CommandInput commandInput) {
        User user = Admin.getInstance().getUser(commandInput.getUsername());
        ArrayList<String> songs = user.showPreferredSongs();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(songs));

        return objectNode;
    }

    /**
     * function that calls the 'getPreferredGenre' logical function and format its output
     * */
    public static ObjectNode getPreferredGenre(final CommandInput commandInput) {
        User user = Admin.getInstance().getUser(commandInput.getUsername());
        String preferredGenre = user.getPreferredGenre();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(preferredGenre));

        return objectNode;
    }

    /**
     * function that calls the 'getTop5Songs' logical function and format its output
     * */
    public static ObjectNode getTop5Songs(final CommandInput commandInput) {
        List<String> songs = Admin.getInstance().getTop5Songs();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(songs));

        return objectNode;
    }

    /**
     * function that calls the 'getTop5Playlists' logical function and format its output
     * */
    public static ObjectNode getTop5Playlists(final CommandInput commandInput) {
        List<String> playlists = Admin.getInstance().getTop5Playlists();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(playlists));

        return objectNode;
    }

    /**
     * function that calls the 'switchConnectionStatus' logical function and format its output
     * */
    public static ObjectNode switchConnectionStatus(final CommandInput commandInput) {
        String message;
        String username = commandInput.getUsername();
        if (!Admin.getInstance().getAllUsers().contains(username)) {
            message = "The username %s doesn't exist.".formatted(username);
        } else if (Admin.getInstance().getUser(username) == null) {
            message = "%s is not a normal user.".formatted(username);
        } else {
            User user = Admin.getInstance().getUser(commandInput.getUsername());
            message = user.switchConnectionStatus();
        }

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);
        return objectNode;
    }

    /**
     * function that calls the 'getOnlineUsers' logical function and format its output
     * */
    public static ObjectNode getOnlineUsers(final CommandInput commandInput) {
        List<String> onlineUsers = Admin.getInstance().getOnlineUsers();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(onlineUsers));

        return objectNode;
    }

    /**
     * function that calls the 'addUser' logical function and format its output
     * */
    public static ObjectNode addUser(final CommandInput commandInput) {
        String message = Admin.getInstance().addUser(commandInput.getUsername(), commandInput.getType(),
                commandInput.getAge(), commandInput.getCity());

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * function that calls the 'addAlbum' logical function and format its output
     * */
    public static ObjectNode addAlbum(final CommandInput commandInput) {
        String message;
        String username = commandInput.getUsername();

        if (!Admin.getInstance().getAllUsers().contains(username)) {
            message = "The username %s doesn't exist.".formatted(username);
        } else if (Admin.getInstance().getArtist(username) == null) {
            message = "%s is not an artist.".formatted(username);
        } else {
            Artist artist = Admin.getInstance().getArtist(username);
            message = artist.addAlbum(commandInput);
        }

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * function that calls the 'showAlbums' logical function and format its output
     * */
    public static ObjectNode showAlbums(final CommandInput commandInput) {
        List<AlbumOutput> albumOutputs = Admin.getInstance().showAlbums(commandInput.getUsername());

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(albumOutputs));

        return objectNode;
    }

    /**
     * function that calls the 'printCurrentPage' logical function and format its output
     * */
    public static ObjectNode printCurrentPage(final CommandInput commandInput) {
        String username = commandInput.getUsername();
        User user = Admin.getInstance().getUser(username);

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("user", username);
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", user.printCurrentPage());
        return objectNode;
    }

    /**
     * function that calls the 'addEvent' logical function and format its output
     * */
    public static ObjectNode addEvent(final CommandInput commandInput) {
        String message;
        String username = commandInput.getUsername();

        if (!Admin.getInstance().checkIfUserExists(username)) {
            message = "The username %s doesn't exist.".formatted(username);
        } else if (Admin.getInstance().getArtist(username) == null) {
            message = "%s is not an artist.".formatted(username);
        } else {
            Artist artist = Admin.getInstance().getArtist(username);
            message = artist.addEvent(commandInput);
        }

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("message", message);
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        return objectNode;
    }

    /**
     * function that calls the 'addMerch' logical function and format its output
     * */
    public static ObjectNode addMerch(final CommandInput commandInput) {
        String message;
        String username = commandInput.getUsername();

        if (!Admin.getInstance().checkIfUserExists(username)) {
            message = "The username %s doesn't exist.".formatted(username);
        } else if (Admin.getInstance().getArtist(username) == null) {
            message = "%s is not an artist.".formatted(username);
        } else {
            Artist artist = Admin.getInstance().getArtist(username);
            message = artist.addMerch(commandInput);
        }

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("message", message);
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("user", commandInput.getUsername());
        return objectNode;
    }

    /**
     * function that calls the 'getAllUsers' logical function and format its output
     * */
    public static ObjectNode getAllUsers(final CommandInput commandInput) {
        List<String> allUsers = Admin.getInstance().getAllUsers();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(allUsers));
        return objectNode;
    }

    /**
     * function that calls the 'deleteUser' logical function and format its output
     * */
    public static ObjectNode deleteUser(final CommandInput commandInput) {
        String message = Admin.getInstance().deleteUser(commandInput.getUsername());

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);
        return objectNode;
    }

    /**
     * function that calls the 'addPodcast' logical function and format its output
     * */
    public static ObjectNode addPodcast(final CommandInput commandInput) {
        String message;
        String username = commandInput.getUsername();

        if (!Admin.getInstance().checkIfUserExists(username)) {
            message = "The username %s doesn't exist.".formatted(username);
        } else if (Admin.getInstance().getHost(username) == null) {
            message = "%s is not a host.".formatted(username);
        } else {
            Host host = Admin.getInstance().getHost(username);
            message = host.addPodcast(commandInput);
        }
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", username);
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);
        return objectNode;
    }

    /**
     * function that calls the 'addAnnouncement' logical function and format its output
     * */
    public static ObjectNode addAnnouncement(final CommandInput commandInput) {
        String message;
        String username = commandInput.getUsername();

        if (!Admin.getInstance().checkIfUserExists(username)) {
            message = "The username %s doesn't exist.".formatted(username);
        } else if (Admin.getInstance().getHost(username) == null) {
            message = "%s is not a host.".formatted(username);
        } else {
            Host host = Admin.getInstance().getHost(username);
            message = host.addAnnouncement(commandInput);
        }

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("message", message);
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("user", username);
        return objectNode;
    }

    /**
     * function that calls the 'removeAnnouncement' logical function and format its output
     * */
    public static ObjectNode removeAnnouncement(final CommandInput commandInput) {
        String message;
        String username = commandInput.getUsername();

        if (!Admin.getInstance().checkIfUserExists(username)) {
            message = "The username %s doesn't exist.".formatted(username);
        } else if (Admin.getInstance().getHost(username) == null) {
            message = "%s is not a host.".formatted(username);
        } else {
            Host host = Admin.getInstance().getHost(username);
            message = host.removeAnnouncement(commandInput);
        }

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", username);
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);
        return objectNode;
    }

    /**
     * function that calls the 'showPodcasts' logical function and format its output
     * */
    public static ObjectNode showPodcasts(final CommandInput commandInput) {
        String username = commandInput.getUsername();
        List<PodcastOutput> result = Admin.getInstance().showPodcasts(username);

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", username);
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(result));
        return objectNode;
    }

    /**
     * function that calls the 'removeAlbum' logical function and format its output
     * */
    public static ObjectNode removeAlbum(final CommandInput commandInput) {
        String username = commandInput.getUsername();
        String message;

        if (!Admin.getInstance().checkIfUserExists(username)) {
            message = "The username %s doesn't exist.".formatted(username);
        } else if (Admin.getInstance().getArtist(username) == null) {
            message = "%s is not an artist.".formatted(username);
        } else {
            Artist artist = Admin.getInstance().getArtist(username);
            message = artist.removeAlbum(commandInput);
        }

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", username);
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);
        return objectNode;
    }

    /**
     * function that calls the 'changePage' logical function and format its output
     * */
    public static ObjectNode changePage(final CommandInput commandInput) {
        String username = commandInput.getUsername();
        String message;

        User user = Admin.getInstance().getUser(username);
        message = user.changePage(commandInput);

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", username);
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);
        return objectNode;
    }

    /**
     * function that calls the 'removePodcast' logical function and format its output
     * */
    public static ObjectNode removePodcast(final CommandInput commandInput) {
        String username = commandInput.getUsername();
        String message;

        if (!Admin.getInstance().checkIfUserExists(username)) {
            message = "The username %s doesn't exist.".formatted(username);
        } else if (Admin.getInstance().getHost(username) == null) {
            message = "%s is not a host.".formatted(username);
        } else {
            Host host = Admin.getInstance().getHost(username);
            message = host.removePodcast(commandInput);
        }

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", username);
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);
        return objectNode;
    }

    /**
     * function that calls the 'removeEvent' logical function and format its output
     * */
    public static ObjectNode removeEvent(final CommandInput commandInput) {
        String username = commandInput.getUsername();
        String message;

        if (!Admin.getInstance().checkIfUserExists(username)) {
            message = "The username %s doesn't exist.".formatted(username);
        } else if (Admin.getInstance().getArtist(username) == null) {
            message = "%s is not an artist.".formatted(username);
        } else {
            Artist artist = Admin.getInstance().getArtist(username);
            message = artist.removeEvent(commandInput);
        }

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", username);
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);
        return objectNode;
    }

    /**
     * function that calls the 'getTop5Albums logical function and format its output
     * */
    public static ObjectNode getTop5Albums(final CommandInput commandInput) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(Admin.getInstance().getTop5Albums()));
        return objectNode;
    }

    /**
     * function that calls 'getTop5Artists' logical function and format its output
     * */
    public static ObjectNode getTop5Artists(final CommandInput commandInput) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(Admin.getInstance().getTop5Artists()));
        return objectNode;
    }
}
