package io.github.cursospringjpa.libraryapi.service;

import io.github.cursospringjpa.libraryapi.model.GeneroLivro;
import io.github.cursospringjpa.libraryapi.model.Livro;
import io.github.cursospringjpa.libraryapi.repository.LivroRepository;
import io.github.cursospringjpa.libraryapi.repository.specs.LivroSpecs;
import io.github.cursospringjpa.libraryapi.validator.LivroValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LivroService {

    private final LivroRepository repository;
    private final LivroValidator validator;

    public LivroService(LivroRepository repository, LivroValidator validator) {
        this.repository = repository;
        this.validator = validator;
    }

    public Livro salvar(Livro livro) {
        validator.validar(livro);
        return repository.save(livro);
    }

    public Optional<Livro> obterPorId(UUID id) {
        return repository.findById(id);
    }

    public void deletar(Livro livro) {
        repository.delete(livro);
    }


    public Page<Livro> pesquisa(String isbn,
                                String titulo,
                                String nomeAutor,
                                GeneroLivro genero,
                                Integer anoPublicacao,
                                Integer pagina,
                                Integer tamanhoPagina
    ){

//        Specification<Livro> specs = Specification
//                .where(LivroSpecs.isbnEqual(isbn))
//                .and(LivroSpecs.tituloLike(titulo))
//                .and(LivroSpecs.generoEqual(genero))
//                ;

        Specification<Livro> specs = Specification.where((root, query, cb) ->
                cb.conjunction());

        if (isbn != null){
            //query = query and isbn = :isbn
            specs = specs.and(LivroSpecs.isbnEqual(isbn));
        }

        if (titulo != null){
            //query = query and isbn = :isbn
            specs = specs.and(LivroSpecs.tituloLike(titulo));
        }
        if (genero != null){
            //query = query and isbn = :isbn
            specs = specs.and(LivroSpecs.generoEqual(genero));
        }
        if (anoPublicacao != null){
            //query = query and isbn = :isbn
            specs = specs.and(LivroSpecs.anoPublicacaoEqual(anoPublicacao));
        }
        if (nomeAutor != null){
            specs = specs.and(LivroSpecs.nomeAutorLike(nomeAutor));
        }


        Pageable pageableRequest = new PageRequest.of(pagina, tamanhoPagina);

        return repository.findAll(specs, pageableRequest);
    }

    public void atualizar(Livro livro) {
        if (livro.getId() == null){
            throw new IllegalArgumentException("Para atualizar é necesário que o autor já esteja salvo");
        }
        validator.validar(livro);
        repository.save(livro);
    }
}