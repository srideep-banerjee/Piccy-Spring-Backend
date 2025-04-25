package me.projects.piccy.media;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Path;
import java.util.UUID;

@Service
public class MediaService {

    public UUID saveFile(MultipartFile inputFile) throws IOException {

        String fileName = inputFile.getOriginalFilename();
        assert fileName != null;

        UUID uuid;
        File f;
        do {
            uuid = UUID.randomUUID();
            f = getFileFromUUID(uuid);
        } while (f.exists());

        if (!f.createNewFile()) throw new IOException("Couldn't create File");

        inputFile.transferTo(f);

        return uuid;
    }

    byte[] retrieveFile(UUID uuid) throws FileNotFoundException {

        File requestedFile = getFileFromUUID(uuid);

        try (FileInputStream fis = new FileInputStream(requestedFile)) {
            return fis.readAllBytes();

        } catch (FileNotFoundException ex) {
            throw ex;

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private File getFileFromUUID(UUID uuid) {
        return Path.of("media", "pic-" + uuid + ".jpeg").toAbsolutePath().toFile();
    }
}
