package kr.co.belleravi.homeapi.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class NewsDTO {
    private String title;
    private MultipartFile imgFile;
    private String content;
    private String tag;
}
