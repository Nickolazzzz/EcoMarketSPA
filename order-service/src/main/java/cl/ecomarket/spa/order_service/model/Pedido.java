package cl.ecomarket.spa.order_service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Long idCliente; // Simulado, vendría del servicio de usuarios
    private Long idProducto;
    private int cantidad;
    private String estado; // PENDIENTE, COMPLETADO, CANCELADO
    private Date fechaCreacion;

    public Pedido(Long idCliente, Long idProducto, int cantidad) {
        this.idCliente = idCliente;
        this.idProducto = idProducto;
        this.cantidad = cantidad;
        this.estado = "PENDIENTE";
        this.fechaCreacion = new Date();
    }
}
