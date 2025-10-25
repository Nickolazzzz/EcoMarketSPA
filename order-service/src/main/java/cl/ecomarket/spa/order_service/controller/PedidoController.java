package cl.ecomarket.spa.order_service.controller;

import cl.ecomarket.spa.order_service.client.ProductoCliente;
import cl.ecomarket.spa.order_service.model.Pedido;
import cl.ecomarket.spa.order_service.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos") // Ruta base DENTRO de este microservicio
public class PedidoController {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ProductoCliente productoCliente; // Inyectamos el cliente Feign

    @PostMapping
    public ResponseEntity<Pedido> crearPedido(@RequestBody Pedido pedidoRequest) {
        
        // --- INICIO DE COMUNICACIÓN ENTRE SERVICIOS ---
        // 1. Llamar al servicio de productos para reducir el stock
        try {
            ResponseEntity<String> response = productoCliente.reducirStock(
                pedidoRequest.getIdProducto(), 
                pedidoRequest.getCantidad()
            );

            // Si el servicio de productos responde con un error (ej: 400 por falta de stock)
            if (response.getStatusCode() != HttpStatus.OK) {
                // Podríamos manejar diferentes errores, pero por simplicidad lo agrupamos
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); 
            }

        } catch (Exception e) {
            // Captura errores si el servicio de productos está caído o no encuentra el producto
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(null);
        }
        // --- FIN DE COMUNICACIÓN ENTRE SERVICIOS ---

        // 2. Si todo salió bien, guardamos el pedido
        Pedido nuevoPedido = new Pedido(
            pedidoRequest.getIdCliente(),
            pedidoRequest.getIdProducto(),
            pedidoRequest.getCantidad()
        );
        nuevoPedido.setEstado("COMPLETADO"); // Marcamos como completado
        pedidoRepository.save(nuevoPedido);

        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPedido);
    }

    @GetMapping("/cliente/{idCliente}")
    public List<Pedido> getPedidosPorCliente(@PathVariable Long idCliente) {
        return pedidoRepository.findByIdCliente(idCliente);
    }
}

