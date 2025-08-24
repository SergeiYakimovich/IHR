package com.ihr.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VacancyDto {
    @NotNull
    private String employer;
    @NotNull
    private Map<String, Integer> skills;
}
