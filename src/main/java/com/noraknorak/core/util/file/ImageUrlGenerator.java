package com.noraknorak.core.util.file;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ImageUrlGenerator {

    @Value("${aws.image-url}")
    private String imageUrl;

    public String getImageUrlById(Long id){
        return imageUrl + "/" + getImageNameByid(id);
    }

    private String getImageNameByid(Long id){
        return String.valueOf(id) + ".jpg";
    }
}
