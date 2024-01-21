package project.shopbackend.utils;

import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class FileUtil {
    @Value("${upload-path}")
    private String uploadDirectory;

    public String generateUniqueFileName(MultipartFile file){
        // Lấy ra file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        // Tạo ra tên file duy nhất
        return UUID.randomUUID().toString() + "_" + fileName;
    }

    public String uploadSingleFile(MultipartFile file) throws IOException {
        if(isFileValid(file)){
            String uniqueFileName = generateUniqueFileName(file);
            Path uploadDir = Paths.get(uploadDirectory);
            // Kiểm tra và tạo thư mục nếu nó không tồn tại
            if(!Files.exists(uploadDir)){
                Files.createDirectories(uploadDir);
            }
            // Đường dẫn đầy đủ đến file
            Path destination = Paths.get(uploadDir.toString(),uniqueFileName);

            // Sao chép file vào thư mục đích
            Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
            return uniqueFileName;
        }
        else throw new FileUploadException("File is too large! Maximum size is 10MB or File must be an image");
    }

    public List<String> uploadMultiFile(List<MultipartFile> files) throws IOException{
        if(isMultiFileValid(files)){
            Path uploadDir = Paths.get(uploadDirectory);
            // Kiểm tra và tạo thư mục nếu nó không tồn tại
            if(!Files.exists(uploadDir)){
                Files.createDirectories(uploadDir);
            }
            List<String> images = new ArrayList<>();
            for(MultipartFile file : files){
                String uniqueFileName = generateUniqueFileName(file);
                // Đường dẫn đầy đủ đến file
                Path destination = Paths.get(uploadDir.toString(),uniqueFileName);

                // Sao chép file vào thư mục đích
                Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
                images.add(uniqueFileName);
            }
            return images;
        }
        else throw new FileUploadException("File is too large! Maximum size is 10MB or File must be an image");
    }
    private boolean isFileValid(MultipartFile file){
        if(file != null){
            if(file.getSize() > 10 *1024 *1024) // Kích thước lớn hơn 10mb
                return false;
            String contenType = file.getContentType();
            if(contenType == null || !contenType.startsWith("image/")) // Không phải file ảnh
                return false;
        }
        return true;
    }

    private boolean isMultiFileValid(List<MultipartFile> files){
        for(MultipartFile file : files){
            if(file.getSize() > 10 *1024 *1024) // Kích thước lớn hơn 10mb
                return false;
            String contenType = file.getContentType();
            if(contenType == null || !contenType.startsWith("image/")) // Không phải file ảnh
                return false;
        }
        return true;
    }

    public void deleteFile(String fileName){
        File file = new File(uploadDirectory + "/"+ fileName);
        if(file.exists()){
            boolean deleted = file.delete();
            if(!deleted) throw new RuntimeException("Delete images error!");
        }
        else throw new RuntimeException("File does not exist.");

    }
}
