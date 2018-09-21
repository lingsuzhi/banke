package com.lsz;

import com.lsz.service.SaveFacesService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

@SpringBootApplication
public class BankedoApplication {

	public static void main(String[] args) {
		if (File.separator.equals("\\")) {
			SaveFacesService.FileDirP = "d:\\post";
		}

		SpringApplication.run(BankedoApplication.class, args);
	}
}
