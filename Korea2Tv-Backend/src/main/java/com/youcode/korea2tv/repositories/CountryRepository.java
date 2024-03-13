package com.youcode.korea2tv.repositories;

import  com.youcode.korea2tv.models.entity.Country;
import  com.youcode.korea2tv.models.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
    Optional<Country> findCountriesByIso(String iso);

}
