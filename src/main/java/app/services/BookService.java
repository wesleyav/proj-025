package app.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.entities.Author;
import app.entities.Book;
import app.repositories.AuthorRepository;
import app.repositories.BookRepository;
import jakarta.transaction.Transactional;

@Service
public class BookService {

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private AuthorRepository authorRepository;

	@Transactional
	public Book save(Book book) {
		return bookRepository.save(book);
	}

	@Transactional
	public List<Book> findAll() {
		return bookRepository.findAll();
	}

	@Transactional
	public Book findById(Long id) {
		return bookRepository.findById(id).get();
	}

	@Transactional
	public Book update(Long id, Book obj) {
		Book book = bookRepository.getReferenceById(id);

		book.setTitle(obj.getTitle());
		return bookRepository.save(book);
	}

	@Transactional
	public void deleteById(Long id) {
		bookRepository.deleteById(id);
	}

	@Transactional
	public void addBookWithAuthor(String name, Book newBook) {
		Optional<Author> authorOptional = authorRepository.findByName(name);

		if (authorOptional.isPresent()) {
			Author author = authorOptional.get();
			newBook.setAuthor(author);
		} else {
			Author newAuthor = new Author();
			newAuthor.setName(name);
			newBook.setAuthor(newAuthor);
		}
		bookRepository.save(newBook);
	}
}
