package io.github.cursospringjpa.libraryapi.service;

import io.github.cursospringjpa.libraryapi.model.Autor;
import io.github.cursospringjpa.libraryapi.repository.AutorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AutorService {
    //serviço trata a camada de dominio (entidade)

    private final AutorRepository autorRepository;

    public AutorService(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }


    public Autor salvar(Autor autor){
        return autorRepository.save(autor);
    }

    public Optional<Autor> obterPorId(UUID id){
        return autorRepository.findById(id);
    }

    public void deletarAutor(Autor autor){
        autorRepository.delete(autor);
    }
    public List<Autor> pesquisa(String nome, String nacionalidade){
        if(nome != null && nacionalidade != null){
            return autorRepository.findByNomeAndNacionalidade(nome, nacionalidade);
        }
        if(nome != null ){
            return autorRepository.findByNome(nome);
        }
        if(nacionalidade != null ){
            return autorRepository.findByNacionalidade(nacionalidade);
        }
        return autorRepository.findAll();

    }
}
