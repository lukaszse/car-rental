package org.lukaszse.carRental.repository;

import org.lukaszse.carRental.model.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarSearchRepository extends JpaRepository<Car, Integer> {

    @Query(value = "select * from Car c where c.manufacturer ilike %:manufacturer% and c.model ilike %:model%",
    nativeQuery = true)
    List<Car> findCars(@Param("manufacturer") final String manufacturer, @Param("model") final String model);
}
