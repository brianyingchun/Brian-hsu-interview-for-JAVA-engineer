package com.coindesk.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoindeskCoinsRepository extends JpaRepository<CoindeskCoins, Integer>{
	   CoindeskCoins findByCname(String cName);
	   CoindeskCoins findByChzcname(String chzCName);
}
