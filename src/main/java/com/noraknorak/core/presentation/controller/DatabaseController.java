package com.noraknorak.core.presentation.controller;

import com.noraknorak.core.presentation.RestResponse;
import com.noraknorak.core.presentation.swagger.DBTestSwagger;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class DatabaseController implements DBTestSwagger {

    private final DataSource dataSource;

    @Override
    @GetMapping("/db")
    public ResponseEntity<RestResponse<String>> testDatabaseConnection() {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT NOW()")) {

            if (resultSet.next()) {
                return ResponseEntity.ok(new RestResponse<>("DB 연결 성공!! : " + resultSet.getString(1)));
            } else {
                return ResponseEntity.ok(new RestResponse<>("DB 연결은 되었지만 데이터를 가져오지 못했습니다."));
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(new RestResponse<>( "DB 연결 실패: " + e.getMessage()));
        }
    }
}
