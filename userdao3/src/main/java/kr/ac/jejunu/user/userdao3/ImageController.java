package kr.ac.jejunu.user.userdao3;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@RestController
@RequestMapping("api/fileupload")
@RequiredArgsConstructor
public class ImageController {
    private static final String UPLOAD_DIR = "static/images/";

    private ImageRepository imageRepository;
    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file, HttpServletRequest request,
                         @RequestParam("latitude") String lat,
                         @RequestParam("longtitude") String lnt) throws IOException {
        File path = new File(request.getServletContext().getRealPath("/") + "/static/");
//        path.mkdir();
        FileOutputStream fileOutputStream = new FileOutputStream(path + file.getOriginalFilename());
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
        bufferedOutputStream.write(file.getBytes());
        bufferedOutputStream.close();


        Image image = Image.builder()
                .url("http://localhost:8080/" + file.getOriginalFilename())
                .latitude(lat)
                .longtitude(lnt)
                .build();
        imageRepository.save(image);
        return "http://localhost:8080/" + file.getOriginalFilename();

    }

}
