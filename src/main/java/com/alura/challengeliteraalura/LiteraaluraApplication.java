package com.alura.challengeliteraalura;
import com.alura.challengeliteraalura.modelo.Autor;
import com.alura.challengeliteraalura.modelo.Libro;
import com.alura.challengeliteraalura.service.LibrosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class LiteraaluraApplication implements CommandLineRunner {
    @Autowired
    private LibrosService librosService;

	public static void main(String[] args) {
		SpringApplication.run(LiteraaluraApplication.class, args);
	}

	@Override
	public void run(String...args) throws Exception{
		Menu();
	}

	public void Menu() throws Exception {
		Scanner teclado = new Scanner(System.in);
		int opcion = 0;
		String menu = """
                **********************************************
                \nBinvenido/a LiteraAlura 
                \n1) Buscar libro por título.
                2) Listar libros registrados.
                3) Listar autores registrados.
                4) Listar autores vivos en un determinado año.
                5) Listar libros por idioma.
				6) Salir.
				\n Seleccione un número de las opciones anteriores:
				""";

		while (opcion !=6){
			System.out.println(menu);
			opcion = teclado.nextInt();
            teclado.nextLine();

			switch (opcion) {
				case 1:
					System.out.println("Escriba el titulo del libro que desea buscar: ");
					String titulo = teclado.nextLine();
					try {
						List<Libro> libros = librosService.buscarLibrosPorTitulo(titulo);
						for (Libro libro : libros) {
							System.out.println("Título: " + libro.getTitulo());
							System.out.println("Autores: " + libro.getAutores());
							System.out.println("Idiomas: " + libro.getIdiomas());
							System.out.println("Número de descargas: " + libro.getNumeroDeDescargas());
						}
					} catch (Exception e) {
						System.out.println("Error al buscar el libro: " + e.getMessage());
					}
					break;
				case 2:
				System.out.println("Estos son los libros registrados hasta el momento: ");
				try {
					List<Libro> libros = librosService.obtenerLibros();
					for (Libro libro : libros) {
						System.out.println("Título: " + libro.getTitulo());
						System.out.println("Autores: " + libro.getAutores());
						System.out.println("Idiomas: " + libro.getIdiomas());
						System.out.println("Número de descargas: " + libro.getNumeroDeDescargas());
					}
				} catch (Exception e) {
					System.out.println("Error al listar los libros: " + e.getMessage());
				}
				break;
                case 3:
                    System.out.println("Estos son los autores registrados hasta el momento: ");
                    try {
                        List<Autor> autores = librosService.listarAutores();
                        for (Autor autor : autores) {
                            System.out.println(autor.getNombre());
                        }
                    } catch (Exception e) {
                        System.out.println("Error al listar los autores: " + e.getMessage());
                    }
                    break;
				case 4:
					System.out.println("Ingrese el año que desea consultar: ");
					int año = teclado.nextInt();
					try {
						librosService.listarAutoresVivos(año);
					} catch (Exception e) {
						System.out.println("Error al listar los autores vivos: " + e.getMessage());
					}
					break;
				case 5:
					System.out.println("Escriba el idioma que desea consultar: ");
					String idioma = teclado.nextLine();
					try {
						long cantidad = librosService.contarLibrosPorIdioma(idioma);
						System.out.println("Hay " + cantidad + " libros en el idioma " + idioma);
					} catch (Exception e) {
						System.out.println("Error al contar los libros: " + e.getMessage());
					}
					break;
				case 6:
					System.out.println("Gracias por usar nuestra biblioteca LiteraAlura, que tenga un feliz día (^-^).");
					break;
				default:
					System.out.println("La opción que has seleccionado no es correcta, por favor de verificar.");
			}
		}
		teclado.close();
	}
}