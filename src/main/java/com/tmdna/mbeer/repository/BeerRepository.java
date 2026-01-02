package com.tmdna.mbeer.repository;

import com.tmdna.mbeer.model.Beer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface BeerRepository extends JpaRepository<Beer, UUID> {
    List<Beer> findAllByBeerNameContainingIgnoreCase(String name);
}
