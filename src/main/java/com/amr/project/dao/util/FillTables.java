package com.amr.project.dao.util;

import com.amr.project.model.entity.City;
import com.amr.project.model.entity.Country;
import com.amr.project.service.abstracts.ReadWriteService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class FillTables {

    private final ReadWriteService<Country, Long> countryService;
    private final ReadWriteService<City, Long> cityService;

    public FillTables(ReadWriteService countryService, ReadWriteService cityService) {
        this.countryService = countryService;
        this.cityService = cityService;
    }

    @PostConstruct
    void fillCountries() {
        Country russia = new Country();
        russia.setName("Russia");
//        russia.setCities(Arrays.asList(new City()));
        countryService.persist(russia);

        Country germany = new Country();
        germany.setName("Germany");
//        germany.setCities(Arrays.asList(new City()));
        countryService.persist(germany);

        Country usa = new Country();
        usa.setName("USA");
//        usa.setCities(Arrays.asList(new City()));
        countryService.persist(usa);


        System.out.println(countryService.getAll());
    }
}
