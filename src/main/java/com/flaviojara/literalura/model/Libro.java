package com.flaviojara.literalura.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(unique = true)
    private String titulo;
    private String idiomas;
    private Double numeroDeDescargas;
    @ManyToOne
    private Autor autor;

    public Libro() {
    }

    public Libro(DatosLibros datosLibros) {
        this.titulo = datosLibros.titulo();
        this.numeroDeDescargas = datosLibros.numeroDeDescargas();
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(String idiomas) {
        this.idiomas = idiomas;
    }

    public Double getNumeroDeDescargas() {
        return numeroDeDescargas;
    }

    public void setNumeroDeDescargas(Double numeroDeDescargas) {
        this.numeroDeDescargas = numeroDeDescargas;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    @Override
    public String toString() {
        return "\n----------------------------------\n" +
                "titulo: " + titulo + "\n" +
                autor + "\n" +
                "numero de descargas: " + numeroDeDescargas +
                "\n----------------------------------\n";
    }
}
