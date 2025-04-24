package me.projects.piccy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@SpringBootApplication
public class PiccyApplication {

	public static void main(String[] args) {
		System.setProperty("org.sqlite.tmpdir","sqlite");
        try {
            Files.createDirectories(Path.of("sqlite"));
			File dbFile = Path.of("sqlite","data.db")
					.toFile();
			if (!dbFile.exists() && !dbFile.createNewFile())
				throw new IOException("Couldn't create config database");
			Files.createDirectories(Path.of("media"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        SpringApplication.run(PiccyApplication.class, args);
	}

}
