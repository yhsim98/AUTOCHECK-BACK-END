package com.auto.check.api.dto;

import com.auto.check.domain.face.FaceImage;
import lombok.Data;

@Data
public class FaceImageDTO {
    private Long faceImageId;
    private String savedUrl;
    private String fileName;

    public FaceImageDTO(FaceImage faceImage) {
        this.faceImageId = faceImage.getId();
        this.savedUrl = faceImage.getSavedUrl();
        this.fileName = faceImage.getFileName();
    }
}
