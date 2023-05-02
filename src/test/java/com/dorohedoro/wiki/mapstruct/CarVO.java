package com.dorohedoro.wiki.mapstruct;

import lombok.Data;

@Data
public class CarVO {
    
    private Long id;
    
    private String vin;

    private Double price;
    
    private String totalPrice;
    
    private String publishDate;
    
    private String brandName;

    private String partIds;

    private DriverVO driver;
}
