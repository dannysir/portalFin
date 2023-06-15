package kr.ac.jejunu.user.userdao3;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Service
public class UserController {
    private final UserDao userDao;
    private final ImageRepository imageRepository;

    @GetMapping("/{id}")
    public User get(@PathVariable Long id) {
        return userDao.findById(id).get();
    }

    @PostMapping("/upload")
    @CrossOrigin(origins = "http://localhost:5173/")
    public String upload(@RequestParam("image") MultipartFile file, HttpServletRequest request,
                         @RequestParam("latitude") String lat,
                         @RequestParam("longtitude") String lnt) throws IOException {
//        File path = new File(request.getServletContext().getRealPath("/") + "/static/");
        String pathStr = Paths.get("").toAbsolutePath() + "/src/main/resources/static/";
        File path = new File(pathStr);

        FileOutputStream fileOutputStream = new FileOutputStream(path + File.separator + file.getOriginalFilename());
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
