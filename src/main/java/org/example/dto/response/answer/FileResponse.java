package org.example.dto.response.answer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.core.io.ByteArrayResource;

@Getter
@AllArgsConstructor
public class FileResponse {
    private String fileName;
    private String fileType;
    private ByteArrayResource resource;
}
