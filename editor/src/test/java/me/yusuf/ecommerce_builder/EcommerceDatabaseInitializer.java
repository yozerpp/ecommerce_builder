//package me.yusuf.ecommerce;
//
//import com.github.javafaker.Faker;
//import me.yusuf.ecommerce.domain.cart.Cart;
//import me.yusuf.ecommerce.domain.cart.CartItem;
//import me.yusuf.ecommerce.domain.category.Category;
//import me.yusuf.ecommerce.domain.coupon.Coupon;
//import me.yusuf.ecommerce.domain.order.Order;
//import me.yusuf.ecommerce.domain.payment.Payment;
//import me.yusuf.ecommerce.domain.product.Product;
//import me.yusuf.ecommerce.domain.product.ProductOffer;
//import me.yusuf.ecommerce.domain.role.Role;
//import me.yusuf.ecommerce.domain.seller.Seller;
//import me.yusuf.ecommerce.domain.session.Session;
//import me.yusuf.ecommerce.domain.shipment.Shipment;
//import me.yusuf.ecommerce.domain.tag.Tag;
//import me.yusuf.ecommerce.domain.user.User;
//import me.yusuf.ecommerce.domain.z_embeddable.Address;
//import me.yusuf.ecommerce.domain.z_embeddable.PhoneNumber;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import java.io.*;
//import java.net.HttpURLConnection;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.time.LocalDate;
//import java.util.*;
//import java.util.concurrent.*;
//import java.util.stream.Collectors;
//import java.util.stream.IntStream;
//
//public class EcommerceDatabaseInitializer {
//    static private final Faker faker = new Faker(Locale.forLanguageTag("tr-TR"));
//    private final DummyManager entityManager;
//    private final Map<Class<?>, List<Object>> saved = new java.util.HashMap<>();
//    public EcommerceDatabaseInitializer(DummyManager entityManager) {
//        this.entityManager = entityManager;
//    }
//    void initializeDatabase() {
//        doRole();
//        var sessions = doSession();
//        System.out.println("Sessions: " + sessions.size());
//        var categories = doCategory();
//        System.out.println("Categories: " + categories.size());
//        var tags = doTags();
//        System.out.println("Tags: " + tags.size());
//        var carts = doCart(sessions);
//        System.out.println("Carts: " + carts.size());
//        var users = doUser(sessions);
//        System.out.println("Users: " + users.size());
//        var sellers = doSeller(users);
//        System.out.println("Sellers: " + sellers.size());
//        var coupons=  doCoupons(sellers);
//        System.out.println("Coupons: " + coupons.size());
//        var products = doProduct(sellers.size(),categories,tags);
//        System.out.println("Products: " + products.size());
//        var offers =  doProductOffer(products,sellers);
//        System.out.println("Offers: " + offers.size());
//        var items =  doCartItem(carts, offers);
//        System.out.println("Cart Items: " + items.size());
//        var orders = doOrder(users);
//        System.out.println("Orders: " + orders.size());
//    }
//    void save(Object o){
//        entityManager.persist(o);
//    }
//    List<Session> doSession(){
//       return IntStream.range(0, NUM_SESSION).parallel().mapToObj(i -> {
//            Session session = new Session();
//            session.setId(java.util.UUID.randomUUID().toString());
//            save(session);
//           return session;
//        }).toList();
//    }
//    List<Cart> doCart(final List<Session> sessions) {
//        return IntStream.range(0, sessions.size()).parallel().mapToObj(i ->{
//            var cart = new Cart();
//            Session session;
//            session = sessions.get(i);
//            cart.setSession(session);
//            cart.setSessionId(session.getId());
//            save(cart);
//            session.setCartId(cart.getId());
//            session.setCart(cart);
//            return cart;
//        }).toList();
//    }
//    void doRole(){
//        try{
//            entityManager.persist(new Role("ROLE_ADMIN","admin"));
//            entityManager.persist(new Role("ROLE_USER","user"));
//            entityManager.persist(new Role("ROLE_SELLER","seller"));
//            entityManager.persist(new Role("ROLE_STAFF","staff"));
//        } catch (Exception e) {
//        }
//    }
//    List<User> doUser(final List<Session> sessions){
//        PasswordEncoder encoder = new BCryptPasswordEncoder();
//        try(var log = Files.newOutputStream(Path.of("./user_logs.txt"));var logger = new PrintWriter(log)) {
//           return IntStream.range(0, NUM_USER).parallel().mapToObj(i ->{
//                var user = new User();
//                user.setFirstName(faker.name().firstName());
//                user.setLastName(faker.name().lastName());
//                user.setUsername(java.util.UUID.randomUUID() + "@" + faker.internet().domainName());
//                user.setPassword(encoder.encode(faker.internet().password()));
//                user.setAddress(fakeAddress());
//                var session = sessions.get(i);
//                user.setActiveSession(session.getId());
//                user.setActiveSessionRef(session);
//                user.getAuthorities().add(new Role("ROLE_USER", null));
//                user.setPhoneNumber(fakePhoneNumber());
//                user.setEnabled(true);
//                user.setProfileImage(fakeImage());
//                try {
//                    save(user);
//                } catch (Exception e) {
//                    e.printStackTrace(logger);
//                }
//                return user;
//            }).toList();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//    List<Seller> doSeller(final List<User> users)  {
//        try (var log = Files.newOutputStream(Path.of("./seller_log.txt")); var logger = new PrintWriter(log)){
//          return IntStream.range(0, users.size() / 10).parallel().mapToObj(i ->{
//                var user = users.get(i*10);
//                var seller = new Seller();
//                seller.setShopName(faker.company().name());
//                seller.setShopAddress(fakeAddress());
//                seller.setUserId(user.getId());
//                seller.setUser(user);
//                try{
//                    save(seller);
//                } catch (Exception e) {
//                    e.printStackTrace(logger);
//                }
//                return  seller;
//            }).toList();
//        } catch(IOException e){
//            throw new RuntimeException(e);
//        }
//    }
//    void addCategories(List<Category> categories,Category parent, int level){
//        if(level == 0 ) {
//            categories.add(parent);
//            return;
//        }
//        for (int i=0 ; i < 4 ; i++){
//            var category = new Category();
//            category.setName(faker.commerce().productName());
//            category.setParentCategory(parent);
//            if(parent!=null)
//                category.setParentId(parent.getId());
//            category.setDescription(faker.lorem().characters(0,255));
//            entityManager.persist(category);
//            addCategories(categories,category, level-1);
//        }
//    }
//     List<Category> doCategory(){
//        var ret = new ArrayList<Category>();
//        for (int i=0; i <5; i++)
//            addCategories(ret,null,4);
//        return ret;
//    }
//    List<Tag> doTags(){
//        return IntStream.range(0, NUM_TAGS).parallel().mapToObj(i ->{
//            var tag = new Tag();
//            tag.setName(faker.internet().uuid().substring(0,32));
//            try {
//                save(tag);
//            } catch (Exception e) {e.printStackTrace();return null;}
//            return  tag;
//        }).filter(Objects::nonNull).toList();
//    }
//    List<Product> doProduct(final int sellerSize, final List<Category> categories,List<Tag> tags)  {
//        try(var log = Files.newOutputStream(Paths.get("products_log.txt")); var logger = new PrintWriter(log)) {
//           return IntStream.range(0, sellerSize * 10).parallel().mapToObj(i -> {
//                var product = new Product();
//                product.setName(faker.commerce().productName());
//                product.setDescription(faker.lorem().paragraph());
//                product.setImage(fakeImage());
//                product.setCategory(categories.get(faker.random().nextInt(categories.size())));
//                product.setCategoryId(product.getCategory().getId());
//                int b = faker.random().nextInt(tags.size() - 1);
//                product.setTags((new HashSet<>(tags.subList(b, faker.random().nextInt(b, tags.size())))));
//                product.setTaxable(faker.random().nextBoolean());
//                try {
//                    save(product);
//                } catch (Exception e) {
//                    e.printStackTrace(logger);
//                    return null;
//                }
//                return product;
//            }).filter(Objects::nonNull).toList();
//        } catch (IOException e){
//            throw new RuntimeException(e);
//        }
//
//    }
//    List<ProductOffer> doProductOffer(final List<Product> products, final List<Seller> sellers)  {
//        try(var log = Files.newOutputStream(Path.of("./productOffer_log.txt")); var logger = new PrintWriter(log)) {
//          return IntStream.range(0, sellers.size() * products.size() / 10).parallel().mapToObj(j -> {
//                int i= j / sellers.size();
//                j = j % sellers.size();
//                var offer = new ProductOffer();
//                offer.setProduct(products.get(i * 10));
//                offer.setSeller(sellers.get(j));
//                offer.setDiscount((float) faker.number().randomDouble(2, 0, 1));
//                offer.setRegularPrice(faker.random().nextInt(100));
//                offer.setDescription(faker.lorem().paragraph());
//                offer.setStock(faker.random().nextInt(1000));
//                offer.setId(new ProductOffer.ProductOfferId(offer.getProduct().getId(), offer.getSeller().getUserId()));
//                try {
//                    save(offer);
//                } catch (Exception e) {
//                    e.printStackTrace(logger);
//                    return null;
//                }
//                return offer;
//            }).filter(Objects::nonNull).toList();
//        } catch (IOException e){
//            throw new RuntimeException(e);
//        }
//    }
//    List<Coupon> doCoupons(final  List<Seller> sellers)  {
//        return IntStream.range(0, sellers.size()*2).parallel().mapToObj(i ->{
//            var coupon = new Coupon();
//            var seller = sellers.get(i / 2);
//            coupon.setSeller(seller);
//            coupon.setSellerId(seller.getUserId());
//            coupon.setCode(faker.internet().uuid().subSequence(0, 16).toString());
//            coupon.setDiscount((float) faker.number().randomDouble(2,0,1));
//            coupon.setActive(true);
//            coupon.setMultiple(faker.random().nextBoolean());
//            coupon.setStartDate(faker.date().past(30, TimeUnit.DAYS).toInstant());
//            coupon.setEndDate(faker.date().future(30, TimeUnit.DAYS).toInstant());
//            coupon.setDescription(faker.lorem().sentence());
//            save(coupon);
//            return coupon;
//        }).toList();
//    }
//    List<CartItem> doCartItem(final List<Cart> carts, final List<ProductOffer> offers)  {
//       return IntStream.range(0, offers.size() / 1000 * carts.size()).parallel().mapToObj(i -> {
//            int j = i / (offers.size()/ 1000);
//            i = i % (offers.size() / 1000);
//            var item = new CartItem();
//            var cart = carts.get(j);
//            var offer = offers.get(i * 10);
//            item.setQuantity(faker.random().nextInt(10));
//            item.setCart(cart);
//            item.setProductOffer(offer);
//            item.setId(new CartItem.CartItemId(offer.getId(), cart.getId()));
//            try {
//                save(item);
//            } catch (Exception e) {
//                return null;
//            }
//           cart.getCartItems().add(item);
//           cart.setTotal(cart.getTotal() + item.getTotalPrice());
//            return item;
//        }).filter(Objects::nonNull).toList();
//    }
//    List<Order> doOrder(final List<User> users)  {
//       return users.stream().limit((long)(users.size()*.7)).parallel().map(user ->{
//            var order = new Order();
//            order.setUser(user);
//            order.setCart(order.getUser().getActiveSessionRef().getCart());
//            order.setUserId(order.getUser().getId());
//            order.setCartId(order.getCart().getId());
//            order.setStatus(Order.OrderStatus.AWAITING_SHIPPING);
//            order.setTotal(order.getCart().getTotal());
//            entityManager.persist(order);
//           var p = new Payment(null,true,order.getId(),System.currentTimeMillis(),order, Payment.PaymentType.CREDIT_CARD);
//           entityManager.persist(p);
//           order.setPayment(p);
//           order.setPaymentId(order.getPayment().getId());
//           order.setShipments(order.getCart().getCartItems().stream().map(ci->{
//               var shipment = new Shipment();
//               shipment.setCartItem(ci);
//               shipment.setOrder(order);
//               shipment.setSeller(ci.getProductOffer().getSeller());
//               shipment.setOrderId(order.getId());
//               shipment.setSellerId(ci.getProductOffer().getSeller().getUserId());
//               shipment.setProduct(ci.getProductOffer().getProduct());
//               shipment.setShipDate(LocalDate.now());
//               shipment.setDeliveryStatusSeller(Shipment.DeliveryStatus.PENDING);
//               shipment.setCartItemId(ci.getId());
//               shipment.setProduct(ci.getProductOffer().getProduct());
//               shipment.setProductId(ci.getProductOffer().getProduct().getId());
//               shipment.setDeliveryAddress(order.getUser().getAddress());
//               entityManager.persist(shipment);
//               return shipment;
//           }).collect(Collectors.toSet()));
//            return order;
//        }).toList();
//    }
//    static int imageFileCounter = 0;
//    private static byte[] fakeImage(){
//        File images = new File("./images");
//        File[] imageFiles = images.listFiles();
//        if(!images.mkdir() && (imageFileCounter = imageFiles.length)>100){
//            try(var fis = new FileInputStream( imageFiles[faker.random().nextInt(100)])){
//                return fis.readAllBytes();
//            }catch (IOException e){ e.printStackTrace();}
//        } else {
//            HttpURLConnection connection = null;
//            try (var fos = new FileOutputStream(new File("./images/image"+(imageFileCounter++)+".jpg"))){
//                connection = (HttpURLConnection) new java.net.URL("https://picsum.photos/200/300").openConnection();
//                connection.setInstanceFollowRedirects(true);
//                connection.connect();
//                var ret = connection.getInputStream().readAllBytes();
//                fos.write(ret);
//                return ret;
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return null;
//    }
//    private PhoneNumber fakePhoneNumber(){
//        return new PhoneNumber(faker.phoneNumber().subscriberNumber(),faker.phoneNumber().cellPhone());
//    }
//    private Address fakeAddress(){
//        return new Address(faker.address().streetAddress(),faker.address().city(),faker.address().state(),faker.address().zipCode(),faker.address().country());
//    }
//    private static final int NUM_TAGS = 1000;
//    private static final int NUM_USER = 1000;
//    private static final int NUM_SESSION = (int) ( NUM_USER * 1.5) + 1;
//    private static final int NUM_ORDER = (int)(NUM_USER * .7);
//}
