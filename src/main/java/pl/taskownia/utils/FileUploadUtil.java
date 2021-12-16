package pl.taskownia.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Component
public class FileUploadUtil {
    @Value("${app.file-uploads-path}")
    private String fileUploadTemp;

    private static String fileUploadsPath;

    @Value("${app.file-uploads-path}")
    public void setFileUploadsPath(String fileUploadTemp) {
        FileUploadUtil.fileUploadsPath = fileUploadTemp; //TODO not executing
    }

    public static Boolean uploadFile(String filename, MultipartFile multipartFile) {
        Path path = Paths.get(fileUploadsPath + '/');

        try {
            if (!Files.exists(path))
                Files.createDirectories(path);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = path.resolve(filename);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
