package com.tmdna.mbeer.service;

import com.tmdna.mbeer.dto.BeerDTO;
import com.tmdna.mbeer.mapper.BeerMapper;
import com.tmdna.mbeer.repository.BeerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Primary
@RequiredArgsConstructor
public class BeerServiceJPA implements BeerService {
    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    @Override
    public Optional<BeerDTO> getBeerById(UUID id) {
        return Optional.ofNullable(beerMapper.beerToBeerDto(
                beerRepository.findById(id).orElse(null)));
    }

    @Override
    public List<BeerDTO> getAllBeers() {
        return beerRepository.findAll().stream()
                .map(beerMapper::beerToBeerDto)
                .toList();
    }

    @Override
    public BeerDTO createBeer(BeerDTO beer) {
        return null;
    }

    @Override
    public void updateBeerFully(UUID id, BeerDTO beer) {

    }

    @Override
    public void deleteBeer(UUID id) {

    }

    @Override
    public void updateBeerPartially(UUID id, BeerDTO beer) {

    }
}
