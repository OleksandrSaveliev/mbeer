package com.tmdna.mbeer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tmdna.mbeer.config.ApiPaths;
import com.tmdna.mbeer.dto.BeerDTO;
import com.tmdna.mbeer.exception.NotFoundException;
import com.tmdna.mbeer.mapper.BeerMapper;
import com.tmdna.mbeer.model.Beer;
import com.tmdna.mbeer.repository.BeerRepository;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class BeerControllerIT {
    public static final UUID FAILED_UUID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    @Autowired
    BeerController controller;

    @Autowired
    BeerRepository beerRepository;

    @Autowired
    BeerMapper beerMapper;

    @Autowired
    WebApplicationContext wac;

    @Autowired
    ObjectMapper objectMapper;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    void updateBeerPartially_withInvalidBeerNameAndPrice_constraintViolationException() throws Exception {
        Beer beer = beerRepository.findAll().getFirst();

        Map<String, Object> beerMap = new HashMap<>();
        beerMap.put("beerName", "New Name".repeat(10));
        beerMap.put("price", new BigDecimal("-1.1"));

        mockMvc.perform(patch(ApiPaths.Beer.WITH_ID, beer.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beerMap)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.details.size()").value(2));
    }

    @Test
    void saveBeer_withInvalidValues_constraintViolationException() {
        BeerDTO beerDTO = BeerDTO.builder()
                .beerName("a".repeat(51))
                .beerStyle("style")
                .upd("12312321")
                .price(new BigDecimal("-12.33"))
                .build();

        Beer beer = beerMapper.beerDtoToBeer(beerDTO);

        assertThrows(ConstraintViolationException.class, () -> {
            beerRepository.saveAndFlush(beer);
        });

    }

    @Rollback
    @Transactional
    @Test
    void saveBeer_withCorrectValues() {
        BeerDTO beerDTO = BeerDTO.builder()
                .beerName("name")
                .beerStyle("style")
                .upd("12312321")
                .price(new BigDecimal("12.33"))
                .build();

        Beer savedBeer = beerRepository.save(beerMapper.beerDtoToBeer(beerDTO));

        beerRepository.flush();

        assertThat(savedBeer.getId()).isNotNull();
        assertEquals("name", savedBeer.getBeerName());
    }

    @Rollback
    @Transactional
    @Test
    void updateBeerPartially() {
        final String updatedName = "Updated Name";
        final String updatedStyle = "Updated Style";
        UUID id = beerRepository.findAll().getFirst().getId();

        BeerDTO beerDTO = BeerDTO.builder()
                .beerName(updatedName)
                .build();

        ResponseEntity<Void> response = controller.updateBeerPartially(id, beerDTO);

        assertEquals(HttpStatus.valueOf(204), response.getStatusCode());
        assertEquals(beerDTO.getBeerName(), beerRepository.findById(id).orElseThrow(NotFoundException::new).getBeerName());

        UUID id2 = beerRepository.findAll().get(1).getId();

        BeerDTO beerDTO2 = BeerDTO.builder()
                .beerName(updatedName)
                .beerStyle(updatedStyle)
                .build();

        ResponseEntity<Void> response2 = controller.updateBeerPartially(id2, beerDTO2);

        assertEquals(HttpStatus.valueOf(204), response2.getStatusCode());
        assertEquals(beerDTO2.getBeerName(), beerRepository.findById(id2).orElseThrow(NotFoundException::new).getBeerName());
        assertEquals(beerDTO2.getBeerStyle(), beerRepository.findById(id2).orElseThrow(NotFoundException::new).getBeerStyle());
    }

    @Test
    void deleteBeerByIdNotFoundTest() {
        assertThrows(NotFoundException.class, () -> controller.deleteBeer(FAILED_UUID));
    }

    @Rollback
    @Transactional
    @Test
    void deleteBeerByIdTest() {
        Beer beer = beerRepository.findAll().getFirst();
        UUID id = beer.getId();
        assertThat(controller.deleteBeer(id).getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));
        assertThrows(NotFoundException.class, () -> controller.getBeerById(id));
    }

    @Test
    void updateBeerFullyNotFoundTest() {
        BeerDTO beerDTO = BeerDTO.builder().build();
        assertThrows(NotFoundException.class, () -> controller.updateBeerFully(FAILED_UUID, beerDTO));
    }

    @Rollback
    @Transactional
    @Test
    void updateBeerFullyTest() {
        Beer beer = beerRepository.findAll().stream().findFirst().orElseThrow(() -> new RuntimeException("TEST FAILED"));
        BeerDTO beerDTO = beerMapper.beerToBeerDto(beer);
        beerDTO.setVersion(null);
        beerDTO.setId(null);
        final String newName = "New Name";
        final String newStyle = "New Style";
        beerDTO.setBeerName(newName);
        beerDTO.setBeerStyle(newStyle);

        ResponseEntity<Void> response = controller.updateBeerFully(beer.getId(), beerDTO);
        assertEquals(HttpStatusCode.valueOf(204), response.getStatusCode());

        BeerDTO updatedBeer = controller.getBeerById(beer.getId()).getBody();

        assertNotNull(updatedBeer);
        assertEquals(newName, updatedBeer.getBeerName());
        assertEquals(newStyle, updatedBeer.getBeerStyle());
    }

    @Rollback
    @Transactional
    @Test
    void createNewBeerWithProvidedName() {
        BeerDTO beer = BeerDTO.builder()
                .beerName("New Name")
                .build();

        ResponseEntity<Void> response = controller.createBeer(beer);

        assertEquals(HttpStatusCode.valueOf(201), response.getStatusCode());
        assertNotNull(response.getHeaders().getLocation());

        HttpHeaders headers = response.getHeaders();
        String locationPath = headers.getLocation().getPath();
        UUID id = UUID.fromString(locationPath.substring(1));

        assertNotNull(controller.getBeerById(id));
    }

    @Test
    void beerNotFoundTest() {
        assertThrows(NotFoundException.class, () -> controller.getBeerById(FAILED_UUID));
    }

    @Test
    void getBeerByIdByIdTest() {
        Beer beer = beerRepository.findAll().getFirst();

        assertNotNull(beerRepository.findById(beer.getId()));
    }

    @Test
    void getBeerByIdListTest() {
        List<BeerDTO> beerDTOS = controller.getBeers().getBody();

        assertNotNull(beerDTOS);
        assertEquals(3, beerDTOS.size());
    }

    @Rollback
    @Transactional
    @Test
    void emptyBeerListTest() {
        beerRepository.deleteAll();
        List<BeerDTO> beerDTOS = controller.getBeers().getBody();

        assertNotNull(beerDTOS);
        assertEquals(0, beerDTOS.size());
    }
}