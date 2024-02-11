package kr.co.belleravi.homeapi.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String imgPath;
    private String content;
    private String tag;
    private LocalDateTime date;

}
