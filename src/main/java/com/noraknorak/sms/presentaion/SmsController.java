package com.noraknorak.sms.presentaion;

import com.noraknorak.core.presentation.RestResponse;
import com.noraknorak.sms.domain.dto.request.SmsRequest;
import com.noraknorak.sms.service.SmsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sms")
@RequiredArgsConstructor
public class SmsController {

    private final SmsService smsService;

    @PostMapping("/send")
    public ResponseEntity<RestResponse<Boolean>> send(@Valid @RequestBody SmsRequest request) {
        smsService.sendSms(request.getPhoneNum());
        return ResponseEntity.ok(new RestResponse<>(true));
    }
}