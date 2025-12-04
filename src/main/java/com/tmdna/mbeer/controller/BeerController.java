package com.tmdna.mbeer.controller;

import com.tmdna.mbeer.config.ApiPaths;
import com.tmdna.mbeer.dto.BeerDTO;
import com.tmdna.mbeer.exception.NotFoundException;
import com.tmdna.mbeer.service.BeerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiPaths.Beer.BASE)
public class BeerController {

    private final BeerService beerService;

    @PatchMapping(ApiPaths.ID)
    public ResponseEntity<Void> updateBeerPartially(
            @PathVariable("id") UUID id,
            @RequestBody BeerDTO beer
    ) {
        beerService.updateBeerPartially(id, beer);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(ApiPaths.ID)
    public ResponseEntity<Void> deleteBeer(@PathVariable("id") UUID id) {
        if (!beerService.deleteBeer(id)) {
            throw new NotFoundException(String.format("Beer with id: %s does not exists.", id));
        }
        return ResponseEntity.noContent().build();
    }

    @PutMapping(ApiPaths.ID)
    public ResponseEntity<Void> updateBeerFully(
            @PathVariable("id") UUID id,
            @RequestBody BeerDTO beer
    ) {
        beerService.updateBeerFully(id, beer).orElseThrow(NotFoundException::new);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<BeerDTO>> getBeers() {
        return ResponseEntity.ok(beerService.getAllBeers());
    }

    @GetMapping(ApiPaths.ID)
    public ResponseEntity<BeerDTO> getBeerById(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(beerService.getBeerById(id)
                .orElseThrow(NotFoundException::new));
    }

    @PostMapping
    public ResponseEntity<Void> createBeer(@RequestBody BeerDTO beer) {
        BeerDTO createdBeer = beerService.createBeer(beer);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdBeer.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }
}
