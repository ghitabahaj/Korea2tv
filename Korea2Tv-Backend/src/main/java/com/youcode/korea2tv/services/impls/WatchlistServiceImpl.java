package com.youcode.korea2tv.services.impls;


import com.youcode.korea2tv.exception.custom.MovieAlreadyInWatchlistException;
import com.youcode.korea2tv.models.entity.AppUser;
import com.youcode.korea2tv.models.entity.Media;
import com.youcode.korea2tv.models.entity.Watchlist;
import com.youcode.korea2tv.repositories.MediaRepository;
import com.youcode.korea2tv.repositories.UserRepository;
import com.youcode.korea2tv.repositories.WatchlistRepository;
import com.youcode.korea2tv.services.WatchlistService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WatchlistServiceImpl implements WatchlistService {

    private final WatchlistRepository watchlistRepository;
    private final UserRepository userRepository;
    private final MediaRepository mediaRepository;


    @Override
    public void addToWatchlist(String userEmail, Long movieIdTmdb) {
        AppUser user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new EntityNotFoundException("User not found with email: " + userEmail));

        Media media = mediaRepository.findMediaByIdTmdb(movieIdTmdb)
                .orElseThrow(() -> new EntityNotFoundException("Movie not found with idTmdb: " + movieIdTmdb));

        boolean movieAlreadyInWatchlist = watchlistRepository.existsByAppUsersAndMedia(user, media);

        if (movieAlreadyInWatchlist) {
            throw new MovieAlreadyInWatchlistException("Movie with idTmdb " + movieIdTmdb + " already exists in the watchlist.");
        }

        // Create a new Watchlist entry
        Watchlist watchlist =
                Watchlist.builder()
                        .appUsers(user)
                        .media(media)
                        .build();

        watchlistRepository.save(watchlist);
    }


    @Override
    public List<Watchlist> getWatchlistByUser(Long userId) {
        AppUser user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        return watchlistRepository.findByAppUsers(user);
    }

    @Override
    public void removeFromWatchlist(Long userId, Long movieId) {
        AppUser user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        Media media = mediaRepository.findById(movieId).orElseThrow(() -> new EntityNotFoundException("Movie not found"));

        Watchlist watchlist = watchlistRepository.findByAppUsersAndMedia(user, media);
        if (watchlist != null) {
            watchlistRepository.delete(watchlist);
        }
    }

}
