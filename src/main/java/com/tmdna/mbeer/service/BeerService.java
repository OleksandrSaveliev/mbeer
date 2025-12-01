package com.tmdna.mbeer.service;

import com.tmdna.mbeer.dto.BeerDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BeerService {
    Optional<BeerDto> getBeerById(UUID id);

    List<BeerDto> getAllBeers();

    BeerDto createBeer(BeerDto beer);

    void updateBeerFully(UUID id, BeerDto beer);

    void deleteBeer(UUID id);

    void updateBeerPartially(UUID id, BeerDto beer);
}
