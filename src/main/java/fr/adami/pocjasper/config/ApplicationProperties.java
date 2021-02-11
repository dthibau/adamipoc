package fr.adami.pocjasper.config;

import javax.validation.constraints.Pattern;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import lombok.Data;

@Component
@ConfigurationProperties("app")
@Data
@Validated
public class ApplicationProperties {

	
	@Pattern(regexp=".*/")
	private String reportFolder;
	

}
