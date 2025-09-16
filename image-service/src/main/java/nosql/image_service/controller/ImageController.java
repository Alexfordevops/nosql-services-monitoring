package nosql.image_service.controller;

import nosql.image_service.dto.DownloadResponseDTO;
import nosql.image_service.dto.UploadResponseDTO;
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
    public ResponseEntity<UploadResponseDTO> uploadImage(@RequestParam("file") MultipartFile file) throws IOException{

        // Chama o service para salvar a imagem
        UploadResponseDTO fileId = imageService.uploadImage(file);

        // Retorna o ID para o cliente (Angular usará para download depois)
        return ResponseEntity.status(HttpStatus.OK).body(fileId);
    }

    @GetMapping("/downloadImage/{id}")
    public ResponseEntity<byte[]> downloadImage(@PathVariable String id) throws IOException {

        //Obtém a imagem
        byte[] data = imageService.downloadImage(id);

        //Obtém os metadados da imagem
        DownloadResponseDTO info = imageService.getFileInfo(id);
        if (info == null) {
            return ResponseEntity.notFound().build();
        }

        //Retorna a imagem com os metadados
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + info.getFileName() + "\"")
                .contentType(MediaType.parseMediaType(info.getContentType()))
                .body(data);
    }

    //Retorna apenas os metadados do arquivo
    @GetMapping("/{id}/info")
    public ResponseEntity<DownloadResponseDTO> getFileInfo(@PathVariable String id) {

        DownloadResponseDTO info = imageService.getFileInfo(id);
        if (info == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(info);
    }
}
