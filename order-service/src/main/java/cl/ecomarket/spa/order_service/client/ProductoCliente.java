package cl.ecomarket.spa.order_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

// "product-service" es un nombre lógico, 'url' es la dirección física
// Usamos "${product.service.url}" para leerlo desde application.properties
@FeignClient(name = "product-service", url = "${product.service.url}")
public interface ProductoCliente {

    // Esta firma debe coincidir con el método en ProductoController
    @PutMapping("/productos/{id}/reducir-stock")
    ResponseEntity<String> reducirStock(@PathVariable("id") Long id, @RequestParam("cantidad") int cantidad);
}
