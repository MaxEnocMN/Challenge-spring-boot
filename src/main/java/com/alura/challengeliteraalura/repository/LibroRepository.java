package com.alura.challengeliteraalura.repository;
import com.alura.challengeliteraalura.modelo.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LibroRepository extends JpaRepository<Libro, Long> {
    List<Libro> findByTitulo(String titulo);
    List<Libro> findByAutores_Nombre(String nombreAutor);
    List<Libro> findByIdiomasContaining(String idioma);
}