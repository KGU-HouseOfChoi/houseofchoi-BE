package com.noraknorak.core.presentation.controller;

import com.noraknorak.core.presentation.RestResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/redis")
@RequiredArgsConstructor
public class RedisController {
    private final RedisTemplate redisTemplate;

    private String key = "hello";
    private String value = "redis";

    @PostMapping("/set")
    public ResponseEntity<RestResponse<Boolean>> setValue() {
        redisTemplate.opsForValue().set(key, value);
        return ResponseEntity.ok(new RestResponse<>(true));
    }

    @GetMapping("/get")
    public ResponseEntity<RestResponse<String>> getValue() {
        String value = (String) redisTemplate.opsForValue().getAndDelete(key);
        return value != null
                ? ResponseEntity.ok(new RestResponse<>(value))
                : ResponseEntity.ok(new RestResponse<>("false"));
    }
}
