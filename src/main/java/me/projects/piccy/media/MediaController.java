package me.projects.piccy.media;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.UUID;

@Controller
@RequestMapping(path = "/media")
public class MediaController {

    @Autowired
    private MediaService mediaService;

    @GetMapping("/{mediaId}")
    ResponseEntity<byte[]> getMedia(@PathVariable UUID mediaId) {
        try {
            byte[] fileData = mediaService.retrieveFile(mediaId);
            return ResponseEntity
                    .accepted()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(fileData);

        } catch (FileNotFoundException e) {
            return ResponseEntity.notFound().build();

        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

}
