package com.tmdna.mbeer.service;

import com.tmdna.mbeer.model.Beer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BeerService {
    Optional<Beer> getBeerById(UUID id);

    List<Beer> getAllBeers();

    Beer createBeer(Beer beer);

    void updateBeerFully(UUID id, Beer beer);

    void deleteBeer(UUID id);

    void updateBeerPartially(UUID id, Beer beer);
}
