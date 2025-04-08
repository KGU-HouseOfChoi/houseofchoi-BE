package com.noraknorak.center.presentation;

import com.noraknorak.center.service.CenterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/centers")
@RequiredArgsConstructor
public class CenterController {
    private final CenterService centerService;
}
