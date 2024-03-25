package com.youcode.korea2tv.rest.controllers;

import com.youcode.korea2tv.models.entity.Watchlist;
import com.youcode.korea2tv.services.WatchlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1.0.0/watchlist")
public class WatchlistController {

    private final WatchlistService watchlistService;

    @PostMapping("/add")
    public ResponseEntity<?> addToWatchlist(@RequestParam String userEmail, @RequestParam Long movieImdbId) {
        try {

            watchlistService.addToWatchlist(userEmail, movieImdbId);
            return ResponseEntity.ok("Movie added to watchlist successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to add movie to watchlist: " + e.getMessage());
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getWatchlistByUser(@PathVariable Long userId) {
        try {
            List<Watchlist> watchlist = watchlistService.getWatchlistByUser(userId);
            return ResponseEntity.ok(watchlist);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to get watchlist: " + e.getMessage());
        }
    }

    @DeleteMapping("/remove")
    public ResponseEntity<?> removeFromWatchlist(@RequestParam Long userId, @RequestParam Long movieId) {
        try {
            watchlistService.removeFromWatchlist(userId, movieId);
            return ResponseEntity.ok("Movie removed from watchlist successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to remove movie from watchlist: " + e.getMessage());
        }
    }

}
