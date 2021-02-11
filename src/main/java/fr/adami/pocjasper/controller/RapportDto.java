package fr.adami.pocjasper.controller;

import java.util.Map;

import lombok.Data;

@Data
public class RapportDto {

	private String name;
	private Map<String,Object> params;
	
	private String format;
}
