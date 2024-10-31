package gachonherald.domain.ocr.application;

import com.google.cloud.vision.v1.AnnotateImageRequest;
import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.BatchAnnotateImagesResponse;
import com.google.cloud.vision.v1.Feature;
import com.google.cloud.vision.v1.Image;
import com.google.cloud.vision.v1.ImageAnnotatorClient;
import com.google.protobuf.ByteString;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import gachonherald.domain.ocr.dto.res.OCRResponse;
import gachonherald.domain.ocr.exception.ErrorDetectedException;
import gachonherald.domain.ocr.exception.ImageNotProvidedException;

import java.util.ArrayList;
import java.util.List;

@Service
public class OCRService {
    public OCRResponse extractTextFromImage(MultipartFile pic){
        if (pic.isEmpty()){
            throw new ImageNotProvidedException();
        }
        try{
            ByteString imgBytes = ByteString.copyFrom(pic.getBytes());
            Image img = Image.newBuilder().setContent(imgBytes).build();
            Feature feat = Feature.newBuilder().setType(Feature.Type.TEXT_DETECTION).build();
            AnnotateImageRequest request = AnnotateImageRequest.newBuilder()
                    .addFeatures(feat)
                    .setImage(img)
                    .build();
            List<AnnotateImageRequest> requests = new ArrayList<>();
            requests.add(request);
            try (ImageAnnotatorClient client = ImageAnnotatorClient.create()) {
                BatchAnnotateImagesResponse response = client.batchAnnotateImages(requests);
                StringBuilder stringBuilder = new StringBuilder();
                for (AnnotateImageResponse res : response.getResponsesList()) {
                    if (res.hasError()) {
                        System.out.printf("Error: %s\n", res.getError().getMessage());
                        throw new ErrorDetectedException();
                    }
                    stringBuilder.append(res.getFullTextAnnotation().getText());
                }
                return new OCRResponse(stringBuilder.toString());
            }
        }catch (Exception e){
            throw new ErrorDetectedException();
        }

    }
}
