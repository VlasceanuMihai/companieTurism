package com.CompanieTurism.repository;

import com.CompanieTurism.models.Destination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DestinatieRepository extends JpaRepository<Destination, Integer> {
}
