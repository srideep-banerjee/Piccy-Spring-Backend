package me.projects.piccy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@EnableCaching
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
			cleanupRedundantBinaries();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        SpringApplication.run(PiccyApplication.class, args);
	}

	private static void cleanupRedundantBinaries() {
		File file = new File("sqlite");
		File[] lockFiles = file.listFiles((dir, name) -> name.endsWith(".lck"));
        assert lockFiles != null;
		boolean first = true;

        for (File lockFile: lockFiles) {
			if (lockFile.delete()) {
				if (first) {
					first = false;
					continue;
				}

				String path = lockFile.getPath();
				path = path.substring(0, path.lastIndexOf(".lck"));
				File binaryFile = new File(path);
				binaryFile.delete();
			}
		}
	}
}
