package com.thiendz.wipe.wipeserve.controllers;

import com.thiendz.wipe.wipeserve.dto.response.FileUploadResponse;
import com.thiendz.wipe.wipeserve.dto.response.PageResponse;
import com.thiendz.wipe.wipeserve.dto.response.Response;
import com.thiendz.wipe.wipeserve.services.FileService;
import com.thiendz.wipe.wipeserve.utils.constant.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/api/v1/files")
public class FileController {
    @Autowired
    FileService fileService;

    @GetMapping("/{fileName}")
    public ResponseEntity get(@PathVariable("fileName") String fileName) {
        return fileService.get(fileName);
    }

    @PostMapping("/upload")
    public ResponseEntity<Response<FileUploadResponse>> uploadFile(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(new Response<>(true, Message.SUCCESS, fileService.upload(file)));
    }

    @PostMapping("/uploads")
    public ResponseEntity<PageResponse<FileUploadResponse>> uploadFile(@RequestParam("file") MultipartFile[] files) {
        return ResponseEntity.ok(fileService.upload(files));
    }

}
