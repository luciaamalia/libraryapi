package io.github.cursospringjpa.libraryapi.controller;

import io.github.cursospringjpa.libraryapi.controller.dto.ErroResponse;
import io.github.cursospringjpa.libraryapi.controller.dto.RequestLivroDTO;
import io.github.cursospringjpa.libraryapi.controller.dto.ResponsePesquisaDTO;
import io.github.cursospringjpa.libraryapi.controller.mappers.LivroMapper;
import io.github.cursospringjpa.libraryapi.exceptions.RegistroDuplicadoException;
import io.github.cursospringjpa.libraryapi.model.GeneroLivro;
import io.github.cursospringjpa.libraryapi.model.Livro;
import io.github.cursospringjpa.libraryapi.service.LivroService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("livros")
public class LivroController {

    private final LivroService service;


    public LivroController(LivroService service) {
        this.service = service;

    }

    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody @Valid RequestLivroDTO requestLivroDTO) {
       try {
           //mapear dto para entidade
           //enviar a entidade para o service valida e salvar na base de dados
           //criar a url pra acessodos dados do livro
           //retornar codigo created com header location
           return ResponseEntity.ok(requestLivroDTO);
       }catch(RegistroDuplicadoException e){
           var erroDTO = ErroResponse.conflito(e.getMessage());
           return  ResponseEntity.status(erroDTO.status()).body(erroDTO);
       }
    }
//
//    @GetMapping("{id}")
//    public ResponseEntity<ResponsePesquisaDTO> obterDetalhes(
//            @PathVariable("id") String id){
//        return service.obterPorId(UUID.fromString(id))
//                .map(livro -> {
//                    var dto = mapper.toDTO(livro);
//                    return ResponseEntity.ok(dto);
//                }).orElseGet( () -> ResponseEntity.notFound().build() );
//    }
//
//    @DeleteMapping("{id}")
//    public ResponseEntity<Object> deletar(@PathVariable("id") String id){
//        return service.obterPorId(UUID.fromString(id))
//                .map(livro -> {
//                    service.deletar(livro);
//                    return ResponseEntity.noContent().build();
//                }).orElseGet(() -> ResponseEntity.notFound().build());
//    }
//
//    @GetMapping
//    public ResponseEntity<Page<ResponsePesquisaDTO>> pesquisa(
//            @RequestParam(value = "isbn", required = false)
//            String isbn,
//            @RequestParam(value = "titulo", required = false)
//            String titulo,
//            @RequestParam(value = "nome-autor", required = false)
//            String nomeAutor,
//            @RequestParam(value = "genero", required = false)
//            GeneroLivro genero,
//            @RequestParam(value = "ano-publicacao", required = false)
//            Integer anoPublicacao,
//            @RequestParam(value = "pagina", defaultValue = "0")
//            Integer pagina,
//            @RequestParam(value = "tamanho-pagina", defaultValue = "10")
//            Integer tamanhoPagina
//    ){
//        Page<Livro> paginaResultado = service.pesquisa(
//                isbn, titulo, nomeAutor, genero, anoPublicacao, pagina, tamanhoPagina);
//
//        Page<ResponsePesquisaDTO> resultado = paginaResultado.map(mapper::toDTO);
//
//        return ResponseEntity.ok(resultado);
//    }
//
//    @PutMapping("{id}")
//    public ResponseEntity<Object> atualizar(
//            @PathVariable("id") String id, @RequestBody @Valid RequestLivroDTO dto){
//        return service.obterPorId(UUID.fromString(id))
//                .map(livro -> {
//                    Livro entidadeAux = mapper.toEntity(dto);
//
//                    livro.setDataPublicacao(entidadeAux.getDataPublicacao());
//                    livro.setIsbn(entidadeAux.getIsbn());
//                    livro.setPreco(entidadeAux.getPreco());
//                    livro.setGenero(entidadeAux.getGenero());
//                    livro.setTitulo(entidadeAux.getTitulo());
//                    livro.setAutor(entidadeAux.getAutor());
//
//                    service.atualizar(livro);
//
//                    return ResponseEntity.noContent().build();
//
//                }).orElseGet( () -> ResponseEntity.notFound().build() );
//    }

}