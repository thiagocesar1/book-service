package br.com.micro.bookservice.controller;

import br.com.micro.bookservice.model.Book;
import br.com.micro.bookservice.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Date;

@RestController
@RequestMapping("book-service")
public class BookController {

    @Autowired
    private Environment environment;

    @Autowired
    private BookRepository repository;

    @GetMapping(value="/{id}/{currency}")
    public Book findBook(@PathVariable("id") Long id,
                         @PathVariable("currency") String currency){

        String port = environment.getProperty("local.server.port");
        Book book = repository.getReferenceById(id);
        if(book == null) throw new RuntimeException("Book not found");

        book.setEnvironment(port);

        return book;
    }
}