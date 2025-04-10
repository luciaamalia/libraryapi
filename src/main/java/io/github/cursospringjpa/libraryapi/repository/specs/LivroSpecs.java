package io.github.cursospringjpa.libraryapi.repository.specs;

import io.github.cursospringjpa.libraryapi.model.GeneroLivro;
import io.github.cursospringjpa.libraryapi.model.Livro;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

public class LivroSpecs {

    //retorna se o isbn é igual ao parametro de comparacao
    public static Specification<Livro> isbnEqual( String isbn){
        return (root, query,  cb) -> cb.equal(root.get("isbn"), isbn);
    }

    public static Specification<Livro> tituloLike (String titulo){
        //
        return (root, query, cb) ->
        cb.like( cb.upper(root.get("titulo")),"%" + titulo.toUpperCase() + "%");
    }

    public static Specification<Livro> generoEqual(GeneroLivro genero){
        return (root, query, cb) -> cb.equal(root.get("genero"),genero);


    }

    public static Specification<Livro> anoPublicacaoEqual(Integer anoPublicacao){
        //to_char (data_publicacao, 'DD/MM/YYYY') = :anoPublicacao
        return (root, query, cb) -> cb.equal(
                cb.function("to_char", String.class,
                        root.get("dataPublicacao"), cb.literal("YYYY")) ,anoPublicacao.toString());


    }   public static Specification<Livro> nomeAutorLike(String nome){
        //to_char (data_publicacao, 'DD/MM/YYYY') = :anoPublicacao
        return (root, query, cb) -> {
//
            Join<Object, Object> joinAutor = root.join("autor", JoinType.LEFT);
            return  cb.like(cb.upper(joinAutor.get("nome")), "%" + nome.toUpperCase() + "%");

//           return cb.like( cb.upper(root.get("autor").get("nome")), "%" + nome.toUpperCase() + "%");
        };
    }


}
