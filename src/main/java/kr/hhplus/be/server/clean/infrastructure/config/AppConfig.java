package kr.hhplus.be.server.clean.infrastructure.config;

import kr.hhplus.be.server.clean.application.OrderService;
import kr.hhplus.be.server.clean.domain.repository.OrderRepository;
import kr.hhplus.be.server.clean.infrastructure.persistence.OrderJpaRepository;
import kr.hhplus.be.server.layerd.repository.coupon.CouponRepository;
import kr.hhplus.be.server.layerd.repository.coupon.CouponUsageHistoryRepository;
import kr.hhplus.be.server.layerd.repository.coupon.IssuedCouponRepository;
import kr.hhplus.be.server.layerd.repository.product.ProductRepository;
import kr.hhplus.be.server.layerd.repository.product.ProductStockHistoryRepository;
import kr.hhplus.be.server.layerd.repository.product.ProductStockRepository;
import kr.hhplus.be.server.layerd.repository.user.UserBalanceHistoryRepository;
import kr.hhplus.be.server.layerd.repository.user.UserBalanceRepository;
import kr.hhplus.be.server.layerd.service.CouponService;
import kr.hhplus.be.server.layerd.service.ProductService;
import kr.hhplus.be.server.layerd.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public OrderService orderService(OrderRepository orderRepository,
                                     ProductService productService,
                                     UserService userService,
                                     CouponService couponService) {
        return new OrderService(orderRepository, productService, userService, couponService);
    }

    @Bean
    public OrderRepository orderRepository(OrderJpaRepository jpaRepo) {
        return jpaRepo; // infra 구현체
    }

    // 레이어드 아키텍처로 구성된 클래스들
    @Bean
    public ProductService productService(ProductRepository productRepository,
                                         ProductStockRepository productStockRepository,
                                         ProductStockHistoryRepository productStockHistoryRepository) {
        return new ProductService(productRepository, productStockRepository, productStockHistoryRepository);
    }

    @Bean
    public UserService userService(UserBalanceRepository userBalanceRepository,
                                   UserBalanceHistoryRepository userBalanceHistoryRepository) {
        return new UserService(userBalanceRepository, userBalanceHistoryRepository);
    }

    @Bean
    public CouponService couponService(CouponRepository couponRepository,
                                       IssuedCouponRepository issuedCouponRepository,
                                       CouponUsageHistoryRepository couponUsageHistoryRepository) {
        return new CouponService(couponRepository,issuedCouponRepository,couponUsageHistoryRepository);
    }
}