package com.noraknorak.core.application;

import com.noraknorak.center.domain.Center;
import com.noraknorak.center.domain.repository.CenterRepository;
import com.noraknorak.program.domain.Program;
import com.noraknorak.program.domain.Tag;
import com.noraknorak.program.domain.repository.ProgramRepository;
import com.noraknorak.program.domain.repository.TagRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomDataLoadService implements CommandLineRunner {

    private final CenterRepository centerRepository;
    private final ProgramRepository programRepository;
    private final TagRepository tagRepository;

    @Value("${data.init.enabled}")
    private boolean initEnabled;

    @Override
    public void run(String... args) throws Exception {
        if(!initEnabled) return;

//        if(centerRepository.count() != 0 || programRepository.count() != 0 || tagRepository.count() != 0) {
//            programRepository.deleteAll();
//            tagRepository.deleteAll();
//            centerRepository.deleteAll();
//        }

        try {
            loadCenters();
            loadPrograms();
            loadProgramTags();
        } catch (Exception e) {
            log.error("데이터 초기화 중 오류 발생!", e);
        }

        log.info("[CustomDataLoadService] - 모든 데이터 삽입 완료");
    }

    @Transactional
    protected void loadCenters() throws IOException {
        log.info("[loadCenters] - Center 데이터 삽입 시작");
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                getClass().getResourceAsStream("/data/center.csv"), StandardCharsets.UTF_8))) {

            br.readLine(); // skip header
            String line;
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(",");
                Center center = Center.builder()
                        .name(tokens[0])
                        .latitude(Double.parseDouble(tokens[1]))
                        .longitude(Double.parseDouble(tokens[2]))
                        .address(tokens[3])
                        .tel(tokens[4])
                        .build();
                centerRepository.save(center);
            }

            log.info("[loadCenters] - Center 데이터 삽입 완료");
        }
    }

    @Transactional
    protected void loadPrograms() throws IOException {
        log.info("[loadPrograms] - Program 데이터 삽입 시작");
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                getClass().getResourceAsStream("/data/program.csv"), StandardCharsets.UTF_8))) {

            br.readLine(); // skip header
            String line;
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(",");

                Center center = centerRepository.findById(Long.parseLong(tokens[12]))
                        .orElseThrow(() -> new RuntimeException("센터 없음: " + tokens[12]));

                Program program = Program.builder()
                        .name(tokens[0])
                        .firDay(tokens[1])
                        .secDay(tokens[2])
                        .thrDay(tokens[3])
                        .fouDay(tokens[4])
                        .fivDay(tokens[5])
                        .startTime(LocalTime.parse(tokens[6]))
                        .endTime(LocalTime.parse(tokens[7]))
                        .price(Long.parseLong(tokens[8].replaceAll("[^\\d]", "")))
                        .mainCategory(tokens[9])
                        .subCategory(tokens[10])
                        .headcount(tokens[11])
                        .center(center)
                        .build();

                programRepository.save(program);
            }
            log.info("[loadPrograms] - Program 데이터 삽입 완료");
        }
    }

    @Transactional
    protected void loadProgramTags() throws IOException {
        Map<String, Tag> tagCache = new HashMap<>();
        Map<Long, Program> programMap = new HashMap<>();

        log.info("[loadProgramTags] - ProgramTag 데이터 삽입 시작");

        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                getClass().getResourceAsStream("/data/tags.csv"), StandardCharsets.UTF_8))) {

            br.readLine(); // skip header
            String line;
            int count = 0;

            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(",");
                Long programId = Long.parseLong(tokens[0]);
                String tagName = tokens[1];

                Program program = programMap.computeIfAbsent(programId, id ->
                        programRepository.findWithTagsById(id)
                                .orElseThrow(() -> new RuntimeException("프로그램 없음: " + id)));

                Tag tag = tagCache.computeIfAbsent(tagName, name -> {
                    Tag newTag = Tag.builder().name(name).build();
                    return tagRepository.save(newTag);
                });

                log.info("프로그램 ID: {}, 태그: {}", programId, tagName);
                program.getTags().add(tag);
                count++;

                // 100개마다 저장 및 flush
                if (count % 100 == 0) {
                    programRepository.saveAll(programMap.values());
                    programRepository.flush();
                    programMap.clear(); // 메모리 관리 + 중복 방지
                    log.info("중간 저장 및 flush 완료 - {}개 처리됨", count);
                }
            }

            // 남은 데이터 마지막 flush
            if (!programMap.isEmpty()) {
                programRepository.saveAll(programMap.values());
                programRepository.flush();
                log.info("마지막 저장 및 flush 완료 - 총 {}개 처리됨", count);
            }

            log.info("[loadProgramTags] - ProgramTag 데이터 삽입 완료");
        }
    }

}