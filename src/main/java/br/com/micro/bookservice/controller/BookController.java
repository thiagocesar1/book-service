package br.com.micro.bookservice.controller;

import br.com.micro.bookservice.model.Book;
import br.com.micro.bookservice.proxy.CambioProxy;
import br.com.micro.bookservice.repository.BookRepository;
import br.com.micro.bookservice.response.Cambio;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Tag(name = "Book Endpoints")
@RestController
@RequestMapping("book-service")
public class BookController {

    @Autowired
    private Environment environment;

    @Autowired
    private BookRepository repository;

    @Autowired
    private CambioProxy cambioProxy;

    @Operation(summary = "Find book")
    @GetMapping(value="/{id}/{currency}")
    //@Retry(name = "default", fallbackMethod = "fallBackMethod")
    //@CircuitBreaker(name = "default", fallbackMethod = "fallBackMethod")
    @RateLimiter(name = "default")
    public Book findBook(@PathVariable("id") Long id,
                         @PathVariable("currency") String currency){

        String port = environment.getProperty("local.server.port");
        Optional<Book> book = repository.findById(id);
        if(book.isEmpty()) throw new RuntimeException("Book not found");

        Cambio cambio = cambioProxy.getCambio(book.get().getPrice(), "USD", currency);

        book.get().setEnvironment("Book port: " + port + " Cambio Port: " + cambio.getEnvironment());
        book.get().setCurrency(currency);
        book.get().setPrice(cambio.getConvertedValue());

        return book.get();
    }
}
