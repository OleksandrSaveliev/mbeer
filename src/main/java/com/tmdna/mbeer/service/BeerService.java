package com.tmdna.mbeer.service;

import com.tmdna.mbeer.dto.BeerDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BeerService {
    Optional<BeerDTO> getBeerById(UUID id);

    List<BeerDTO> getAllBeers();

    BeerDTO createBeer(BeerDTO beer);

    void updateBeerFully(UUID id, BeerDTO beer);

    void deleteBeer(UUID id);

    void updateBeerPartially(UUID id, BeerDTO beer);
}
