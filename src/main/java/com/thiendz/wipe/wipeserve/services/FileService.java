package com.thiendz.wipe.wipeserve.services;

import com.thiendz.wipe.wipeserve.configs.FileUploadConfig;
import com.thiendz.wipe.wipeserve.dto.response.FileUploadResponse;
import com.thiendz.wipe.wipeserve.dto.response.PageResponse;
import com.thiendz.wipe.wipeserve.utils.constant.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class FileService {

    private final Path fileLocation;

    @Autowired
    public FileService(FileUploadConfig fileUploadConfig) throws IOException {
        fileLocation = Paths.get(fileUploadConfig.getUploadDir()).toAbsolutePath().normalize();
        if (Files.notExists(fileLocation)) {
            Files.createDirectory(fileLocation);
        }
    }


    public FileUploadResponse upload(MultipartFile multipartFile) {
        FileUploadResponse fileUploadResponse = new FileUploadResponse();
        if (multipartFile.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, Message.FILES_UPLOAD_NOT_FOUND);
        }
        String originalFileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        String fileExtension = getExtensionFileName(originalFileName);
        if (fileExtension.equals("")) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, Message.FILES_UPLOAD_EXT_NOT_SUPPORT);
        }
        String hashFileName = hashFileName(originalFileName);
        Path path = fileLocation.resolve(hashFileName + fileExtension);
        try {
            Files.copy(multipartFile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Message.FILES_UPLOAD_FILE_ERROR);
        }
        fileUploadResponse.setName(hashFileName);
        fileUploadResponse.setType(getExtensionNotDot(fileExtension));
        fileUploadResponse.setPath("/api/v1/files/" + hashFileName + fileExtension);
        fileUploadResponse.setSize(multipartFile.getSize());
        return fileUploadResponse;
    }

    public PageResponse<FileUploadResponse> upload(MultipartFile[] multipartFiles) {
        List<FileUploadResponse> fileUploadResponseList = Arrays.asList(multipartFiles)
                .stream()
                .map(file -> upload(file))
                .collect(Collectors.toList());
        PageResponse<FileUploadResponse> pageResponse = new PageResponse<>(
                fileUploadResponseList,
                fileUploadResponseList.size(),
                fileUploadResponseList.size()
        );
        pageResponse.setStatus(true);
        pageResponse.setMessage(Message.SUCCESS);
        return pageResponse;
    }

    public ResponseEntity get(String fileName) {
        Path path = fileLocation.resolve(fileName).normalize();
        try {
            Resource resource = new UrlResource(path.toUri());
            if (resource.exists()) {
                MediaType mediaType = MediaTypeFactory.getMediaType(fileName).get();
                return ResponseEntity
                        .ok()
                        .contentType(mediaType)
                        .body(resource);
            }
        } catch (MalformedURLException e) {
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, Message.FILES_FILE_NOT_FOUND);
    }

    private String getExtensionFileName(String originalFileName) {
        if (originalFileName.contains(".")) {
            return originalFileName.substring(originalFileName.lastIndexOf("."));
        }
        return "";
    }

    private String hashFileName(String originalFilename) {
        Random random = new Random();
        int hash = random.nextInt(999999999);
        return DigestUtils.md5DigestAsHex((originalFilename + hash).getBytes());
    }

    private String getExtensionNotDot(String ext) {
        return ext.replaceAll("\\.", "");
    }
}
