package com.receitas_da_vovo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Phelipe
 * @version 1.0.0
 */
@SpringBootApplication
public class ReceitasDaVovoApplication {

	/**
	 * Método reponsável por rodar a aplicação
	 * 
	 * @param args recebe um array de string
	 */
	public static void main(String[] args) {
		SpringApplication.run(ReceitasDaVovoApplication.class, args);
	}

}
