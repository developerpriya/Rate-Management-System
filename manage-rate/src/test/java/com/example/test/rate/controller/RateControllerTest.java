package com.example.test.rate.controller;

import com.example.test.rate.exception.RateNotFound;
import com.example.test.rate.model.Rate;
import com.example.test.rate.repository.RateRepository;
import com.example.test.rate.service.RateService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;


import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static com.example.test.rate.model.Rate.*;
import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//dont want webservers
@RunWith(MockitoJUnitRunner.class)
class RateControllerTest {

    private MockMvc mockMvc;
//json to string
            ObjectMapper objectMapper =
                new ObjectMapper().registerModule(new JavaTimeModule());
    ObjectWriter objectWriter = objectMapper.writer();

    @Mock
    private RateService rateService;
    private RateRepository rateRepository;

    @InjectMocks
    private RateController rateController;

    Rate rate1 = new Rate(1L,"DONE",
            LocalDate.ofEpochDay(2021-12-12),
            LocalDate.ofEpochDay(2025-12-12),1234);
    Rate rate2 = new Rate(2L,"DONE",
            LocalDate.ofEpochDay(2021-12-12),
            LocalDate.ofEpochDay(2025-12-12),4567);


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(rateController).build();
    }
//
//    @Test
//    public void getValuesFromRestTemplate() throws URISyntaxException {
//        RestTemplate restTemplate = new RestTemplate();
//        final String baseUrl = "https://adfolks.free.beeceptor.com/charge";
//        URI uri = new URI(baseUrl);
//        ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);
//        //Verify request succeed
//        Assert.assertEquals(200, result.getStatusCodeValue());
//    }

    @Test
    public void getRateByIdSucess() throws Exception {

        Mockito.when(rateService.findById(rate1.getRateId()))
                .thenReturn(Optional.of(rate1));
     //   mockMvc.perform(get("/api/v1/rates/1")
                mockMvc.perform(get("/rates/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",notNullValue()))
                .andExpect(jsonPath("$.rateId").value(1))
                .andExpect(jsonPath("$.amount").value(1234));
    }

    @Test
    public void createRateRecordSucess() throws Exception {

        Rate rates = builder()
                .rateId(3L)
                .rateDesc("Increase")
                .rateEffDate(LocalDate.ofEpochDay(2024 - 12 - 12))
                .rateExpDate(LocalDate.ofEpochDay(2024 - 12 - 12))
                .amount(23456)
                .build();

        Mockito.when(rateService.saveRate(rates))
                .thenReturn(String.valueOf(rates));

        String content = objectWriter.writeValueAsString(rates);

        MockHttpServletRequestBuilder mockResult = MockMvcRequestBuilders.post("/rates")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content);

        mockMvc.perform(mockResult)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()));
//                .andExpect((ResultMatcher) jsonPath("$.rateId",is(rates.getRateId())));
//                .andExpect(jsonPath("$.rateId").value(3L));
//              .andExpect(jsonPath("$.amount").value(23456));

    }
//    @Test
//    @Disabled
//    public void updateRateRecordSucess() throws Exception {
//
//        Rate updatedRates = builder()
//                .rateId(1L)
//                .rateDesc("Decrease")
//                .rateEffDate(LocalDate.ofEpochDay(2024 - 12 - 12))
//                .rateExpDate(LocalDate.ofEpochDay(2024 - 12 - 12))
//                .amount(5678)
//                .build();
//        CompletableFuture<String> completableFuture = new CompletableFuture<String>();
//
//        Mockito.when(rateService.findById(rate1.getRateId())).thenReturn(java.util.Optional.of(rate1));
//
//        Mockito.when(rateService.updateRate(rate1.getRateId(),updatedRates))
//                .thenReturn(String.valueOf(updatedRates));
//
//        String updatedContent = objectWriter.writeValueAsString(updatedRates);
//
//        MockHttpServletRequestBuilder mockResult = MockMvcRequestBuilders.put("/api/v1/rates/1")
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON)
//                .content(updatedContent);
//
//        mockMvc.perform(mockResult)
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", notNullValue()));
////    //           .andExpect(jsonPath("$.rates.rateDesc").value("Decrease"));
////
////
////        //     .andExpect(MockMvcResultMatchers.jsonPath("$.desc").value("Decrease"));
////   //                  .andExpect(MockMvcResultMatchers.jsonPath("$.desc").exists());
////   //        .andExpect(MockMvcResultMatchers.jsonPath("$.desc").value("Decrease"));
////   //      .andExpect(MockMvcResultMatchers.jsonPath("$.rates[*].rateId").isNotEmpty());
////
////     //           .andExpect((ResultMatcher) jsonPath("$.amount",is(5678)));
////
//   }
    @Test
    public void deleteRateRecord_sucess() throws Exception {
        Mockito.when(rateService.findById(rate2.getRateId()))
                .thenReturn(java.util.Optional.of(rate2));
        mockMvc.perform(delete("/rates/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());


    }
    }