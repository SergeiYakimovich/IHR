package com.ihr.controller;

import com.ihr.dto.CandidateDto;
import com.ihr.dto.VacancyDto;
import com.ihr.service.VacancyService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vacancy")
@RequiredArgsConstructor
public class VacancyController {
    private final VacancyService vacancyService;

    @PostMapping
    public VacancyDto create(@RequestBody @Valid @NotNull VacancyDto vacancyDto) {
        return vacancyService.save(vacancyDto);
    }

    @GetMapping
    public List<VacancyDto> findByName(@RequestParam @NotNull String name) {
        return vacancyService.findByName(name);
    }

    @PostMapping("/find")
    public List<CandidateDto> findBests(@RequestBody @Valid @NotNull VacancyDto vacancyDto) {
        return vacancyService.findBests(vacancyDto);
    }
}
