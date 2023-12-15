package app.audio.Collections;

import app.audio.Files.AudioFile;
import app.audio.LibraryEntry;
import lombok.Getter;

@Getter
public abstract class AudioCollection extends LibraryEntry {
    private final String owner;

    public AudioCollection(final String name, final String owner) {
        super(name);
        this.owner = owner;
    }

    /**
     * abstract method that counts tracks from an audioCollection
     * */
    public abstract int getNumberOfTracks();

    /**
     * abstract method that a track by id from an audioCollection
     * */
    public abstract AudioFile getTrackByIndex(int index);

    /**
     * abstract method that counts total likes from an audioCollection
     * */
    public abstract int countLikes();

    /**
     * abstract method that prints tracks from an audioCollection
     * */
    public abstract String likedContentPagePrint();

    /**
     * {@inheritDoc}
     * */
    @Override
    public boolean matchesOwner(final String user) {
        return this.getOwner().equals(user);
    }


}
