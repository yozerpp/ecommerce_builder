package me.yusuf.ecommerce_builder.demo.domain.service;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import me.yusuf.ecommerce_builder.shared.types.dto.ProductForm;
import me.yusuf.ecommerce_builder.demo.domain.repository.CategoryRepository;
import me.yusuf.ecommerce_builder.demo.domain.repository.ProductOfferRepository;
import me.yusuf.ecommerce_builder.demo.domain.repository.ProductRepository;
import me.yusuf.ecommerce_builder.demo.domain.repository.SellerRepository;
import me.yusuf.ecommerce_builder.demo.utils.Utils;
import me.yusuf.ecommerce_builder.shared.types.annotation.MethodInfo;
import me.yusuf.ecommerce_builder.shared.types.entity.*;
import me.yusuf.ecommerce_builder.shared.types.exception.NotFoundException;
import me.yusuf.ecommerce_builder.shared.types.tuple.Tuple2;
import me.yusuf.ecommerce_builder.shared.types.tuple.Tuple3;
import me.yusuf.ecommerce_builder.shared.types.tuple.Tuple4;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService extends ServiceBase {
    private final ObjectMapper objectMapper;
    private final CategoryRepository categoryRepository;
    private final SellerRepository sellerRepository;
    ProductRepository productRepository;
    ProductOfferRepository productOfferRepository;
    public ProductService( ProductRepository productRepository, ProductOfferRepository productOfferRepository, ObjectMapper objectMapper, CategoryRepository categoryRepository, SellerRepository sellerRepository) {
        this.productRepository = productRepository;
        this.productOfferRepository = productOfferRepository;
        this.objectMapper = objectMapper;
        this.categoryRepository = categoryRepository;
        this.sellerRepository = sellerRepository;
    }

    @MethodInfo(userFriendlyName = "Tüm Ürünler")
    public Page<Product> all(){
        return productRepository.findAll(Pageable.ofSize(20));
    }
    
    @MethodInfo(userFriendlyName = "Ürün Ara")
    public List<Product> search(String[] params){
        return Utils.search(Product.class, _entityManager, params);
    }
    
    @MethodInfo(userFriendlyName = "Ürün Getir")
    public Product product(Integer id){
        return productRepository.findById(id);
    }
    
    @MethodInfo(userFriendlyName = "Ürün Oluştur")
    @PreAuthorize("hasAnyRole('ROLE_SELLER','ROLE_ADMIN','ROLE_STAFF')")
    public Tuple3<Integer, Product, Category> createProduct(@RequestBody ProductForm form, Boolean cat) {
        try {
            var categories = categoryRepository.findByNameLikeIgnoreCase(form.getCategoryName());
            Category category;
            if (categories.isEmpty()) {
                category = Category.NONE;
                cat = false;
            }
            else category = categories.get(0);
            var product = new Product();
            product.setName(form.getName());
            product.setImage(form.getImage());
            product.setTaxable(true);
            product.setDescription(form.getDescription());
            product.setTags(Arrays.stream(form.getTags().split(",")).map(s -> new Tag(s, null)).collect(Collectors.toSet()));
            product.setSpecifications(objectMapper.readValue(form.getSpecifications(), java.util.Map.class));
            product.setCategory(category);
            product.setCategoryId(category.getId());
            productRepository.save(product);
            return new Tuple3<>(product.getId(), product, category);
        } catch (JacksonException e){
            throw new RuntimeException("invalid product specifications format", e);
        }
    }
    
    @MethodInfo(userFriendlyName = "Teklif Oluştur")
    public Tuple4<ProductOffer.ProductOfferId, ProductOffer, Product, Seller> createOffer(@RequestBody ProductForm form, @RequestParam(required = false, defaultValue = "null") Integer id, @RequestParam(required = false, defaultValue = "null") Integer sellerId) throws BadRequestException, NotFoundException {
        Seller seller;
        if(getUser().getAuthorities().contains(Role.ADMIN)) {
            var o  = sellerRepository.findById(sellerId);
            if (o.isEmpty()) throw new NotFoundException("No such seller with id " + sellerId);
            seller = o.get();
        } else if(getUser().getAuthorities().contains(Role.SELLER)) {
            seller = getUser().getSeller();
        } else {
            throw new BadRequestException("You don't have permission to create offers on behalf of sellers");
        }
        var product = productRepository.findById(id);
        if(product == null) throw new NotFoundException("Product not found");
        var productOffer = new ProductOffer();
        if(form.getStock() == null || form.getDiscount() == null || form.getPrice() == null || form.getDescription() == null)
            throw new BadRequestException("Stock, discount, price and description are required");
        productOffer.setSeller(seller);
        productOffer.setProduct(product);
        productOffer.setStock(form.getStock());
        productOffer.setDiscount(form.getDiscount());
        productOffer.setRegularPrice(form.getPrice());
        productOffer.setDescription(form.getDescription());
        productOffer = productOfferRepository.save(productOffer);
        return new Tuple4<>( productOffer.getId(),productOffer, product, seller);
    }
    
    @MethodInfo(userFriendlyName = "Teklif Sil")
    public Tuple4<Void,ProductOffer,Seller,Product> deleteOffer(@PathVariable int productId, @PathVariable int sellerId) throws NotFoundException {
        var ps = productOfferRepository.findWithSellerById(new ProductOffer.ProductOfferId(productId, sellerId));
        if(ps == null) throw new NotFoundException("Product offer not found");
        productOfferRepository.delete(ps);
        return new Tuple4<>(null, ps, ps.getSeller(), ps.getProduct());
    }
    
    @MethodInfo(userFriendlyName = "Teklif Güncelle")
    public Tuple4<Void,ProductOffer, Product, Seller> updateOffer(@PathVariable int productId, @PathVariable int sellerId, @RequestParam(required = false, defaultValue = "null") Integer stock, @RequestParam(required = false, defaultValue = "null") Double price, @RequestParam(required = false, defaultValue = "null") Float discount, @RequestParam(required = false, defaultValue = "null") String description) throws NotFoundException {
        var ps = productOfferRepository.findWithSellerById(new ProductOffer.ProductOfferId(productId, sellerId));
        if(ps == null) throw new NotFoundException("Product offer not found");
        if(stock != null){
            if(stock > 0)
                ps.setStock(stock);
        }
        if(price != null){
            if(price > 0)
                ps.setRegularPrice(price);
        }
        if(discount != null){
            if(discount > 0 || discount < 1)
                ps.setDiscount(discount);
        }
        if(description != null){
            ps.setDescription(description);
        }
        ps = productOfferRepository.save(ps);
        return new Tuple4<>(null, ps, ps.getProduct(), ps.getSeller());
    }
}
