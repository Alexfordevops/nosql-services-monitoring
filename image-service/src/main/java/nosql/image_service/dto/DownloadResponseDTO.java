package nosql.image_service.dto;

public class DownloadResponseDTO {
    private String id;
    private String fileName;
    private String contentType;
    private long size;

    public DownloadResponseDTO(String id, String fileName, String contentType, long size) {
        this.id = id;
        this.fileName = fileName;
        this.contentType = contentType;
        this.size = size;
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
    public long getSize() {
        return size;
    }
}

