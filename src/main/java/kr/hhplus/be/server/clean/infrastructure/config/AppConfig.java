package kr.hhplus.be.server.clean.infrastructure.config;

import kr.hhplus.be.server.clean.application.OrderService;
import kr.hhplus.be.server.clean.domain.repository.OrderRepository;
import kr.hhplus.be.server.clean.infrastructure.persistence.OrderJpaRepository;
import kr.hhplus.be.server.clean.infrastructure.persistence.SpringOrderJpa;
import kr.hhplus.be.server.layerd.service.CouponService;
import kr.hhplus.be.server.layerd.service.ProductService;
import kr.hhplus.be.server.layerd.service.UserService;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = {
        "kr.hhplus.be.server"
})
@EntityScan(basePackages = {
        "kr.hhplus.be.server"
})
public class AppConfig {

    // 클린 아키텍처는 따로 의존성 주입
    @Bean
    public OrderRepository orderRepository(SpringOrderJpa springOrderJpa) { // SpringDataJpa 주입
        return new OrderJpaRepository(springOrderJpa);
    }

    @Bean
    public OrderService orderService(OrderRepository orderRepository,
                                     ProductService productService,
                                     UserService userService,
                                     CouponService couponService) {
        return new OrderService(orderRepository, productService, userService, couponService);
    }
}