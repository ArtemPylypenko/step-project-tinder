package org.example.dao;

import org.example.Profile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProfileDao implements DAO<Profile> {
    private List<Profile> profiles = new ArrayList<>();
    private List<Profile> liked = new ArrayList<>();

    public ProfileDao() {
    }

    public List<Profile> getLiked() {
        return liked;
    }

    public void addLiked(Profile like) {
        liked.add(like);
    }

    @Override
    public Optional<Profile> get(int id) {
        return Optional.ofNullable(profiles.get((int) id));
    }

    @Override
    public List<Profile> getAll() {
        return profiles;
    }

    @Override
    public void save(Profile profile) {
        profiles.add(profile);
    }

    @Override
    public void delete(Profile profile) {
        profiles.remove(profile);
    }
}
