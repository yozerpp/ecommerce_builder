package me.yusuf.ecommerce_builder.demo.domain.network;

import me.yusuf.ecommerce_builder.shared.types.entity.Product;
import me.yusuf.ecommerce_builder.shared.types.dto.ProductForm;
import me.yusuf.ecommerce_builder.demo.domain.service.ProductService;
import me.yusuf.ecommerce_builder.shared.types.exception.NotFoundException;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import static me.yusuf.ecommerce_builder.demo.domain.network.ControllerBase.basePath;

@RestController
@RequestMapping(basePath)
public class ProductController extends ControllerBase {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/product")
    public ResponseEntity<Page<Product>> mainPage(){
        Page<Product> products = productService.all();
        return ResponseEntity.ok(products);
    }

    @GetMapping("product/{id}")
    public ResponseEntity<?> detail(@PathVariable Integer id){
        var pr = productService.product(id);
        if(pr == null) 
            return ResponseEntity.status(404).body(Map.of("message", "Product not found"));
        return ResponseEntity.ok(pr);
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam String query){
        String[] parameters = query.split("&");
        var products = productService.search(parameters);
        return ResponseEntity.ok(Map.of("products", products));
    }

    @PostMapping("/product")
    public Map<String, Object> create(@RequestBody ProductForm productForm){
        Map<String, Object> response = new HashMap<>();
        Boolean cat = false;
        var id = productService.createProduct(productForm, cat)._1();
        String message = createdMessage(id, "/product") + (cat
                ? "\nProduct has been assigned to no category because no category label with: " + productForm.getCategoryName() + " was found"
                : "");
        response.put("message", message);
        return response;
    }

    @PostMapping("/product/{id}")
    public Map<String, Object> createOffer(@RequestBody ProductForm form,
                                             @PathVariable Integer id,
                                             @RequestParam Integer sellerId){
        Map<String, Object> response = new HashMap<>();
        try {
            var i = this.productService.createOffer(form, id, sellerId)._1();
            response.put("message", createdMessage(i, "/offer"));
        } catch (BadRequestException | NotFoundException e) {
            response.put("message", e.getMessage());
        }
        return response;
    }

    @PutMapping("/offer/{productId}/{sellerId}")
    public Map<String, Object> updateOffer(@PathVariable int productId,
                                             @PathVariable int sellerId,
                                             @RequestParam(required = false) Integer stock,
                                             @RequestParam(required = false) Double price,
                                             @RequestParam(required = false) Float discount,
                                             @RequestParam(required = false) String description){
        Map<String, Object> response = new HashMap<>();
        try {
            this.productService.updateOffer(productId, sellerId, stock, price, discount, description);
            response.put("message", "Offer updated successfully");
        } catch (NotFoundException e){
            response.put("message", e.getMessage());
        }
        return response;
    }

    @DeleteMapping("/offer/{productId}/{sellerId}")
    public Map<String, Object> deleteOffer(@PathVariable int productId,
                                             @PathVariable int sellerId) {
        Map<String, Object> response = new HashMap<>();
        try {
            this.productService.deleteOffer(productId, sellerId);
            response.put("message", "Offer deleted successfully");
        } catch (NotFoundException e) {
            response.put("message", e.getMessage());
        }
        return response;
    }
}
