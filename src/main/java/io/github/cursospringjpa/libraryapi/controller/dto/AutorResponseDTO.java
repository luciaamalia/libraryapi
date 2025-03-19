package io.github.cursospringjpa.libraryapi.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;

public record AutorResponseDTO(
        UUID id,
        @NotBlank(message = "campo obrigatório")
        String nome,
        @NotNull(message = "campo obrigatório")
        LocalDate dataNascimento,
        @NotBlank(message = "campo obrigatório")
        String nacionalidade
) {
}
