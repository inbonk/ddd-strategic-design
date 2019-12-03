package camp.nextstep.edu.kitchenpos.dao;

import camp.nextstep.edu.kitchenpos.model.Product;

import java.util.List;
import java.util.Optional;

/**
 * Created by inbonk on 2019/12/03.
 */
public interface ProductDao {
    Product save(Product entity);

    Optional<Product> findById(Long id);

    List<Product> findAll();
}
