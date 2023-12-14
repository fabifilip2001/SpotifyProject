package app.page.system.artistPages;

import app.audio.Collections.AudioCollection;
import app.page.system.Page;
import app.page.system.artistPages.utils.Event;
import app.page.system.artistPages.utils.Merch;
import app.user.User;
import lombok.Getter;

import java.util.List;

public class ArtistPage extends Page {
    @Getter
    private List<Event> events;
    @Getter
    private List<Merch> merches;

    public ArtistPage(User owner, List<AudioCollection> audioCollection, List<Event> events, List<Merch> merches) {
        super(owner, audioCollection);
        this.events = events;
        this.merches = merches;
    }

    public void updateEvents(List<Event> events) {
        this.events = events;
    }

    public void updateMerches(List<Merch> merches) {
        this.merches = merches;
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
