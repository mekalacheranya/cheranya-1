package com.oosd.vstudent.repositories;

import com.oosd.vstudent.models.CarPool;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CarPoolRepository extends JpaRepository<CarPool, Integer> {
}
