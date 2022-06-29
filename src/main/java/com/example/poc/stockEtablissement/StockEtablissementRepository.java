package com.example.poc.stockEtablissement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockEtablissementRepository extends JpaRepository<StockEtablissement, Long> {

}
