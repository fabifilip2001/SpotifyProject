package app.page.system.hostPages;

import app.audio.Collections.AudioCollection;
import app.page.system.Page;
import app.page.system.hostPages.utils.Announcement;
import app.user.User;
import lombok.Getter;

import java.util.List;

public final class HostPage extends Page {
    @Getter
    private List<Announcement> announcements;

    public HostPage(final User owner, final List<AudioCollection> audioCollection,
                    final List<Announcement> announcements) {
        super(owner, audioCollection);
        this.announcements = announcements;
    }

    /**
     * function thar updates a host's announcements
     * */
    @Override
    public void updateAnnouncements(final List<Announcement> newAnnouncements) {
        this.announcements = newAnnouncements;
    }

    @Override
    public String print() {
        return "Podcasts:\n\t"
                +
                getAudioCollection()
                +
                "\n\nAnnouncements:\n\t"
                +
                announcements;
    }
}
