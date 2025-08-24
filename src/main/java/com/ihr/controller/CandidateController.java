package com.ihr.controller;

import com.ihr.service.CandidateService;
import com.ihr.dto.CandidateDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/candidate")
@RequiredArgsConstructor
public class CandidateController {
    private final CandidateService candidateService;

    @PostMapping
    public CandidateDto create(@RequestBody @Valid @NotNull CandidateDto candidateDto) {
        return candidateService.save(candidateDto);
    }

    @GetMapping
    public List<CandidateDto> findByName(@RequestParam @NotNull String name) {
        return candidateService.findByName(name);
    }

    @PutMapping("/{id}")
    public boolean updateAddress(@PathVariable @NotNull Long id, @RequestParam @NotNull String newAddress) {
        return candidateService.updateAddress(id, newAddress);
    }

}
