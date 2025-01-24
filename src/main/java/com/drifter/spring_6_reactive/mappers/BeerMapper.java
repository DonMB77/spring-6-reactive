package com.drifter.spring_6_reactive.mappers;

import com.drifter.spring_6_reactive.domain.Beer;
import com.drifter.spring_6_reactive.domain.BeerDTO;
import org.mapstruct.Mapper;

@Mapper
public interface BeerMapper {
    Beer beerDtoToBeer(BeerDTO dto);
    BeerDTO beerToBeerDto(Beer beer);
}
