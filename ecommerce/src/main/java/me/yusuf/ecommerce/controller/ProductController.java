package me.yusuf.ecommerce.controller;

import me.yusuf.ecommerce.domain.product.Product;
import me.yusuf.ecommerce.domain.product.ProductForm;
import me.yusuf.ecommerce.domain.product.ProductService;
import me.yusuf.ecommerce.utils.exception.NotFoundException;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller("productController")
public class ProductController extends ControllerBase{
    private final ProductService productService;
    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    @GetMapping("/")
    public String mainPage(Model model){
        Page<Product> products = productService.all();
        model.addAttribute("products", products);
        return "index";
    }
    @GetMapping("product/{id}")
    public String detail(@PathVariable Integer id, Model model){
        var pr = productService.product(id);
        if(pr==null) return "redirect:/404";
        model.addAttribute("product", pr);
        return "product";
    }
    @GetMapping("/search")
    public String search(@RequestParam String query, Model model){
        String[] parameters = query.split("&");
        var products = productService.search(parameters);
        model.addAttribute("products", products);
        return "index";
    }
    @PostMapping("/product")
    public void create(@RequestBody ProductForm productForm, Model model){
        Boolean cat = false;
        var id = productService.createProduct(productForm, cat);
        model.addAttribute("message",createdMessage(id,"/product") +(cat?'\n' +"Product has been assigned to no category because no category label with: " + productForm.getCategoryName() + " was found" :""));
    }
    @PostMapping("/product/{id}")
    public void createOffer(@RequestBody ProductForm form, @PathVariable Integer id, @RequestParam Integer sellerId, Model model){
        try {
            var i = this.productService.createOffer(form, id, sellerId);
            model.addAttribute("message", createdMessage(i, "/offer"));
        } catch (BadRequestException | NotFoundException e) {
            model.addAttribute("message", e.getMessage());
        }
    }
    @PutMapping("/offer/{productId}/{sellerId}")
    public void updateOffer(Model model,@PathVariable int productId, @PathVariable int sellerId, @RequestParam(required = false, defaultValue = "null") Integer stock, @RequestParam(required = false, defaultValue = "null") Double price, @RequestParam(required = false, defaultValue = "null") Float discount,@RequestParam(required = false,defaultValue = "null") String description){
        try {
            this.productService.updateOffer(productId, sellerId, stock, price, discount, description);
        } catch (NotFoundException e){
            model.addAttribute("message", e.getMessage());
        }
    }
    @DeleteMapping("/offer/{productId}/{sellerId}")
    public void deleteOffer(Model model, @PathVariable int productId, @PathVariable int sellerId) {
        try {
            this.productService.deleteOffer(productId, sellerId);
        } catch (NotFoundException e) {
            model.addAttribute("message", e.getMessage());
        }
    }
}