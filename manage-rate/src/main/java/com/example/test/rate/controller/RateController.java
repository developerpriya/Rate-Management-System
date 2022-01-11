package com.example.test.rate.controller;

import com.example.test.rate.exception.RateNotFound;
import com.example.test.rate.model.JwtRequest;
import com.example.test.rate.model.JwtResponse;
import com.example.test.rate.model.Rate;
import com.example.test.rate.service.RateService;
import com.example.test.rate.service.UserService;
import com.example.test.rate.utility.JWTUtility;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.security.Principal;
import java.util.List;
import java.util.concurrent.CompletableFuture;


//@CrossOrigin(origins = "http://localhost:4200")
@CrossOrigin("*")
@RestController
//@RequestMapping("/api/v1")
@Slf4j
public class RateController {

    @Autowired
    private RateService rateService;

    @Autowired
    private JWTUtility jwtUtility;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    private static final Logger LOGGER = LoggerFactory.getLogger(RateController.class);

//    @GetMapping
//    public String welcomeMessage(Principal principal) {
//        String success = "Login Success: welcome " + principal.getName() + "" + " to Rate Management System !";
//        return success;

    @RequestMapping("/")
//    @ResponseBody
//    public String home(@AuthenticationPrincipal OidcUser oidcUser) {
//        return "Welcome, " + oidcUser.getFullName();
//    }
    @ResponseBody
    public String home() {
        return "Welcome to Rate MAnagement System";
    }

    @CrossOrigin
    @GetMapping("/rates")
    public List<Rate> findAllRates()
    {
        LOGGER.info("Get all the Rate details in RMS");
        return   rateService.getRate();
    }

  //  @CrossOrigin
    @PostMapping("/authenticate")
    public String authenticate(@RequestBody JwtRequest jwtRequest) throws Exception{
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            jwtRequest.getUsername(),
                            jwtRequest.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }

        final UserDetails userDetails
                = userService.loadUserByUsername(jwtRequest.getUsername());

        final String token =
                jwtUtility.generateToken(userDetails);
        System.out.println("token:"+token);
   //     return  new JwtResponse(token);
        return  token;

    }
//    }

    @PostMapping("/rates")
    @HystrixCommand(fallbackMethod = "handleSaveDownTime",commandProperties = {
          @HystrixProperty(name ="execution.isolation.thread.timeoutInMilliseconds", value = "5000")}
            ,ignoreExceptions={Exception.class})
    public ResponseEntity<String> saveRate(@NotNull @RequestBody Rate rate) throws InterruptedException {
//        Thread.sleep(2000);
        rateService.saveRate(rate);
        LOGGER.info("Inside Controller:New Rate details are added to RMS");
        return ResponseEntity.status(HttpStatus.OK).body("New Rate details are added to RMS");
    }
    public ResponseEntity<String> handleSaveDownTime(@NotNull @RequestBody Rate rate){
        LOGGER.error("Hystrix timeout error");
        return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT)
                .body("DB INSTANCE DOWN OR API ENDPOINTS TIME OUT,TRY AFTER SOMETIME");
    }

    @GetMapping("/rates/{rateId}")
    @HystrixCommand(fallbackMethod = "handleGetDownTime",commandProperties = {
            @HystrixProperty(name ="execution.isolation.thread.timeoutInMilliseconds", value = "5000")
    },ignoreExceptions={RateNotFound.class})
    public ResponseEntity<Object> findById(@PathVariable("rateId") Long rateId) throws RateNotFound, ParseException {
        rateService.findById(rateId);
        LOGGER.info("Inside Controller::Retrieve rate details from RMS by Rate ID");
        return ResponseEntity.status(HttpStatus.OK).body(rateService.findById(rateId));
    }
    public ResponseEntity<Object> handleGetDownTime(@PathVariable("rateId") Long rateId) throws RateNotFound, ParseException {
        LOGGER.error("Inside get controller Hystrix timeout error");
        return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT)
                .body("DB INSTANCE DOWN OR API ENDPOINTS TIME OUT OR DOWN,TRY AFTER SOMETIME");
    }

    @DeleteMapping("/rates/{rateId}")
    @HystrixCommand(fallbackMethod = "handleDeleteDownTime",commandProperties = {
            @HystrixProperty(name ="execution.isolation.thread.timeoutInMilliseconds", value = "5000")
    },ignoreExceptions={RateNotFound.class})
    public ResponseEntity<String> deleteRateById(@PathVariable("rateId") Long rateId) throws RateNotFound {
        rateService.deleteRateById(rateId);
        LOGGER.info("Inside controller: Rate details deleted successfully for Rate ID: "+rateId);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Rate details deleted successfully for Rate ID: "+rateId);
    }
    public ResponseEntity<String> handleDeleteDownTime(@PathVariable("rateId") Long rateId) throws RateNotFound {
        System.out.println("Fault delete");
        LOGGER.error("Hystrix timeout error in delete method");
        return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT)
                .body("DB INSTANCE DOWN OR API ENDPOINTS TIME OUT OR DOWN,TRY AFTER SOMETIME");
    }

    @PutMapping("/rates/{rateId}")
//    @HystrixCommand(fallbackMethod = "handleUpdateDownTime",commandProperties = {
//            @HystrixProperty(name ="execution.isolation.thread.timeoutInMilliseconds", value = "5000")
//    },ignoreExceptions={RateNotFound.class,Exception.class}})
    public CompletableFuture<String> updateRate(@PathVariable("rateId") Long rateId,
                                                  @RequestBody Rate rate) throws RateNotFound{
        LOGGER.info("Inside controller: Rate details updated successfully for Rate ID: "+rateId);
        return rateService.updateRate(rateId, rate).thenApplyAsync(user -> {
            return "Rate details updated successfully for Rate ID: "+rateId;
        });
    }
//    public String handleUpdateDownTime(@PathVariable("rateId") Long rateId,
//                                       @RequestBody Rate rate) throws RateNotFound{
//        LOGGER.error("Hystrix timeout error in update method");
////        return rateService.updateRate(rateId, rate).thenApplyAsync(user -> {
////            return"DB INSTANCE DOWN OR API ENDPOINTS TIME OUT OR DOWN,TRY AFTER SOMETIME";
//        return "DB INSTANCE DOWN OR API ENDPOINTS TIME OUT OR DOWN,TRY AFTER SOMETIME";
//        }
    }


