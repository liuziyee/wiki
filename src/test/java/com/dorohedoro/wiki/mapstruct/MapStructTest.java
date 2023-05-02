package com.dorohedoro.wiki.mapstruct;

import com.dorohedoro.wiki.util.IDGenerator;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Date;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class MapStructTest {

    @Autowired
    private CarMapper carMapper;

    @Test
    public void dto2VO() {
        log.info("{}", carMapper.dto2VO(buildCarDTO()));
    }
    
    public CarDTO buildCarDTO() {
        CarDTO carDTO = new CarDTO();
        carDTO.setId(IDGenerator.nextId());
        carDTO.setVin("vin" + IDGenerator.nextId());
        carDTO.setPrice(1111.111D);
        carDTO.setTotalPrice(11111.111D);
        carDTO.setPublishDate(new Date());
        carDTO.setBrand("红旗");

        PartDTO door = new PartDTO();
        door.setId(IDGenerator.nextId());
        door.setName("车门");
        PartDTO wheel = new PartDTO();
        wheel.setId(IDGenerator.nextId());
        wheel.setName("方向盘");
        carDTO.setParts(Arrays.asList(door, wheel));

        DriverDTO driver = new DriverDTO();
        driver.setId(IDGenerator.nextId());
        driver.setName("司机");
        carDTO.setDriver(driver);
        return carDTO;
    }
}
