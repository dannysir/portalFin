package kr.ac.jejunu.user.userdao3;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Service
public class ImageController {
//    private final String UPLOAD_DIR = "/src/main/webapp/WEB-INF/static/";
    private final String UPLOAD_DIR = "/src/main/webapp/WEB-INF/static/image/";
    private final ImageRepository imageRepository;


    @PostMapping("/upload")
    @CrossOrigin(origins = "http://localhost:5173/")
    public String upload(@RequestParam("image") MultipartFile file, HttpServletRequest request,
                         @RequestParam("latitude") String lat,
                         @RequestParam("longtitude") String lnt) throws IOException {
//        File path = new File(request.getServletContext().getRealPath("/") + "/static/");
        String pathStr = Paths.get("").toAbsolutePath() + UPLOAD_DIR;
        File path = new File(pathStr);

        FileOutputStream fileOutputStream = new FileOutputStream(path + File.separator + file.getOriginalFilename());
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
        bufferedOutputStream.write(file.getBytes());
        bufferedOutputStream.close();

        Image image = Image.builder()
                .url("http://localhost:8080/image/" + file.getOriginalFilename())
                .latitude(lat)
                .longtitude(lnt)
                .build();
        imageRepository.save(image);

        return "http://localhost:8080/" + file.getOriginalFilename();

    }

    @GetMapping("/images")
    @CrossOrigin(origins = "http://localhost:5173/")
    public Set<String> getAllimages(@RequestParam("latitude") String lat,
                                    @RequestParam("longtitude") String lnt) {
        System.out.println(lat + lnt);
        List<Image> imgese = imageRepository.findAllByLatitudeAndLongtitude(lat, lnt);
        System.out.println(imgese);

        return imgese.stream()
                .map(Image::getUrl)
                .collect(Collectors.toSet());
    }

}
