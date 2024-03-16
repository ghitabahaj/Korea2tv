package com.youcode.korea2tv.repositories;

import com.youcode.korea2tv.models.entity.MediaCredit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MediaCreditRepository extends JpaRepository<MediaCredit, Long> {
}
