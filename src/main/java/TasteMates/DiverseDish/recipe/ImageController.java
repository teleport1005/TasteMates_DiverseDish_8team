package TasteMates.DiverseDish.recipe;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;


@Slf4j
@Controller
@RequestMapping("/media")
@RequiredArgsConstructor
public class ImageController {
    @GetMapping("/{filename}")
    @ResponseBody
    public ResponseEntity<byte[]> getImage(
            @PathVariable("filename")
            String filename
    ) throws IOException {
        File file = new File("media/" + filename);
        // 이미지 파일을 byte 배열로 읽어옵니다.
        byte[] imageBytes = Files.readAllBytes(file.toPath());

        // 이미지를 응답으로 반환합니다.
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(imageBytes);
    }
}
