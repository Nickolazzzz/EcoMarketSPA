package cl.ecomarket.spa.order_service.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ProductoClientRest {

    private final RestTemplate restTemplate;

    @Value("${product.service.url}")
    private String productServiceUrl;

    public ProductoClientRest(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<String> reducirStock(Long id, int cantidad) {
        String url = String.format("%s/productos/%d/reducir-stock?cantidad=%d", productServiceUrl, id, cantidad);
        return restTemplate.exchange(url, HttpMethod.PUT, null, String.class);
    }
}
