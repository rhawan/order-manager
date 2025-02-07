package br.com.ordermanager.infra.provider.db.repository;

import br.com.ordermanager.infra.provider.db.model.OrderModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderModel, Long> {
}