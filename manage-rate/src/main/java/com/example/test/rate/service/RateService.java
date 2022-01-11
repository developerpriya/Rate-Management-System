package com.example.test.rate.service;

import com.example.test.rate.VO.RestTemplateVO;
import com.example.test.rate.controller.RateController;
import com.example.test.rate.exception.CustomExceptionHandler;
import com.example.test.rate.exception.RateNotFound;
import com.example.test.rate.model.Rate;
import com.example.test.rate.repository.RateRepository;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.json.simple.parser.ParseException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class RateService {

    @Autowired
    private RateRepository rateRepository;

    @Autowired
    private RestTemplate restTemplate;

    private static final Logger LOGGER = LoggerFactory.getLogger(RateService.class);

//    @Value("${surcharge.url}")
//    private String url;

    public List<Rate> getRate() {
        return rateRepository.findAll();
    }

    public String saveRate(Rate rate) {
        rateRepository.save(rate);
        LOGGER.info("Service Layer : New Rate details are added to RMS");
        return "New Rate details are added to RMS service layer";
    }

    public Object findById(Long rateId) throws RateNotFound, ParseException {

            String surcharge = restTemplate
                    .getForObject("https://adfolks.free.beeceptor.com/charge", String.class);
      
            JSONObject object = (JSONObject) new JSONParser().parse(surcharge);
            String status = (String) object.get("status");
            JSONObject jsonObject2 = (JSONObject) object.get("data");
            Long surcharges = (Long) jsonObject2.get("surcharge");
            Long tax = (Long) jsonObject2.get("tax");

        Optional<Rate> rate =
                rateRepository.findById(rateId);
        if (!rate.isPresent()) {
            LOGGER.error("Service Layer: RateId not found in RMS");
            throw new RateNotFound("RateId not found in RMS");
        }
        RestTemplateVO rates = new RestTemplateVO();
        rates.setSurcharge(surcharges);
        rates.setTax(tax);
        rates.setStatus(status);
        rates.setRateId(rateId);
        rates.setRateExpDate(rate.get().rateEffDate);
        rates.setRateEffDate(rate.get().rateEffDate);
        rates.setAmount(rate.get().amount);
        rates.setRateDesc(rate.get().rateDesc);
        LOGGER.info("Service Layer:Retrieve rate details from RMS by Rate ID");
        return rates;
    }

    public String deleteRateById(Long rateId) throws RateNotFound {
        Optional<Rate> rate =
                rateRepository.findById(rateId);
        if (!rate.isPresent()) {
            LOGGER.error("Service Layer Delete:RateId not found in RMS");
            throw new RateNotFound("RateId not found in RMS");
        }
        rateRepository.deleteById(rateId);
        LOGGER.info("Service Layer:Rate details deleted successfully for Rate ID: "+rateId);
        return "Rate details deleted successfully for Rate ID service layer: " + rateId;
    }

    @Async("updateExecutor")
    public CompletableFuture<String> updateRate(Long rateId, Rate rate) throws RateNotFound {
        System.out.println("Asnc thread " + Thread.currentThread().getName());
        Optional<Rate> rateDb = rateRepository.findById(rateId);
        if (!rateDb.isPresent()) {
            System.out.println("Enter");
            LOGGER.error("RateId not found in RMS");
            throw new RateNotFound("RateId not found in RMS");
        }
        else
        {
            Rate newRate = rateDb.get();
            if (Objects.nonNull(rate.getRateDesc()) && !"".equalsIgnoreCase(rate.getRateDesc())) {
                newRate.setRateDesc(rate.getRateDesc());
            }

            if (Objects.nonNull(rate.getAmount())) {
                newRate.setAmount(rate.getAmount());
            }

            if (Objects.nonNull(rate.getRateEffDate())) {
                newRate.setRateEffDate(rate.getRateEffDate());
            }
            if (Objects.nonNull(rate.getRateExpDate())) {
                newRate.setRateExpDate(rate.getRateExpDate());
            }
            System.out.println("Enter");
            LOGGER.info("Service Layer:Rate details updated successfully for Rate ID: "+rateId);
            rateRepository.save(newRate);
        }
        return CompletableFuture.completedFuture("Rate values are updated for Id: " + rateId);
    }
}



