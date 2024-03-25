package com.youcode.korea2tv.services;


import com.youcode.korea2tv.models.entity.Watchlist;
import org.springframework.stereotype.Service;

import java.util.List;


public interface WatchlistService {

    void addToWatchlist(String userEmail, Long movieId);

    List<Watchlist> getWatchlistByUser(Long userId);

    void removeFromWatchlist(Long userId, Long movieId);
}
