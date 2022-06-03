package br.com.micro.bookservice.controller;

import br.com.micro.bookservice.model.Book;
import br.com.micro.bookservice.proxy.CambioProxy;
import br.com.micro.bookservice.repository.BookRepository;
import br.com.micro.bookservice.response.Cambio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;

@RestController
@RequestMapping("book-service")
public class BookController {

    @Autowired
    private Environment environment;

    @Autowired
    private BookRepository repository;

    @Autowired
    private CambioProxy cambioProxy;

    @GetMapping(value="/{id}/{currency}")
    public Book findBook(@PathVariable("id") Long id,
                         @PathVariable("currency") String currency){

        String port = environment.getProperty("local.server.port");
        Optional<Book> book = repository.findById(id);
        if(book.isEmpty()) throw new RuntimeException("Book not found");

        Cambio cambio = cambioProxy.getCambio(book.get().getPrice(), "USD", currency);

        book.get().setEnvironment(port);
        book.get().setCurrency(currency);
        book.get().setPrice(cambio.getConvertedValue());

        return book.get();
    }
}
