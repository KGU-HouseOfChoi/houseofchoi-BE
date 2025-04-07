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

    @PostMapping("/set")
    public ResponseEntity<RestResponse<Boolean>> setValue(@RequestParam String key, @RequestParam String value) {
        redisTemplate.opsForValue().set(key, value);
        return ResponseEntity.ok(new RestResponse<>(true));
    }

    @GetMapping("/get")
    public ResponseEntity<RestResponse<String>> getValue(@RequestParam String key) {
        String value = (String) redisTemplate.opsForValue().getAndDelete(key);
        return value != null
                ? ResponseEntity.ok(new RestResponse<>(value))
                : ResponseEntity.ok(new RestResponse<>("false"));
    }
}
