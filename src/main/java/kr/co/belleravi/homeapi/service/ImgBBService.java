package kr.co.belleravi.homeapi.service;

import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Service
public class ImgBBService {

    @Value("${imgbb.api-key}")
    private String IMGBB_API_KEY;

    public String uploadImage(MultipartFile imageFile) throws IOException {
        OkHttpClient client = new OkHttpClient();

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("image", imageFile.getOriginalFilename(),
                        RequestBody.create(MediaType.parse("application/octet-stream"), imageFile.getBytes()))
                .build();

        Request request = new Request.Builder()
                .url("https://api.imgbb.com/1/upload?key=" + IMGBB_API_KEY)
                .post(requestBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            String responseBody = response.body().string();
            // 이미지 업로드 후 ImgBB에서 반환한 URL 추출
            return extractImageUrl(responseBody);
        }
    }

    private String extractImageUrl(String responseBody) {
        // 이미지 업로드 후 ImgBB에서 반환한 URL을 추출하는 로직을 구현
        // 여기서는 간단히 예시로 정규표현식을 사용하겠습니다.
        // 실제로는 JSON 파싱 라이브러리를 사용하는 것이 좋습니다.
        // 여기서는 간단한 예제이므로 정규표현식을 사용합니다.
        // 참고: 이 부분은 실제로 사용하는 API 응답 형식에 따라 적절히 수정되어야 합니다.
        String pattern = "\"url\":\"([^\"]+)\"";
        return extractValueByPattern(responseBody, pattern);
    }

    private String extractValueByPattern(String input, String pattern) {
        java.util.regex.Pattern regex = java.util.regex.Pattern.compile(pattern);
        java.util.regex.Matcher matcher = regex.matcher(input);

        if (matcher.find()) {
            return matcher.group(1);
        } else {
            throw new RuntimeException("Failed to extract value by pattern");
        }
    }
}
