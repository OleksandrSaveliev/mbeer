package com.tmdna.mbeer.service;

import com.tmdna.mbeer.dto.BeerDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class BeerServiceImpl implements BeerService {

    Map<UUID, BeerDTO> beers = new HashMap<>();

    public BeerServiceImpl() {
        BeerDTO beer1 = BeerDTO.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Galaxy Cat")
                .beerStyle("Pale Ale")
                .upd("Initial")
                .quantityOnHand(100)
                .price(new BigDecimal("9.99"))
                .createdTime(LocalDateTime.now())
                .updatedTime(LocalDateTime.now())
                .build();

        BeerDTO beer2 = BeerDTO.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Chernivtsi Stout")
                .beerStyle("Stout")
                .upd("Initial")
                .quantityOnHand(80)
                .price(new BigDecimal("7.49"))
                .createdTime(LocalDateTime.now())
                .updatedTime(LocalDateTime.now())
                .build();

        BeerDTO beer3 = BeerDTO.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("UkrGolden Lager")
                .beerStyle("Lager")
                .upd("Initial")
                .quantityOnHand(120)
                .price(new BigDecimal("5.99"))
                .createdTime(LocalDateTime.now())
                .updatedTime(LocalDateTime.now())
                .build();

        beers.put(beer1.getId(), beer1);
        beers.put(beer2.getId(), beer2);
        beers.put(beer3.getId(), beer3);
    }

    @Override
    public Optional<BeerDTO> getBeerById(UUID id) {
        return Optional.of(beers.get(id));
    }

    public List<BeerDTO> getAllBeers() {
        return new ArrayList<>(beers.values());
    }

    @Override
    public BeerDTO createBeer(BeerDTO beer) {

        BeerDTO savedBeer = BeerDTO.builder()
                .id(UUID.randomUUID())
                .createdTime(LocalDateTime.now())
                .updatedTime(LocalDateTime.now())
                .version(1)
                .beerName(beer.getBeerName())
                .beerStyle(beer.getBeerStyle())
                .price(beer.getPrice())
                .quantityOnHand(beer.getQuantityOnHand())
                .upd(beer.getUpd())
                .build();

        beers.put(savedBeer.getId(), savedBeer);

        return savedBeer;
    }

    @Override
    public Optional<BeerDTO> updateBeerFully(UUID id, BeerDTO beer) {
        BeerDTO existing = beers.get(id);

        if (existing == null) {
            return Optional.empty();
        }

        existing.setBeerName(beer.getBeerName());
        existing.setBeerStyle(beer.getBeerStyle());
        existing.setPrice(beer.getPrice());
        existing.setUpd(beer.getUpd());
        existing.setQuantityOnHand(beer.getQuantityOnHand());
        existing.setVersion(existing.getVersion() + 1);
        existing.setUpdatedTime(LocalDateTime.now());

        return Optional.of(existing);
    }

    @Override
    public void deleteBeer(UUID id) {
        beers.remove(id);
    }

    @Override
    public void updateBeerPartially(UUID id, BeerDTO beer) {

        BeerDTO existing = beers.get(id);

        if (existing == null) {
            return;
        }

        if (StringUtils.hasText(beer.getBeerName())) {
            existing.setBeerName(beer.getBeerName());
        }

        if (StringUtils.hasText(beer.getBeerStyle())) {
            existing.setBeerStyle(beer.getBeerStyle());
        }

        if (beer.getPrice() != null) {
            existing.setPrice(beer.getPrice());
        }

        if (beer.getQuantityOnHand() != null) {
            existing.setQuantityOnHand(beer.getQuantityOnHand());
        }

        if (StringUtils.hasText(beer.getUpd())) {
            existing.setUpd(beer.getUpd());
        }

        existing.setVersion(existing.getVersion() + 1);
        existing.setUpdatedTime(LocalDateTime.now());
    }
}
