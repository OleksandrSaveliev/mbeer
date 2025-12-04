package com.tmdna.mbeer.service;

import com.tmdna.mbeer.dto.BeerDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BeerService {
    Optional<BeerDTO> getBeerById(UUID id);

    List<BeerDTO> getAllBeers();

    BeerDTO createBeer(BeerDTO beer);

    Optional<BeerDTO> updateBeerFully(UUID id, BeerDTO beer);

    boolean deleteBeer(UUID id);

    Optional<BeerDTO> updateBeerPartially(UUID id, BeerDTO beer);
}
