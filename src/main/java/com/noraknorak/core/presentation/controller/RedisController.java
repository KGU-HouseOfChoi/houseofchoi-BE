package com.noraknorak.core.presentation.controller;

import com.noraknorak.core.presentation.RestResponse;
import com.noraknorak.core.presentation.swagger.RedisSwagger;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test/redis")
@RequiredArgsConstructor
public class RedisController implements RedisSwagger {
    private final RedisTemplate redisTemplate;

    private static final String KEY = "hello";
    private static final String VALUE = "redis";

    @Override
    @PostMapping("/set")
    public ResponseEntity<RestResponse<Boolean>> setValue() {
        try {
            redisTemplate.opsForValue().set(KEY, VALUE);
            return ResponseEntity.ok(new RestResponse<>(true));
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(new RestResponse<>(false));
        }
    }

    @Override
    @GetMapping("/get")
    public ResponseEntity<RestResponse<Object>> getValue() {
        try{
            String value = (String) redisTemplate.opsForValue().get(KEY);
            if(value != null){
                return ResponseEntity.ok(new RestResponse<>(value));
            }else{
                return ResponseEntity.ok(new RestResponse<>(false));
            }
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(new RestResponse<>(false));
        }
    }
}
