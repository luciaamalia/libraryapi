package io.github.cursospringjpa.libraryapi.controller;

import io.github.cursospringjpa.libraryapi.controller.dto.AutorDTO;
import io.github.cursospringjpa.libraryapi.controller.dto.AutorResponseDTO;
import io.github.cursospringjpa.libraryapi.controller.dto.ErroResponse;
import io.github.cursospringjpa.libraryapi.controller.mappers.AutorMapper;
import io.github.cursospringjpa.libraryapi.exceptions.OperacaoNaoPermitidaException;
import io.github.cursospringjpa.libraryapi.exceptions.RegistroDuplicadoException;
import io.github.cursospringjpa.libraryapi.model.Autor;
import io.github.cursospringjpa.libraryapi.service.AutorService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("autores")
public class AutorController implements GenericController {

    private final AutorService service;
    private final AutorMapper mapper;

    public AutorController(AutorService service, AutorMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<Void> salvar(@Valid @RequestBody AutorDTO dto) {

        Autor autor = mapper.toEntity(dto);
        service.salvar(autor);
        var url = gerarHeaderLocation(autor.getId());
        return ResponseEntity.created(url).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<AutorDTO> obterDetalhes(@PathVariable("id") String id) {
        var idAutor = UUID.fromString(id);

        return service.obterPorId(idAutor).map(autor -> {
            AutorDTO dto = mapper.toDTO(autor);
            return ResponseEntity.ok(dto);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletarAutor(@PathVariable("id") String id) {
        var idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = service.obterPorId(idAutor);

        if (autorOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        service.deletarAutor(autorOptional.get());

        return ResponseEntity.noContent().build();

    }

    @GetMapping
    public ResponseEntity<List<AutorDTO>> pesquisar(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "nacionalidade", required = false) String nacionalidade) {
        List<Autor> resultado = service.pesquisaByExample(nome, nacionalidade);
        List<AutorDTO> lista = resultado
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(lista);
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> atualizar(@PathVariable("id") String id, @RequestBody AutorResponseDTO autorResponseDTO) {

        var idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = service.obterPorId(idAutor);

        if (autorOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var autor = autorOptional.get();
        autor.setNome(autorResponseDTO.nome());
        autor.setNacionalidade(autorResponseDTO.nacionalidade());
        autor.setDataNascimento(autorResponseDTO.dataNascimento());

        service.atualizar(autor);

        return ResponseEntity.noContent().build();

    }
}