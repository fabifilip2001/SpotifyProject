package app.page.system.artistPages;

import app.audio.Collections.AudioCollection;
import app.page.system.Page;
import app.page.system.artistPages.utils.Event;
import app.page.system.artistPages.utils.Merch;
import app.user.User;
import lombok.Getter;

import java.util.List;

public final class ArtistPage extends Page {
    @Getter
    private List<Event> events;
    @Getter
    private List<Merch> merches;

    public ArtistPage(final User owner, final List<AudioCollection> audioCollection,
                      final List<Event> events, final List<Merch> merches) {
        super(owner, audioCollection);
        this.events = events;
        this.merches = merches;
    }

    /**
     * function that updates an artist's events
     * */
    public void updateEvents(final List<Event> newEvents) {
        this.events = newEvents;
    }

    /**
     * function thar updates an artist's merches
     * */
    public void updateMerches(final List<Merch> newMerches) {
        this.merches = newMerches;
    }
    @Override
    public String print() {
        return "Albums:\n\t"
                +
                getAudioCollection()
                +
                "\n\nMerch:\n\t"
                +
                getMerches()
                +
                "\n\nEvents:\n\t"
                +
                getEvents();
    }
}
