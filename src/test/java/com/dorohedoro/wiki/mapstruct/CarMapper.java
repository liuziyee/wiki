package com.dorohedoro.wiki.mapstruct;

import org.mapstruct.*;

import static java.util.stream.Collectors.joining;

@Mapper(componentModel = "spring")
public interface CarMapper extends BaseMapper<CarDTO, CarVO> {

    @Override
    @Mapping(source = "totalPrice", target = "totalPrice", numberFormat = "#.00")
    @Mapping(source = "publishDate", target = "publishDate", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @Mapping(source = "brand", target = "brandName")
    @Mapping(source = "driver", target = "driver")
    @Mapping(target = "price", ignore = true)
    CarVO dto2VO(CarDTO carDTO);

    @Mapping(source = "id", target = "driverId")
    @Mapping(source = "name", target = "driverName")
    DriverVO driverDTO2DriverVO(DriverDTO driverDTO);

    @AfterMapping // 钩子函数
    default void setPartIds(CarDTO carDTO, @MappingTarget CarVO carVO) {
        carVO.setPartIds(carDTO.getParts().stream().map(o -> o.getId().toString()).collect(joining(",")));
    }
}
