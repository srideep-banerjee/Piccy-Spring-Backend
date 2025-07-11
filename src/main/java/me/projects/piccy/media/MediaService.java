package me.projects.piccy.media;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Path;
import java.util.UUID;

@Service
public class MediaService {

    @Value("${media.maxsize}")
    private Long maxSize;

    public UUID saveFile(MultipartFile inputFile) throws IOException, MediaException {

        String fileName = inputFile.getOriginalFilename();
        assert fileName != null;

        if (inputFile.getSize() > maxSize) {
            throw new MediaException("File size can't be greater than 5MB");
        }

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

    public static String getUrlFromUUID(UUID mediaId) {
        return "/media/" + mediaId;
    }

    public static UUID getUUIDFromUrl(String url) {
        return UUID.fromString(url.substring(7));
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

    public boolean deleteFile(UUID uuid) {
        return getFileFromUUID(uuid).delete();
    }

    private File getFileFromUUID(UUID uuid) {
        return Path.of("media", "pic-" + uuid + ".jpeg").toAbsolutePath().toFile();
    }
}
