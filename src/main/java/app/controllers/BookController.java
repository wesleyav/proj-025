package app.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.entities.Author;
import app.entities.Book;
import app.services.AuthorService;
import app.services.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value = "/api/v1")
@Tag(name = "Book")
public class BookController {

	@Autowired
	private BookService bookService;

	@Autowired
	private AuthorService authorService;

	@PostMapping(value = "/books", produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Endpoint para adicionar um livro com autor")
	public ResponseEntity<String> addBookWithAuthor(@RequestBody Map<String, Object> requestBody) {
		try {
			String name = (String) requestBody.get("name");
			String title = (String) requestBody.get("title");

			if (name != null && !name.isEmpty()) {
				Author author = authorService.findAuthorByName(name);

				if (author == null) {
					author = new Author();
					author.setName(name);
					authorService.save(author);
				}

				Book newBook = new Book();
				newBook.setTitle(title);
				newBook.setAuthor(author);

				bookService.addBookWithAuthor(newBook.getAuthor().getName(), newBook);

				return ResponseEntity.ok("Livro adicionado com sucesso.");
			} else {
				return ResponseEntity.badRequest().body("Nome do autor não pode ser nulo ou vazio.");
			}
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Erro ao adicionar o livro: " + e.getMessage());
		}
	}

	@GetMapping(value = "/books", produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Endpoint para listar todos os books")
	public ResponseEntity<List<Book>> findAll() {
		List<Book> books = bookService.findAll();
		return new ResponseEntity<List<Book>>(books, HttpStatus.OK);
	}

	@GetMapping(value = "/books/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Endpoint para buscar book por id")
	public ResponseEntity<Book> findById(@PathVariable Long id) {
		Book book = bookService.findById(id);
		return new ResponseEntity<Book>(book, HttpStatus.OK);
	}

	@PutMapping(value = "/books/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Endpoint para atualizar um book")
	public ResponseEntity<Book> update(@PathVariable Long id, @RequestBody Book obj) {
		Book book = bookService.update(id, obj);
		return new ResponseEntity<Book>(book, HttpStatus.OK);
	}

	@DeleteMapping(value = "/books/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Endpoint para remover um book por id")
	public ResponseEntity<Void> deleteById(@PathVariable Long id) {
		bookService.deleteById(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

}
