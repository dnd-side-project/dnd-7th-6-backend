package com.hot6.phopa.core.domain.map.service;

import com.hot6.phopa.core.common.utils.QueryUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class KakaoMapService {

    @Value("${kakao.map.api-key}")
    private String apiKey;

    @Value("${kakao.map.host}")
    private String host;

    @Value("${kakao.map.path.keyword-search}")
    private String keywordSearchPath;
    public String getLocation(String keyword, Double latitude, Double longitude, Double distance) {

        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("query", keyword);
        queryMap.put("page", 1);
        queryMap.put("size", 30);
        Optional.ofNullable(latitude).ifPresent(x -> queryMap.put("x", x));
        Optional.ofNullable(longitude).ifPresent(y -> queryMap.put("y", y));
        Optional.ofNullable(distance).ifPresent(radius -> queryMap.put("radius", (int)(distance * 1000)));
        String queryString = "?" + QueryUtil.urlEncodeUTF8(queryMap);
//        queryString = "?query=" + QueryUtil.urlEncodeUTF8(keyword);
        HttpURLConnection conn = null;
        StringBuffer response = new StringBuffer();

        //인증키 - KakaoAK하고 한 칸 띄워주셔야해요!
        String auth = apiKey;
        while(true){
            try {
                //URL 설정
                URL url = new URL(host + keywordSearchPath + queryString);

                conn = (HttpURLConnection) url.openConnection();

                //Request 형식 설정
                conn.setRequestMethod("GET");
                conn.setRequestProperty("X-Requested-With", "curl");
                conn.setRequestProperty("Authorization", auth);

                //request에 JSON data 준비
                conn.setDoOutput(true);

                //보내고 결과값 받기
                int responseCode = conn.getResponseCode();
                if (responseCode == 400) {
                    System.out.println("400:: 해당 명령을 실행할 수 없음");
                } else if (responseCode == 401) {
                    System.out.println("401:: Authorization가 잘못됨");
                } else if (responseCode == 500) {
                    System.out.println("500:: 서버 에러, 문의 필요");
                } else { // 성공 후 응답 JSON 데이터받기

                    Charset charset = Charset.forName("UTF-8");
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), charset));

                    String inputLine;
                    while ((inputLine = br.readLine()) != null) {
                        response.append(inputLine);
                    }
                    if(true){
                        break;
                    }
                    return response.toString();
                }
            } catch (Exception e) {

            }
        }

        return null;
    }
}
