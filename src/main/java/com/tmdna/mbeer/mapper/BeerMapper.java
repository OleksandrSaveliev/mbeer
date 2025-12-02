package com.tmdna.mbeer.mapper;

import com.tmdna.mbeer.dto.BeerDTO;
import com.tmdna.mbeer.model.Beer;
import org.mapstruct.Mapper;

@Mapper
public interface BeerMapper {
    Beer beerDtoToBeer(BeerDTO beerDTO);

    BeerDTO beerToBeerDto(Beer beer);
}
