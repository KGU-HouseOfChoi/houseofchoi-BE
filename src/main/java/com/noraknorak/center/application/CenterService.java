package com.noraknorak.center.application;

import com.noraknorak.center.domain.repository.CenterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CenterService {
    private final CenterRepository centerRepository;
}
