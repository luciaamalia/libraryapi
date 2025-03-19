package io.github.cursospringjpa.libraryapi.controller;

import io.github.cursospringjpa.libraryapi.controller.dto.AutorDTO;
import io.github.cursospringjpa.libraryapi.controller.dto.AutorResponseDTO;
import io.github.cursospringjpa.libraryapi.model.Autor;
import io.github.cursospringjpa.libraryapi.service.AutorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/autores")
public class AutorController {

    private final AutorService service;

    public AutorController(AutorService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Void> salvar(@RequestBody AutorDTO autor){
        Autor autorEntidade= autor.mapearParaAutor();
        service.salvar(autorEntidade);

        //http://localhost:8080/autores/{id}
        URI location= ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(autorEntidade.getId())
                .toUri();


        return ResponseEntity.created(location).build();
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
    public ResponseEntity<Void> deletarAutor(@PathVariable("id") String id){
        var idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = service.obterPorId(idAutor);

        if (autorOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        service.deletarAutor(autorOptional.get());

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<AutorResponseDTO>> pesquisar(
            //param nao obrigatorios
            @RequestParam(value ="nome", required=false) String nome,
            @RequestParam(value = "nacionalidade", required=false) String nacionalidade){
        List<Autor> resultado = service.pesquisa(nome,nacionalidade);
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
    public ResponseEntity<Void> atualizar(
            @PathVariable("id") String id, @RequestBody AutorResponseDTO autorResponseDTO) {

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