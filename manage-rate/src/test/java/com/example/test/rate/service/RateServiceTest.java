package com.example.test.rate.service;

import com.example.test.rate.model.Rate;
import com.example.test.rate.repository.RateRepository;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RateServiceTest {

    @Mock
    private RateRepository rateRepository;

    @InjectMocks
    private RateService rateService;


    @Test
    public void addRate_success() {
        Rate rate = new Rate();
        rate.setRateId(3L);
        rate.setRateDesc("Done");
        rate.setRateEffDate(LocalDate.ofEpochDay(2020 - 12 - 12));
        rate.setRateExpDate(LocalDate.ofEpochDay(2024 - 12 - 12));
        rate.setAmount(4567);
        when(rateRepository.save(Mockito.any(Rate.class))).thenReturn(rate);
        String newRate = rateService.saveRate(rate);
        System.out.println(newRate);
        Assertions.assertNotNull(newRate);
        Assertions.assertEquals(3L, rate.getRateId());

    }

//    @Test
//    public void getRateByID_success() throws ParseException, InterruptedException  {
//        Rate rate = new Rate();
//        rate.setRateId(3L);
//        rate.setRateDesc("Done");
//        rate.setRateEffDate(LocalDate.ofEpochDay(2020 - 12 - 12));
//        rate.setRateExpDate(LocalDate.ofEpochDay(2024 - 12 - 12));
//        rate.setAmount(4567);
//        Optional<Rate> rateOptional = Optional.of(rate);
//        rateRepository.save(rate);
//        when(rateRepository.findById(Mockito.anyLong())).thenReturn(rateOptional);
//        Rate newRate = (Rate) rateService.findById(3L);
//        System.out.println(newRate);
//        verify(rateRepository,times(1)).findById(anyLong());
//        Assertions.assertNotNull(newRate);
//        Assertions.assertEquals(4567, rate.getAmount());
//
//    }

    @Test
    public void deleteRateByID_success() throws Exception {
        Rate rate = new Rate();
        rate.setRateId(3L);
        rate.setRateDesc("Done");
        rate.setRateEffDate(LocalDate.ofEpochDay(2020 - 12 - 12));
        rate.setRateExpDate(LocalDate.ofEpochDay(2024 - 12 - 12));
        rate.setAmount(4567);
        rateRepository.deleteById(3L);
        verify(rateRepository,times(1)).deleteById(anyLong());
    }

//    @Test
//    public void updateRate_success() {
//        Rate rate = new Rate();
//        rate.setRateId(3L);
//        rate.setRateDesc("Done");
//        rate.setRateEffDate(LocalDate.ofEpochDay(2020 - 12 - 12));
//        rate.setRateExpDate(LocalDate.ofEpochDay(2024 - 12 - 12));
//        rate.setAmount(4567);
//        Optional<Rate> rateOptional = Optional.of(rate);
//        when(rateRepository.findById(Mockito.anyLong())).thenReturn(rateOptional);
//     rate.setAmount(1234);
//         String newRate = rateService.updateRate(3l,rate);
//        System.out.println(newRate);
//        Assertions.assertNotNull(newRate);
//        Assertions.assertEquals(3L, rate.getRateId());
//
//    }

}