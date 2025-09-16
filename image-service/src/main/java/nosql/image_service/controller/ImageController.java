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
import java.util.List;

@RestController
@RequestMapping("/image")
public class ImageController {

    @Autowired
    private ImageService imageService;

    //Upload de imagem
    @PostMapping("/uploadImage")
    public ResponseEntity<UploadResponseDTO> uploadImage(@RequestParam("file") MultipartFile file) throws IOException {

        // Chama o service para salvar a imagem
        UploadResponseDTO fileId = imageService.uploadImage(file);

        // Retorna o ID para o cliente (Angular usará para download depois)
        return ResponseEntity.status(HttpStatus.OK).body(fileId);
    }

    //Download de imagem
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

    //Retorna os metadados de uma imagem
    @GetMapping("/{id}/info")
    public ResponseEntity<DownloadResponseDTO> getFileInfo(@PathVariable String id) {

        //Obtém os metadados da imagem selecionada
        DownloadResponseDTO info = imageService.getFileInfo(id);

        //Retorno condicional
        return info == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(info);
    }

    //Retorna os metadados de todas as imagens
    @GetMapping("/listImages")
    public ResponseEntity<List<DownloadResponseDTO>> listAllFiles() {
        return ResponseEntity.ok(imageService.listAllFiles());
    }
}
