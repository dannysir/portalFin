package kr.ac.jejunu.user.userdao3;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Long> {
    Optional<Image> findByLatitudeAndLongtitude(String latitude, String longtitude);
//    List<Image> findByLatitudeAndLongtitude(String latitude, String longtitude);
    List<Image> findAllByLatitudeAndLongtitude(String latitude, String longtitude);
}
