package fr.adami.pocjasper.config;

import lombok.Data;

@Data
public class JdbcConfig {

	private String url;
	private String username;
	private String password;
}
