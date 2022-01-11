package com.example.test.rate.VO;

import com.example.test.rate.model.Rate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestTemplateVO {
    public Long rateId;
    public String rateDesc;
    public LocalDate rateEffDate;
    public LocalDate rateExpDate;
    public Integer amount;
//    private Rate rate;
    public String status;
    public Long surcharge;
    public Long tax;


}
