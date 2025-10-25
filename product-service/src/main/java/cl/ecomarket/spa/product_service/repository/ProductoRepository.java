package cl.ecomarket.spa.product_service.repository;

import cl.ecomarket.spa.product_service.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    // Spring Data JPA crea automáticamente los métodos CRUD (save, findById, findAll, etc.)
}

