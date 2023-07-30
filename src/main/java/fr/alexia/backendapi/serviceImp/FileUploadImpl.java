package fr.alexia.backendapi.serviceImp;

import com.cloudinary.Cloudinary;

import fr.alexia.backendapi.service.FileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileUploadImpl implements FileUploadService {

    private final Cloudinary cloudinary;

    /**
     * Upload a file to Cloudinary.
     *
     * @param multipartFile The file to be uploaded.
     * @return String The URL of the uploaded file.
     * @throws IOException If an I/O exception occurs during file upload.
     */
    @Override
    public String uploadFile(MultipartFile multipartFile) throws IOException {
        return cloudinary.uploader() // Upload the file to Cloudinary using the Cloudinary uploader
                // The uploader().upload() method takes the file's bytes and the public ID as
                // parameters
                .upload(multipartFile.getBytes(),
                        // It uploads the file to the Cloudinary service with the specified public ID.
                        Map.of("public_id", UUID.randomUUID().toString()))
                .get("url") // Get the URL of the uploaded file from the response
                .toString();
    }
}
