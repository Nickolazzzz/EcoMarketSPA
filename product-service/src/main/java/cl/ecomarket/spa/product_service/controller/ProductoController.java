package cl.ecomarket.spa.product_service.controller;

import cl.ecomarket.spa.product_service.model.Producto;
import cl.ecomarket.spa.product_service.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/productos") // Esta es la ruta base DENTRO del microservicio
public class ProductoController {

    @Autowired
    private ProductoRepository productoRepository;

    @GetMapping
    public List<Producto> getAllProductos() {
        return productoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> getProductoById(@PathVariable Long id) {
        Optional<Producto> producto = productoRepository.findById(id);
        return producto.map(ResponseEntity::ok)
                       .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    // Endpoint para crear un producto (para poblar la BD)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Producto createProducto(@RequestBody Producto producto) {
        return productoRepository.save(producto);
    }

    // Endpoint clave que será consumido por el servicio de pedidos
    @PutMapping("/{id}/reducir-stock")
    public ResponseEntity<String> reducirStock(@PathVariable Long id, @RequestParam int cantidad) {
        Optional<Producto> optionalProducto = productoRepository.findById(id);

        if (!optionalProducto.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto no encontrado");
        }

        Producto producto = optionalProducto.get();

        if (producto.getStock() < cantidad) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Stock insuficiente");
        }

        producto.setStock(producto.getStock() - cantidad);
        productoRepository.save(producto);
        return ResponseEntity.ok("Stock actualizado");
    }
}

