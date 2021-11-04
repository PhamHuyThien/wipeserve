package com.thiendz.wipe.wipeserve.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FileUploadResponse {
    String name;
    String type;
    long size;
    String path;

}
