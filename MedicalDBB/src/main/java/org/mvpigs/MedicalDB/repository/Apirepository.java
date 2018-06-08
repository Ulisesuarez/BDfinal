package org.mvpigs.MedicalDB.repository;

import java.util.Optional;
import org.mvpigs.MedicalDB.controller.ApiData;

import org.mvpigs.MedicalDB.domain.File;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Apirepository {
	private static final Logger LOG = LoggerFactory.getLogger(ApiData.class);
	
	private String result;
	private ApiData apidata= new ApiData();
	
	public Apirepository(String uri){
	this.setResult(apidata.get(uri));
	LOG.info("desdeRepository-------------------------------------------------------------------------");
	LOG.info(this.result);
	}

	public String getResult() {
		return this.result;
	}






	public void setResult(String result) {
		this.result = result;
	}

}
