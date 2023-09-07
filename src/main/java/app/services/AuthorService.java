package app.services;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.entities.Author;
import app.entities.Book;
import app.repositories.AuthorRepository;
import jakarta.transaction.Transactional;

@Service
public class AuthorService {

	@Autowired
	private AuthorRepository authorRepository;

	public Author save(Author author) {
		return authorRepository.save(author);
	}

	@Transactional
	public List<Author> findAll() {
		return authorRepository.findAll();
	}

	@Transactional
	public Author findById(Long id) {
		return authorRepository.findById(id).get();
	}

	@Transactional
	public Author update(Long id, Author obj) {
		Author author = authorRepository.getReferenceById(id);

		author.setName(obj.getName());
		return authorRepository.save(author);
	}

	@Transactional
	public void deleteById(Long id) {
		authorRepository.deleteById(id);
	}

	@Transactional
	public List<Book> getAllBooksByAuthor(Long authorId) {
		Optional<Author> authorOptional = authorRepository.findById(authorId);

		if (authorOptional.isPresent()) {
			Author author = authorOptional.get();
			List<Book> books = author.getBooks();
			return books;
		} else {

			return Collections.emptyList();
		}
	}

	@Transactional
	public Author findAuthorByName(String name) {
		Optional<Author> authorOptional = authorRepository.findByName(name);
		return authorOptional.orElse(null);
	}

}
