package com.youcode.korea2tv.repositories;

import com.youcode.korea2tv.models.entity.AppUser;
import com.youcode.korea2tv.models.entity.Media;
import com.youcode.korea2tv.models.entity.Watchlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface WatchlistRepository extends JpaRepository<Watchlist, Long> {
    List<Watchlist> findByAppUsers(AppUser user);

    Watchlist findByAppUsersAndMedia(AppUser user, Media media);
    // You can define custom query methods if needed
}