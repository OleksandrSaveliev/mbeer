package com.tmdna.mbeer.service;

import com.tmdna.mbeer.dto.BeerDTO;
import com.tmdna.mbeer.mapper.BeerMapper;
import com.tmdna.mbeer.repository.BeerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

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
        return beerMapper.beerToBeerDto(
                beerRepository.save(beerMapper.beerDtoToBeer(beer)));
    }

    @Override
    public Optional<BeerDTO> updateBeerFully(UUID id, BeerDTO beer) {
        AtomicReference<Optional<BeerDTO>> atomicReference = new AtomicReference<>();

        beerRepository.findById(id).ifPresentOrElse(foundBeer -> {
                    foundBeer.setBeerName(beer.getBeerName());
                    foundBeer.setBeerStyle(beer.getBeerStyle());
                    foundBeer.setUpdatedTime(LocalDateTime.now());
                    foundBeer.setUpd(beer.getUpd());
                    foundBeer.setQuantityOnHand(beer.getQuantityOnHand());
                    foundBeer.setPrice(beer.getPrice());

                    atomicReference.set(Optional.of(
                            beerMapper.beerToBeerDto(
                                    beerRepository.save(foundBeer))));
                }, () -> atomicReference.set(Optional.empty())
        );

        return atomicReference.get();
    }

    @Override
    public boolean deleteBeer(UUID id) {
        if (!beerRepository.existsById(id)) {
            return false;
        }
        beerRepository.deleteById(id);
        return true;
    }

    @Override
    public Optional<BeerDTO> updateBeerPartially(UUID id, BeerDTO beer) {
        AtomicReference<Optional<BeerDTO>> atomicReference = new AtomicReference<>();

        beerRepository.findById(id).ifPresentOrElse(foundBeer -> {
                    if (Objects.nonNull(beer.getBeerName())) {
                        foundBeer.setBeerName(beer.getBeerName());
                    }
                    if (Objects.nonNull(beer.getBeerStyle())) {
                        foundBeer.setBeerStyle(beer.getBeerStyle());
                    }
                    if (Objects.nonNull(beer.getBeerName())) {
                        foundBeer.setUpd(beer.getUpd());
                    }
                    if (Objects.nonNull(beer.getQuantityOnHand())) {
                        foundBeer.setQuantityOnHand(beer.getQuantityOnHand());
                    }
                    if (Objects.nonNull(beer.getPrice())) {
                        foundBeer.setPrice(beer.getPrice());
                    }
                    foundBeer.setUpdatedTime(LocalDateTime.now());


                    atomicReference.set(Optional.of(
                            beerMapper.beerToBeerDto(
                                    beerRepository.save(foundBeer))));
                }, () -> atomicReference.set(Optional.empty())
        );

        return atomicReference.get();
    }
}
