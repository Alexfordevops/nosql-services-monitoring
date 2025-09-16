package nosql.image_service.dto;

public class UploadResponseDTO {
    private String id;
    private String fileName;
    private String contentType;

    public UploadResponseDTO(String id, String fileName, String contentType) {
        this.id = id;
        this.fileName = fileName;
        this.contentType = contentType;
    }

    public String getId() {
        return id;
    }
    public String getFileName() {
        return fileName;
    }
    public String getContentType() {
        return contentType;
    }
}

