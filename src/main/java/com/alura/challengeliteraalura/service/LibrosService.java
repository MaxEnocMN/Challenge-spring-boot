package com.alura.challengeliteraalura.service;
import com.alura.challengeliteraalura.api.GutendexApi;
import com.alura.challengeliteraalura.modelo.Autor;
import com.alura.challengeliteraalura.modelo.Libro;
import com.alura.challengeliteraalura.repository.AutorRepository;
import com.alura.challengeliteraalura.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class LibrosService {
    @Autowired
    private LibroRepository libroRepository;
    @Autowired
    private AutorRepository autorRepository;
    private final GutendexApi gutendexApi;
    private List<Libro> libros;

    public LibrosService() throws Exception {
        this.gutendexApi = new GutendexApi();
        this.libros = obtenerLibros();
    }

    public List<Libro> buscarLibrosPorTitulo(String titulo) throws Exception {
        return libroRepository.findByTitulo(titulo);
    }

    public List<Libro> buscarLibrosPorAutor(String nombreAutor) throws Exception {
        return libroRepository.findByAutores_Nombre(nombreAutor);
    }

    public List<Libro> buscarLibrosPorIdioma(String idioma) throws Exception {
        return libroRepository.findByIdiomasContaining(idioma);
    }

    public List<Libro> obtenerLibros() throws Exception {
        return gutendexApi.obtenerLibros();
    }

    public long contarLibrosPorIdioma(String idioma) throws Exception {
        return libroRepository.findByIdiomasContaining(idioma).size();
    }

    public List<Autor> listarAutores() {
        return autorRepository.findAll();
    }

    public void listarAutoresVivos(int año) {
        List<Autor> autores = autorRepository.findAutoresVivos(año);
        for (Autor autor : autores) {
            System.out.println(autor.getNombre());
        }
    }
}