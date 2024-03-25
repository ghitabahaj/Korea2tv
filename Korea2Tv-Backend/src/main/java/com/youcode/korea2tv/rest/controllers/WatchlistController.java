package com.youcode.korea2tv.rest.controllers;

import com.youcode.korea2tv.dtos.response.actor.ActorResDto;
import com.youcode.korea2tv.models.entity.Watchlist;
import com.youcode.korea2tv.services.WatchlistService;
import com.youcode.korea2tv.utils.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1.0.0/watchlist")
public class WatchlistController {

    private final WatchlistService watchlistService;

    @PostMapping("/add")
    public ResponseEntity<Response<String>> addToWatchlist(@RequestParam String userEmail, @RequestParam Long movieImdbId) {
        watchlistService.addToWatchlist(userEmail, movieImdbId);
        return new ResponseEntity<>(Response.<String>builder()
                .result("Movie added to watchlist successfully")
                .message("Success")
                .build(),
                HttpStatus.CREATED);
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
