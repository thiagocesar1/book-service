package br.com.micro.bookservice.proxy;

import br.com.micro.bookservice.response.Cambio;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;

@FeignClient(name = "cambio-service", url = "localhost:8765")
public interface CambioProxy {
    @GetMapping(value="/cambio-service/{amount}/{from}/{to}")
    Cambio getCambio(@PathVariable("amount") BigDecimal amount,
                     @PathVariable("from") String from,
                     @PathVariable("to") String to);
}
