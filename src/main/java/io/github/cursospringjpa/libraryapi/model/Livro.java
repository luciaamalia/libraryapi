package io.github.cursospringjpa.libraryapi.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Entity
@Table(name = "livro")
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "titulo" , length = 150, nullable = false)
    private String titulo;

    @Column(name = "dataPublicacao")
    private LocalDate dataPublicacao;

    @Enumerated(EnumType.STRING)
    @Column(name = "genero", length = 30, nullable = false)
    private GeneroLivro genero;

    @Column(name = "preco", precision = 18, scale = 2)
    private Double preco;

    @ManyToOne
    @JoinColumn(name = "id_colum")
    private Autor autor;
}
