package nosql.image_service.service;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.model.GridFSUploadOptions;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class ImageService {

    private final GridFSBucket gridFSBucket;

    //cria um "bucket" GridFS (local onde os arquivos ficam no MongoDB)
    @Autowired
    public ImageService(MongoDatabaseFactory mongoDatabaseFactory) {
        this.gridFSBucket = GridFSBuckets.create(mongoDatabaseFactory.getMongoDatabase());
    }

    //Upload da imagem para o MongoDB
    public String uploadImage(MultipartFile file) throws IOException {

        //Define metadados do arquivo (tipo MIME, ex: image/png, image/jpeg, etc.)
        GridFSUploadOptions options = new GridFSUploadOptions()
                .metadata(new org.bson.Document("contentType", file.getContentType()));

        //Lê os bytes do arquivo e salva no GridFS
        ObjectId fileId;

        try (var stream = file.getInputStream()) {
            fileId = gridFSBucket.uploadFromStream(file.getOriginalFilename(), stream, options);
        }

        //Retorna o ID do arquivo salvo como String
        return fileId.toHexString();
    }

    //Download da imagem para o MongoDB
    public byte[] downloadImage(String id) throws IOException{

        //Converte String para ObjectId
        var fileId = new ObjectId(id);
        var outputStream = new ByteArrayOutputStream();

        //Recupera os chunks do arquivo do GridFS e junta no outputStream
        gridFSBucket.downloadToStream(fileId, outputStream);

        //Retorna os bytes da imagem
        return outputStream.toByteArray();
    }

    //Buscar o contentType da imagem nos metadados
    public String getContentType(String id) {

        var fileId = new ObjectId(id);
        var file = gridFSBucket.find(new Document("_id", fileId)).first();

        if (file != null && file.getMetadata() != null) {
            return file.getMetadata().getString("contentType");
        }
        return "application/octet-stream"; // fallback genérico
    }
}
