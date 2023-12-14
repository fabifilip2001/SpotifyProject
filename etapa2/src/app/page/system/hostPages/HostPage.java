package app.page.system.hostPages;

import app.audio.Collections.AudioCollection;
import app.page.system.Page;
import app.page.system.hostPages.utils.Announcement;
import app.user.User;
import lombok.Getter;

import java.util.List;

public class HostPage extends Page {
    @Getter
    private List<Announcement> announcements;

    public HostPage(User owner, List<AudioCollection> audioCollection, List<Announcement> announcements) {
        super(owner, audioCollection);
        this.announcements = announcements;
    }

    @Override
    public void updateAnnouncements(List<Announcement> announcements) {
        this.announcements = announcements;
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
