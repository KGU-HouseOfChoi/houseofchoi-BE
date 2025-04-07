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
public class SmsController {

    private final SmsService smsService;

    /**
     * Handles a POST request to send an SMS message.
     *
     * <p>Extracts the phone number from the provided {@code SmsRequest} and delegates the SMS sending
     * operation to the SMS service. Returns a response indicating the successful dispatch of the SMS.
     *
     * @param request the SMS request payload containing the recipient's phone number
     * @return a {@code ResponseEntity} containing a {@code RestResponse} that wraps a boolean flag set to
     *         {@code true} if the SMS was sent successfully
     * @throws Exception if an error occurs during the SMS sending process
     */
    @PostMapping("/send")
    public ResponseEntity<RestResponse<Boolean>> send(@Valid @RequestBody SmsRequest request) throws Exception {
        smsService.sendSms(request.getPhoneNum());
        return ResponseEntity.ok(new RestResponse<>(true));
    }
}