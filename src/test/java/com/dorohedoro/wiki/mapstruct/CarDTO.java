package com.dorohedoro.wiki.mapstruct;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class CarDTO {
    
    private Long id;
    
    private String vin;
    
    private double price;
    
    private double totalPrice;
    
    private Date publishDate;
    
    private String brand;
    
    private List<PartDTO> parts;

    private DriverDTO driver;
}
