package com.tmdna.mbeer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tmdna.mbeer.config.ApiPaths;
import com.tmdna.mbeer.dto.BeerDTO;
import com.tmdna.mbeer.exception.NotFoundException;
import com.tmdna.mbeer.service.BeerService;
import com.tmdna.mbeer.service.BeerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BeerController.class)
class BeerControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    BeerService beerService;

    BeerServiceImpl beerServiceImpl;

    @Captor
    ArgumentCaptor<BeerDTO> beerArgumentCaptor;

    @Captor
    ArgumentCaptor<UUID> uuidArgumentCaptor;

    @BeforeEach
    void setUp() {
        beerServiceImpl = new BeerServiceImpl();
    }

    @Test
    void getBeerByIdWithNotFoundException() throws Exception {
        given(beerService.getBeerById(any(UUID.class)))
                .willReturn(Optional.empty());

        mvc.perform(get(ApiPaths.Beer.WITH_ID, UUID.randomUUID())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result ->
                        assertInstanceOf(NotFoundException.class, result.getResolvedException()));
    }

    @Test
    void updateBeerPartially() throws Exception {
        BeerDTO beer = beerServiceImpl.getAllBeers().getFirst();

        Map<String, Object> beerMap = new HashMap<>();
        beerMap.put("beerName", "New Name");

        mvc.perform(patch(ApiPaths.Beer.WITH_ID, beer.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beerMap)))
                .andExpect(status().isNoContent());

        verify(beerService).updateBeerPartially(uuidArgumentCaptor.capture(), beerArgumentCaptor.capture());

        assertEquals(beer.getId(), uuidArgumentCaptor.getValue());
        assertEquals(beerMap.get("beerName"), beerArgumentCaptor.getValue().getBeerName());
    }

    @Test
    void deleteBeer() throws Exception {
        BeerDTO beer = beerServiceImpl.getAllBeers().getFirst();

        given(beerService.deleteBeer(any(UUID.class))).willReturn(true);

        mvc.perform(delete(ApiPaths.Beer.WITH_ID, beer.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(beerService).deleteBeer(uuidArgumentCaptor.capture());

        assertEquals(beer.getId(), uuidArgumentCaptor.getValue());
    }

    @Test
    void updateBeerFullyWithEmptyFields() throws Exception {
        BeerDTO beer = beerServiceImpl.getAllBeers().getFirst();
        beer.setBeerName(null);
        beer.setBeerStyle(null);
        beer.setPrice(null);
        beer.setUpd(null);

        given(beerService.updateBeerFully(any(UUID.class), any())).willReturn(Optional.of(beer));

       mvc.perform(put(ApiPaths.Beer.WITH_ID, beer.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beer)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.details.size()", is(7)));
    }

    @Test
    void updateBeerFully() throws Exception {
        BeerDTO beer = beerServiceImpl.getAllBeers().getFirst();

        given(beerService.updateBeerFully(any(UUID.class), any())).willReturn(Optional.of(beer));

        mvc.perform(put(ApiPaths.Beer.WITH_ID, beer.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beer)))
                .andExpect(status().isNoContent());

        verify(beerService).updateBeerFully(any(UUID.class), any(BeerDTO.class));
    }

    @Test
    void createBeerWithNullName() throws Exception {
        BeerDTO beerDTO = BeerDTO.builder().build();

        given(beerService.createBeer(any(BeerDTO.class))).willReturn(beerDTO);

        MvcResult mvcResult = mvc.perform(post(ApiPaths.Beer.BASE)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beerDTO)))
                .andExpect(status().isBadRequest()).andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    @Test
    void createBeer() throws Exception {
        BeerDTO beer = beerServiceImpl.getAllBeers().getFirst();
        beer.setId(null);
        beer.setVersion(null);
        beer.setUpdatedTime(null);
        beer.setCreatedTime(null);

        given(beerService.createBeer(any(BeerDTO.class)))
                .willReturn(beerServiceImpl.getAllBeers().get(1));

        MvcResult mvcResult = mvc.perform(post(ApiPaths.Beer.BASE)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beer)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location")).andReturn();

        System.out.println(mvcResult);
    }

    @Test
    void getAllBeers() throws Exception {

        given(beerService.getAllBeers()).willReturn(beerServiceImpl.getAllBeers());

        mvc.perform(get(ApiPaths.Beer.BASE)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(beerServiceImpl.getAllBeers().size())));
    }

    @Test
    void getBeerByIdById() throws Exception {

        BeerDTO beer = beerServiceImpl.getAllBeers().getFirst();

        given(beerService.getBeerById(beer.getId())).willReturn(Optional.of(beer));

        mvc.perform(get(ApiPaths.Beer.WITH_ID, beer.getId()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(beer.getId().toString())))
                .andExpect(jsonPath("$.beerName", is(beer.getBeerName())));
    }
}