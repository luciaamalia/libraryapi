package io.github.cursospringjpa.libraryapi.controller.mappers;

import io.github.cursospringjpa.libraryapi.controller.dto.RequestLivroDTO;
import io.github.cursospringjpa.libraryapi.controller.dto.ResponsePesquisaDTO;
import io.github.cursospringjpa.libraryapi.model.Livro;
import io.github.cursospringjpa.libraryapi.repository.AutorRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;


@Mapper(componentModel = "spring", uses = AutorMapper.class )
public abstract class LivroMapper {

    @Autowired
    AutorRepository autorRepository;

    public LivroMapper(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    @Mapping(target = "autor", expression = "java( autorRepository.findById(dto.idAutor()).orElse(null) )")
    public abstract Livro toEntity(RequestLivroDTO dto);

    public abstract ResponsePesquisaDTO toDTO(Livro livro);
}