package com.example.test.rate.repository;

import com.example.test.rate.model.Rate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;


import java.time.LocalDate;


@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RateRepositoryTest {

    @Autowired
    private RateRepository rateRepository;

    @Test
    @Order(1)
    @Rollback(value = false)
    public void saveRateTestSuccess(){

        Rate rate = Rate.builder()
                .rateId(1L)
                .rateDesc("Decrease")
                .rateEffDate(LocalDate.ofEpochDay(2024 - 12 - 12))
                .rateExpDate(LocalDate.ofEpochDay(2024 - 12 - 12))
                .amount(5678)
                .build();

        rateRepository.save(rate);

        Assertions.assertThat(rate.getRateId()).isGreaterThan(0);
    }
    @Test
    @Order(2)
    public void getRateByIdTestSuccess(){

        Rate rate = rateRepository.findById(1L).get();
        Assertions.assertThat(rate.getRateId()).isEqualTo(1L);

    }

    @Test
    @Order(3)
    @Rollback(value = false)
    public void updateEmployeeTest(){

        Rate rates = rateRepository.findById(1L).get();
        rates.setRateDesc("Done");

        Rate rateUpdated =  rateRepository.save(rates);
        Assertions.assertThat(rateUpdated.getRateId()).isEqualTo(1L);

    }
    @Test
    @Order(4)
    @Rollback(value = false)
    public void deleteRateTest(){

        Rate rate = rateRepository.findById(1L).get();

        rateRepository.deleteById(1L);

        Rate rate1 = null;

        Optional<Rate> optionalRate = rateRepository.findById(1L);

        if(optionalRate.isPresent()){
            rate1 = optionalRate.get();
        }

        Assertions.assertThat(rate1).isNull();
    }


}