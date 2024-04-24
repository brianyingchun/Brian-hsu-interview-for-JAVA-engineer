package com.coindesk.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class CoindeskCoins {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String cname;
    private String chzcname;

    // Constructors, getters, and setters
    public CoindeskCoins() {
    }

    public CoindeskCoins(String cName, String chzCName) {
        this.cname = cName;
        this.chzcname = chzCName;
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getChzcname() {
		return chzcname;
	}

	public void setChzcname(String chzcname) {
		this.chzcname = chzcname;
	}

	
}
