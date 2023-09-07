package app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.entities.Author;
import app.services.AuthorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value = "/api/v1")
@Tag(name = "Author")
public class AuthorController {

	@Autowired
	private AuthorService authorService;

	@GetMapping(value = "/authors", produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Endpoint para listar todos os authors")
	public ResponseEntity<List<Author>> findAll() {
		List<Author> authors = authorService.findAll();
		return new ResponseEntity<List<Author>>(authors, HttpStatus.OK);
	}

	@GetMapping(value = "/authors/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Endpoint para buscar author por id")
	public ResponseEntity<Author> findById(@PathVariable Long id) {
		Author author = authorService.findById(id);
		return new ResponseEntity<Author>(author, HttpStatus.OK);
	}

	@PutMapping(value = "/authors/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Endpoint para atualizar um authors")
	public ResponseEntity<Author> update(@PathVariable Long id, @RequestBody Author obj) {
		Author author = authorService.update(id, obj);
		return new ResponseEntity<Author>(author, HttpStatus.OK);
	}

	@DeleteMapping(value = "/authors/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Endpoint para remover um author por id")
	public ResponseEntity<Void> deleteById(@PathVariable Long id) {
		authorService.deleteById(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

}
