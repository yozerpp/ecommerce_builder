package me.yusuf.ecommerce_builder.demo;

import com.github.javafaker.Faker;
import jakarta.persistence.*;
import me.yusuf.ecommerce_builder.shared.types.entity.Cart;
import me.yusuf.ecommerce_builder.shared.types.entity.CartItem;
import me.yusuf.ecommerce_builder.shared.types.entity.Category;
import me.yusuf.ecommerce_builder.shared.types.entity.Coupon;
import me.yusuf.ecommerce_builder.shared.types.entity.Order;
import me.yusuf.ecommerce_builder.shared.types.entity.Payment;
import me.yusuf.ecommerce_builder.shared.types.entity.Product;
import me.yusuf.ecommerce_builder.shared.types.entity.ProductOffer;
import me.yusuf.ecommerce_builder.shared.types.entity.Role;
import me.yusuf.ecommerce_builder.shared.types.entity.Seller;
import me.yusuf.ecommerce_builder.shared.types.entity.Session;
import me.yusuf.ecommerce_builder.shared.types.entity.Shipment;
import me.yusuf.ecommerce_builder.shared.types.entity.Tag;
import me.yusuf.ecommerce_builder.shared.types.entity.User;
import me.yusuf.ecommerce_builder.shared.types.entity.embeddable.Address;
import me.yusuf.ecommerce_builder.shared.types.entity.embeddable.PhoneNumber;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.net.HttpURLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.IntStream;

@Component
public class EcommerceDatabaseInitializer {
    static private final Faker faker = new Faker(Locale.forLanguageTag("tr-TR"));
    @PersistenceContext(unitName = "demoEntityManagerFactory")
    EntityManager entityManager;

    public EcommerceDatabaseInitializer() {
    }
    @Transactional(transactionManager = "demoTransactionManager")
    void initializeDatabase() {
        entityManager.setFlushMode(FlushModeType.AUTO);
        doRole();
        var categories = doCategory();
        System.out.println("Categories: " + categories.size());

        var tags = doTags();
        System.out.println("Tags: " + tags.size());
        var carts = doCarts();
        System.out.println("Carts: " + carts.size());
        var sessions = doSessions(carts);
        System.out.println("Sessions: " + sessions.size());
        var users = doUser(sessions);
        System.out.println("Users: " + users.size());
        var sellers = doSeller(users);
        System.out.println("Sellers: " + sellers.size());
        var products = doProduct(sellers.size(), categories, tags);
        System.out.println("Products: " + products.size());
        var offers = doProductOffer(products, sellers);
        System.out.println("Offers: " + offers.size());
        var coupons = doCoupons(sellers);
        System.out.println("Coupons: " + coupons.size());
        var items = doCartItem(carts, offers);
        System.out.println("Cart Items: " + items.size());
        var orders = doOrder(users);
        System.out.println("Orders: " + orders.size());
    }
    void save(Object o){
        entityManager.persist(o);
    }
    
    List<Cart> doCarts(){

        var result = IntStream.range(0, NUM_SESSION).sequential().mapToObj(i -> {
            Cart cart = new Cart();
            cart.setTotal(0.0);
            cart.setItem_count(0);
            cart.setOrdered(false);
            save(cart);
            return cart;
        }).toList();
        return result;
    }

    

    List<Session> doSessions(List<Cart> carts){

        var result = IntStream.range(0, carts.size()).sequential().mapToObj(i -> {
            Cart cart = carts.get(i);
            Session session = new Session();
            session.setId(java.util.UUID.randomUUID().toString());
            session.setCartId(cart.getId());
            session.setCart(cart);
            save(session);
            return session;
        }).toList();
        return result;
    }
    private final Map<String, Role> roleCache = new HashMap<>();
    

    void doRole(){
        try{
            Role adminRole = new Role("ROLE_ADMIN","admin");
            Role userRole = new Role("ROLE_USER","user");
            Role sellerRole = new Role("ROLE_SELLER","seller");
            Role staffRole = new Role("ROLE_STAFF","staff");
            entityManager.persist(adminRole);
            entityManager.persist(userRole);
            entityManager.persist(sellerRole);
            entityManager.persist(staffRole);
                // Cache the roles for reuse
            roleCache.put("ROLE_ADMIN", adminRole);
            roleCache.put("ROLE_USER", userRole);
            roleCache.put("ROLE_SELLER", sellerRole);
            roleCache.put("ROLE_STAFF", staffRole);
        } catch (Exception e) {
            // If roles already exist, we still need to populate the cache
            roleCache.put("ROLE_ADMIN", new Role("ROLE_ADMIN","admin"));
            roleCache.put("ROLE_USER", new Role("ROLE_USER","user"));
            roleCache.put("ROLE_SELLER", new Role("ROLE_SELLER","seller"));
            roleCache.put("ROLE_STAFF", new Role("ROLE_STAFF","staff"));
        }
    }    

    List<User> doUser(final List<Session> sessions){
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        try(var log = Files.newOutputStream(Path.of("./user_logs.txt"));var logger = new PrintWriter(log)) {
            return IntStream.range(0, NUM_USER).sequential().mapToObj(i ->{
                var user = new User();
                user.setFirstName(faker.name().firstName());
                user.setLastName(faker.name().lastName());
                user.setUsername("user" + i + "@" + faker.internet().domainName());
                user.setPassword(encoder.encode(faker.internet().password()));
                user.setAddress(fakeAddress());
                var session = sessions.get(i);
                user.setActiveSession(session.getId());
                user.setActiveSessionRef(session);
                user.setPhoneNumber(fakePhoneNumber());
                user.setEnabled(true);
                user.setEmailVerified(false);
                user.setPhoneVerified(false);
                user.setProfileImage(fakeImage());
                try {
                    save(user);
                    
                    // Use the cached role instead of creating a new one
                    Role userRole = roleCache.get("ROLE_USER");
                    user.getAuthorities().add(userRole);
                    entityManager.merge(user);
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
                return user;
            }).filter(Objects::nonNull).toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }    

    List<Seller> doSeller(final List<User> users)  {

        try (var log = Files.newOutputStream(Path.of("./seller_log.txt")); var logger = new PrintWriter(log)){
          var result = IntStream.range(0, users.size() / 10).sequential().mapToObj(i ->{
                var user = users.get(i*10);
                var seller = new Seller();
                seller.setShopName(faker.company().name());
                seller.setShopAddress(fakeAddress());
                seller.setUserId(user.getId());
                seller.setUser(user);
                try{
                    save(seller);
                    
                    // Add seller role to the user
                    Role sellerRole = roleCache.get("ROLE_SELLER");
                    user.getAuthorities().add(sellerRole);
                    entityManager.merge(user);
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
                return  seller;
            }).filter(Objects::nonNull).toList();
                return result;
        } catch(IOException e){
            throw new RuntimeException(e);
        }
    }
//    void addCategories(List<Category> categories,Category parent, int level){
//        if(level == 0 ) {
//            categories.add(parent);
//            return;
//        }
//        for (int i=0 ; i < 4 ; i++){
////            var category = new Category();
////            category.setName(faker.commerce().productName());
////            category.setParentCategory(parent);
////            if(parent!=null)
////                category.setParentId(parent.getId());
////            category.setDescription(faker.lorem().characters(0,255));
////            entityManager.persist(category);
//            addCategories(categories,category, level-1);
//        }
//    }    


List<Category> doCategory(){
        var ret = new ArrayList<Category>();

        for (int i=0; i <20; i++) {
            var category = new Category();
            category.setName(faker.commerce().productName());
            category.setDescription(faker.lorem().characters(0, 255));
            entityManager.persist(category);
            ret.add(category);
        }
        return ret;
    }    

    List<Tag> doTags(){

        var ret = IntStream.range(0, NUM_TAGS).sequential().mapToObj(i ->{

            var tag = new Tag();
            tag.setName(faker.internet().uuid().substring(0,32));
            try {
                save(tag);
            } catch (Exception e) {e.printStackTrace();return null;}
            return  tag;
        }).filter(Objects::nonNull).toList();
        return ret;
    }    

    List<Product> doProduct(final int sellerSize, final List<Category> categories,List<Tag> tags)  {
        try(var log = Files.newOutputStream(Paths.get("products_log.txt")); var logger = new PrintWriter(log)) {
           var ret = IntStream.range(0, sellerSize * 10).sequential().mapToObj(i -> {
                var product = new Product();
                product.setName(faker.commerce().productName());
                product.setDescription(faker.lorem().paragraph());
                product.setImage(fakeImage());
                product.setCategory(categories.get(faker.random().nextInt(categories.size())));
                product.setCategoryId(product.getCategory().getId());
                int b = faker.random().nextInt(tags.size() - 1);
                product.setTags((new HashSet<>(tags.subList(b, faker.random().nextInt(b, tags.size())))));
                product.setTaxable(faker.random().nextBoolean());
                try {
                    save(product);
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
                return product;
            }).filter(Objects::nonNull).toList();
              return ret;
        } catch (IOException e){
            throw new RuntimeException(e);
        }

    }    

    List<ProductOffer> doProductOffer(final List<Product> products, final List<Seller> sellers) {
        try(var log = Files.newOutputStream(Path.of("./productOffer_log.txt")); var logger = new PrintWriter(log)) {
            var ret = IntStream.range(0, sellers.size() * products.size() / 10).sequential().mapToObj(j -> {
                int i = j / sellers.size();
                j = j % sellers.size();
                var offer = new ProductOffer();
                var product = products.get(i * 10);
                var seller = sellers.get(j);
                
                offer.setProduct(product);
                offer.setSeller(seller);
                offer.setDiscount((float) faker.number().randomDouble(2, 0, 1));
                offer.setRegularPrice(faker.random().nextInt(100) + 1); 
                offer.setDescription(faker.lorem().paragraph());
                offer.setStock(faker.random().nextInt(1000) + 1);
                offer.setInStock(offer.getStock() > 0);
                offer.setId(new ProductOffer.ProductOfferId(product.getId(), seller.getUserId()));
                
                try {
                    save(offer);
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
                return offer;
            }).filter(Objects::nonNull).toList();
            return ret;
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }    

    List<Coupon> doCoupons(final  List<Seller> sellers)  {
        return IntStream.range(0, sellers.size()*2).sequential().mapToObj(i ->{
            var coupon = new Coupon();
            var seller = sellers.get(i / 2);
            coupon.setSeller(seller);
            coupon.setSellerId(seller.getUserId());
            coupon.setCode(faker.internet().uuid().subSequence(0, 16).toString());
            coupon.setDiscount((float) faker.number().randomDouble(2,0,1));
            coupon.setActive(true);
            coupon.setMultiple(faker.random().nextBoolean());
            coupon.setStartDate(faker.date().past(30, TimeUnit.DAYS).toInstant());
            coupon.setEndDate(faker.date().future(30, TimeUnit.DAYS).toInstant());
            coupon.setDescription(faker.lorem().sentence());
            save(coupon);
            return coupon;
        }).toList();
    }    

    List<CartItem> doCartItem(final List<Cart> carts, final List<ProductOffer> offers) {
        return IntStream.range(0, Math.min(offers.size() / 10, carts.size() * 5)).sequential().mapToObj(i -> {
            int cartIndex = i % carts.size();
            int offerIndex = (i / carts.size()) % (offers.size() / 10);
            
            var item = new CartItem();
            var cart = carts.get(cartIndex);
            var offer = offers.get(offerIndex * 10);
            
            item.setQuantity(faker.random().nextInt(1, 10));
            item.setCart(cart);
            item.setProductOffer(offer);
            item.setId(new CartItem.CartItemId(offer.getId(), cart.getId()));
            
            try {
                save(item);
                
                cart.getCartItems().add(item);
                cart.setTotal(cart.getTotal() + item.getTotalPrice());
                cart.setItem_count(cart.getItem_count() + 1);
                save(cart);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            return item;
        }).filter(Objects::nonNull).toList();
    }    

    List<Order> doOrder(final List<User> users)  {

        var result = users.stream().limit((long)(users.size()*.7)).sequential().map(user ->{
            // Re-fetch user to ensure activeSessionRef is loaded
            User managedUser = entityManager.find(User.class, user.getId());
            if (managedUser.getActiveSessionRef() == null) {
                return null;
            }
            var cart = managedUser.getActiveSessionRef().getCart();
            if (cart.getCartItems().isEmpty()) {
                return null; 
            }
            
            
            cart.setOrdered(true);
            entityManager.merge(cart);
            
            var order = new Order();
            order.setUserId(managedUser.getId());
            order.setUser(managedUser);
            order.setCart(cart);
            order.setCartId(cart.getId());
            order.setStatus(Order.OrderStatus.AWAITING_SHIPPING);
            order.setTotal(cart.getTotal());
            save(order);
            
            
            var payment = new Payment();
            payment.setSuccessful(true);
            payment.setOrderId(order.getId());
            payment.setOrder(order);
            payment.setLastPaymentAttempt(System.currentTimeMillis());
            payment.setType(Payment.PaymentType.CREDIT_CARD);
            save(payment);
            
            
            order.setPaymentId(payment.getId());
            entityManager.merge(order);
            
            
            cart.getCartItems().forEach(ci -> {
               var shipment = new Shipment();
               shipment.setOrderId(order.getId());
               shipment.setOrder(order);
               shipment.setSeller(ci.getProductOffer().getSeller());
               shipment.setSellerId(ci.getProductOffer().getSeller().getUserId());
               shipment.setProductId(ci.getProductOffer().getProduct().getId());
               shipment.setProduct(ci.getProductOffer().getProduct());
               shipment.setShipDate(LocalDate.now());
               shipment.setDeliveryStatusSeller(Shipment.DeliveryStatus.PENDING);
               shipment.setCartItemId(ci.getId());
               shipment.setDeliveryAddress(managedUser.getAddress());
               save(shipment);
            });
            
            return order;
        }).filter(Objects::nonNull).toList();
        return result;
    }
    static int imageFileCounter = 0;
    private static byte[] fakeImage(){
        File images = new File("./images");
        File[] imageFiles = images.listFiles();
        if(!images.mkdir() && (imageFileCounter = imageFiles.length)>100){
            try(var fis = new FileInputStream( imageFiles[faker.random().nextInt(100)])){
                return fis.readAllBytes();
            }catch (IOException e){ e.printStackTrace();}
        } else {
            HttpURLConnection connection = null;
            try (var fos = new FileOutputStream(new File("./images/image"+(imageFileCounter++)+".jpg"))){
                connection = (HttpURLConnection) new java.net.URL("https://picsum.photos/200/300").openConnection();
                connection.setInstanceFollowRedirects(true);
                connection.connect();
                var ret = connection.getInputStream().readAllBytes();
                fos.write(ret);
                return ret;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    private PhoneNumber fakePhoneNumber(){
        return new PhoneNumber("+90", faker.phoneNumber().subscriberNumber(10));
    }
    private Address fakeAddress(){
        return new Address(
            faker.address().streetAddress(),
            faker.address().city(),
            faker.address().state(),
            faker.address().zipCode(),
            "TR" 
        );
    }
    private static final int NUM_TAGS = 1000;
    private static final int NUM_USER = 100;
    private static final int NUM_SESSION = (int) ( NUM_USER * 1.5) + 1;
    private static final int NUM_ORDER = (int)(NUM_USER * .7);
}
