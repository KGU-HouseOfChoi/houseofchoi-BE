package com.noraknorak.sms.presentaion;

import com.noraknorak.core.presentation.RestResponse;
import com.noraknorak.sms.presentaion.request.SmsRequest;
import com.noraknorak.sms.service.SmsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sms")
@RequiredArgsConstructor
public class SmsController implements SmsSwagger{

    private final SmsService smsService;

    @Override
    @PostMapping("/send")
    public ResponseEntity<RestResponse<Boolean>> send(@Valid @RequestBody SmsRequest request) throws Exception {
        smsService.sendSms(request.getPhoneNum());
        return ResponseEntity.ok(new RestResponse<>(true));
    }
}