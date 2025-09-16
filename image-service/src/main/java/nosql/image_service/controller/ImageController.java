package nosql.image_service.controller;

import nosql.image_service.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/image")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @PostMapping("/uploadImage")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) throws IOException{

        // Chama o service para salvar a imagem
        String fileId = imageService.uploadImage(file);

        // Retorna o ID para o cliente (Angular usará para download depois)
        return ResponseEntity.status(HttpStatus.OK).body(fileId);
    }

    @GetMapping("/downloadImage/{id}")
    public ResponseEntity<byte[]> download(@PathVariable String id) throws IOException {

        // Recupera os bytes da imagem
        byte[] data = imageService.downloadImage(id);

        // Recupera o tipo MIME original dos metadados
        String contentType = imageService.getContentType(id);

        // Retorna a resposta HTTP com cabeçalhos corretos
        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"image\"")
                .contentType(MediaType.parseMediaType(contentType))
                .body(data);
    }
}
