package app.utils;

public class Enums { // diferite enumuri, le-am gurpat pe toate intr-un loc
    public enum Genre {
        POP,
        ROCK,
        RAP
    } // etc

    public enum Visibility {
        PUBLIC,
        PRIVATE
    }

    public enum SearchType {
        SONG,
        PLAYLIST,
        PODCAST
    }

    public enum RepeatMode {
        REPEAT_ALL, REPEAT_ONCE, REPEAT_INFINITE, REPEAT_CURRENT_SONG, NO_REPEAT,
    }

    public enum PlayerSourceType {
        LIBRARY, PLAYLIST, ALBUM, PODCAST
    }

    public enum UserConnectionStatus {
        ONLINE, OFFLINE
    }

    public enum PageType {
        HOMEPAGE, LIKED_CONTENT_PAGE, ARTIST_PAGE, HOST_PAGE
    }
}
