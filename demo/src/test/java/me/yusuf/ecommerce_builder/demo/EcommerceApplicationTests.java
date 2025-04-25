package me.yusuf.ecommerce_builder.demo;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class EcommerceApplicationTests {
    @BeforeAll
    static void init() {
        System.setProperty("test.env", "true");
    }
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    DummyManager entityManager;
    @Test
    void initializeDatabase() {
        if (true) return;
        List<String> tables = new ArrayList<>();
        jdbcTemplate.query("SELECT i.table_name FROM ecommerce.information_schema.tables i where i.table_schema='public'",(rs,_)->tables.add(rs.getString(1)));
        if(tables.stream().filter(t->!(t.equals("notification") || t.equals("card_payment"))).anyMatch(t->
                jdbcTemplate.queryForObject("SELECT COUNT(*) FROM ecommerce.public."+t,int.class)==0)){
            tables.forEach(t->jdbcTemplate.execute("TRUNCATE TABLE ecommerce.public."+t + " CASCADE"));
            System.out.println("----Database is empty, initializing database----");
            new EcommerceDatabaseInitializer(entityManager).initializeDatabase();
            System.out.println("----Initialization finished----");
        }
    }

}
