package wepik.backend.module.file.dao;

public enum FileType {
    JPEG("image/jpeg", "jpeg"),
    PNG("image/png", "png");

    private final String mimeType;
    private final String fileExtension;

    FileType(String mimeType, String fileExtension) {
        this.mimeType = mimeType;
        this.fileExtension = fileExtension;
    }

    public String getMimeType() {
        return mimeType;
    }
}