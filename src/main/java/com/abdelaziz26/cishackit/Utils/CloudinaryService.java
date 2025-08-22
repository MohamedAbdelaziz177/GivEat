package com.abdelaziz26.cishackit.Utils;

import com.cloudinary.Cloudinary;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CloudinaryService {

    private final Cloudinary cloudinary;

    private static final String[] VALID_EXTENSIONS = {"jpg", "jpeg", "png", "gif"};

    public String uploadFile(MultipartFile file) throws IOException {

        if (file.isEmpty())
            throw new IOException("File is empty");

        if(file.getOriginalFilename() == null || file.getOriginalFilename().isEmpty())
            throw new IOException("Filename is empty");

        if(file.getOriginalFilename().contains("..") ||
                file.getOriginalFilename().contains("/")
                || file.getOriginalFilename().contains("\\"))

            throw new IOException("Filename contains invalid characters");

        String extension =
                file.getOriginalFilename()
                        .substring(file.getOriginalFilename().lastIndexOf(".") + 1);

        if(!Arrays.asList(VALID_EXTENSIONS).contains(extension))
            throw new IOException("Filename has invalid extension only {jpg, png, jpeg} are allowed");

        return cloudinary.uploader().upload(file.getBytes(),
                Map.of(
                        "folder", "CisHackIt",
                        "resource_type", "auto",
                        "public_id", file.getOriginalFilename(),
                        "overwrite", true
                )
        ).get("url").toString();
    }

    public void deleteFile(String publicId) throws IOException {
        cloudinary.uploader().destroy(publicId, Map.of());
    }
}
