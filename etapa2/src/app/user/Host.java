package app.user;

import app.Admin;
import app.audio.Collections.Podcast;
import app.audio.Files.Episode;
import app.page.system.PageUtils;
import app.page.system.hostPages.HostPage;
import app.page.system.hostPages.utils.Announcement;
import app.player.Player;
import fileio.input.CommandInput;
import fileio.input.EpisodeInput;
import lombok.Getter;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;
import java.util.stream.Collectors;

public final class Host extends User {
    @Getter
    private final List<Podcast> podcasts;
    @Getter
    private final List<Announcement> announcements;
    private final HostPage page;

    /**
     * function that returns the host's page
     * */
    @Override
    public HostPage getPage() {
        return page;
    }

    public Host(final String username, final int age, final String city) {
        super(username, age, city);
        podcasts = new ArrayList<>();
        announcements = new ArrayList<>();
        page = new HostPage(this, PageUtils.getPodcasts(podcasts), announcements);
    }

    /**
     * function that adds a new podcast in host's entities
     * */
    public String addPodcast(final CommandInput commandInput) {
        String username = commandInput.getUsername();

        if (podcasts.stream().anyMatch(podcast -> podcast.getName()
                .equals(commandInput.getName()))) {
            return "%s has another podcast with the same name.".formatted(username);
        }

        Set<String> podcastEpisodesSet = new HashSet<>(commandInput.getEpisodes().stream()
                .map(EpisodeInput::getName).collect(Collectors.toList()));

        if (podcastEpisodesSet.size() < commandInput.getEpisodes().size()) {
            return "%s has the same episode in this podcast.".formatted(username);
        }

        List<Episode> episodes = new ArrayList<>();
        for (EpisodeInput episodeInput : commandInput.getEpisodes()) {
            Episode episode = new Episode(episodeInput.getName(), episodeInput.getDuration(),
                    episodeInput.getDescription());
            episodes.add(episode);
        }

        Podcast podcast = new Podcast(commandInput.getName(), username, episodes);
        podcasts.add(podcast);
        if (!Admin.getPodcasts().contains(podcast)) {
            Admin.addPodcast(podcast);
        }

        page.updateAudioCollection(PageUtils.getPodcasts(podcasts));

        return "%s has added new podcast successfully.".formatted(username);
    }
    /**
     * function that adds a new announcement in the host's entities
     * */
    public String addAnnouncement(final CommandInput commandInput) {
        String username = commandInput.getUsername();
        if (announcements.stream().anyMatch(announcement -> announcement
                .getName().equals(commandInput.getName()))) {
            return "%s has already added an announcement with this name.".formatted(username);
        }

        Announcement announcement = new Announcement(commandInput.getName(),
                commandInput.getDescription());
        announcements.add(announcement);
        page.updateAnnouncements(announcements);
        return "%s has successfully added new announcement.".formatted(username);
    }

    /**
     * function that removes an announcement from the host's entities
     * */
    public String removeAnnouncement(final CommandInput commandInput) {
        String username = commandInput.getUsername();
        if (!announcements.stream().anyMatch(announcement -> announcement
                .getName().equals(commandInput.getName()))) {
            return "%s has no announcement with the given name.".formatted(username);
        }

        Iterator<Announcement> iterator = announcements.iterator();
        while (iterator.hasNext()) {
            Announcement announcement = iterator.next();
            if (announcement.getName().equals(commandInput.getName())) {
                iterator.remove();
                break;
            }
        }

        return "%s has successfully deleted the announcement.".formatted(username);
    }

    /**
     * function that removes a podcast from the host's entities
     * */
    public String removePodcast(final CommandInput commandInput) {
        String username = commandInput.getUsername();
        if (!podcasts.stream().anyMatch(podcast -> podcast.getName()
                .equals(commandInput.getName()))) {
            return "%s doesn't have a podcast with the given name.".formatted(username);
        }
        for (User user : Admin.getUsers()) {
            Player player = user.getPlayer();
            if (player.getSource() != null) {
                if (player.getSource().getAudioCollection().getName()
                        .equals(commandInput.getName()) || podcasts.stream()
                        .anyMatch(podcast -> podcast.getEpisodes()
                                .contains(player.getSource().getAudioFile()))) {
                    return "%s can't delete this podcast.".formatted(username);
                }
            }
        }

        Podcast podcast = Admin.getPodcast(commandInput.getName());
        Admin.removePodcast(podcast);
        podcasts.remove(podcast);
        page.updateAudioCollection(PageUtils.getPodcasts(podcasts));
        return "%s deleted the podcast successfully.".formatted(username);
    }
}
