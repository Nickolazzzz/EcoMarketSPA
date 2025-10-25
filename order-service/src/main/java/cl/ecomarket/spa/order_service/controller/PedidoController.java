package cl.ecomarket.spa.order_service.controller;

import cl.ecomarket.spa.order_service.client.ProductoClientRest;
import cl.ecomarket.spa.order_service.model.Pedido;
import cl.ecomarket.spa.order_service.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos") // Ruta base
public class PedidoController {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ProductoClientRest productoCliente; // Cliente REST para comunicarse con product-service

    @PostMapping
    public ResponseEntity<Pedido> crearPedido(@RequestBody Pedido pedidoRequest) {
        
        // --- INICIO DE COMUNICACIÓN ENTRE SERVICIOS ---
        //lamar al servicio de productos para reducir stock
        try {
            ResponseEntity<String> response = productoCliente.reducirStock(
                pedidoRequest.getIdProducto(), 
                pedidoRequest.getCantidad()
            );

            // if servicio de productos responde con un error
            if (response.getStatusCode() != HttpStatus.OK) {
                // manejar diferentes errores
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); 
            }

        } catch (Exception e) {
            // errores si el servicioestá caído o no encuentra
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(null);
        }
        // --- FIN DE COMUNICACIÓN ENTRE SERVICIOS 

        //, guardamos el pedido
        Pedido nuevoPedido = new Pedido(
            pedidoRequest.getIdCliente(),
            pedidoRequest.getIdProducto(),
            pedidoRequest.getCantidad()
        );
        nuevoPedido.setEstado("COMPLETADO"); //marcamos completado
        pedidoRepository.save(nuevoPedido);

        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPedido);
    }

    @GetMapping("/cliente/{idCliente}")
    public List<Pedido> getPedidosPorCliente(@PathVariable Long idCliente) {
        return pedidoRepository.findByIdCliente(idCliente);
    }
}

