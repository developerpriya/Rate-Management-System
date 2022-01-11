package com.example.test.rate.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.tomcat.jni.Local;

import javax.validation.constraints.NotNull;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="rates")
public class Rate {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    public Long rateId;
    public String rateDesc;
    @NotNull(message = "Effective Date must not be empty")
    public LocalDate rateEffDate;
    @NotNull(message = "Expiration Date must not be empty")
    public LocalDate rateExpDate;
    @NotNull(message = "Amount must not be empty")
    public Integer amount;


}
