package io.github.cursospringjpa.libraryapi.controller;

import io.github.cursospringjpa.libraryapi.controller.dto.AutorDTO;
import io.github.cursospringjpa.libraryapi.controller.dto.AutorResponseDTO;
import io.github.cursospringjpa.libraryapi.controller.dto.ErroResponse;
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
public class AutorController {

    private final AutorService service;

    public AutorController(AutorService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Object> salvar(@Valid @RequestBody AutorDTO autor){
        try {
            Autor autorEntidade = autor.mapearParaAutor();
            service.salvar(autorEntidade);

            //http://localhost:8080/autores/{id}
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(autorEntidade.getId())
                    .toUri();


            return ResponseEntity.created(location).build();
        }catch (RegistroDuplicadoException e){
            var erroDto = ErroResponse.conflito(e.getMessage());
            return ResponseEntity.status(erroDto.status()).body(erroDto);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<AutorResponseDTO> obterDetalhes(@PathVariable("id") String id){
        var idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = service.obterPorId(idAutor);
        if (autorOptional.isPresent()){
            Autor autor = autorOptional.get();
            AutorResponseDTO autorResponseDTO = new AutorResponseDTO(
                    autor.getId(),
                    autor.getNome(),
                    autor.getDataNascimento(), autor.getNacionalidade());
            return ResponseEntity.ok(autorResponseDTO);

        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deletarAutor(@PathVariable("id") String id){
        try {
            var idAutor = UUID.fromString(id);
            Optional<Autor> autorOptional = service.obterPorId(idAutor);

            if (autorOptional.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            service.deletarAutor(autorOptional.get());

            return ResponseEntity.noContent().build();
        }catch (OperacaoNaoPermitidaException e){
            var erroResponse = ErroResponse.respostaPadrao(e.getMessage());
            return ResponseEntity.status(erroResponse.status()).body(erroResponse);
        }
    }

    @GetMapping
    public ResponseEntity<List<AutorResponseDTO>> pesquisar(
            //param nao obrigatorios
            @RequestParam(value ="nome", required=false) String nome,
            @RequestParam(value = "nacionalidade", required=false) String nacionalidade){
        List<Autor> resultado = service.pesquisaByExample(nome,nacionalidade);
        List<AutorResponseDTO> lista = resultado
                .stream()
                .map(autor -> new AutorResponseDTO(
                        autor.getId(),
                        autor.getNome(),
                        autor.getDataNascimento(),
                        autor.getNacionalidade())
                ).collect(Collectors.toList());
        //retorna a lista
                return ResponseEntity.ok(lista);
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> atualizar(
            @PathVariable("id") String id, @RequestBody AutorResponseDTO autorResponseDTO) {

        try {

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

        } catch(RegistroDuplicadoException e){
            var erroDto = ErroResponse.conflito(e.getMessage());
            return ResponseEntity.status(erroDto.status()).body(erroDto);
        }
    }
}