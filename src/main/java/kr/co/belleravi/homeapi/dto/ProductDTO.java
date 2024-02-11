package kr.co.belleravi.homeapi.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ProductDTO {
    private String title;
    private MultipartFile imgFile;
    private String description;
    private String content;
}
