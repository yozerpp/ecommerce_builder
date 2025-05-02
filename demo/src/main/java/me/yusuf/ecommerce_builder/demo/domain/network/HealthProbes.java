package me.yusuf.ecommerce_builder.demo.domain.network;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
public class HealthProbes implements ApplicationListener<ApplicationReadyEvent> {
private boolean started = false;
    public HealthProbes(){}
    @GetMapping("/startup")
    public ResponseEntity<Void> isStarted(){
        if (started)
            return ResponseEntity.ok().build();
        else return ResponseEntity.status(503).build();
    }
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        started =true;
    }
}
