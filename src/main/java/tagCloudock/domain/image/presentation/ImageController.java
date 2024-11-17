package tagCloudock.domain.image.presentation;

import tagCloudock.domain.image.presentation.constant.ResponseMessage;
import tagCloudock.domain.image.application.ImageService;
import tagCloudock.domain.image.dto.res.ImageResponse;
import tagCloudock.domain.image.exception.ImageNotFoundException;
import tagCloudock.global.config.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
public class ImageController {


    @Value("${image.path}")
    private String IMAGE_PATH;
    private final ImageService imageService;

    @GetMapping("/image")
    public ResponseEntity<?> returnImage(@RequestParam String path) {
//        if (path.contains(imageService.IMPORTANT_KEYWORD)) {
//            Resource resource = new FileSystemResource(IMAGE_PATH + imageService.DEFAULT_IMAGE);
//            return new ResponseEntity<>(resource, HttpStatus.OK);
//        }
        System.out.println(IMAGE_PATH + path);
        try {
            Resource resource = new FileSystemResource(IMAGE_PATH + path);
            return new ResponseEntity<>(resource, HttpStatus.OK);
        } catch (Exception e) {
            throw new ImageNotFoundException();
        }

    }

    @PostMapping("/image")
    public ResponseDto<ImageResponse> saveImage(@RequestPart MultipartFile pic) {
        try {
            String imageName = imageService.save(pic, false);
            return ResponseDto.of(OK.value(), ResponseMessage.SUCCESS_CREATE.getMessage(), new ImageResponse(imageName));
        } catch (Exception e) {
            throw new ImageNotFoundException();
        }
    }
}