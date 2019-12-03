package camp.nextstep.edu.kitchenpos.bo;

import camp.nextstep.edu.kitchenpos.dao.ProductDao;
import camp.nextstep.edu.kitchenpos.model.Product;

import java.util.*;

import static java.util.Optional.ofNullable;

/**
 * Created by inbonk on 2019/12/03.
 */
public class InMemoryProductDao implements ProductDao {

    private Map<Long, Product> products = new HashMap<>();

    @Override
    public Product save(Product entity) {
        products.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public Optional<Product> findById(Long id) {
        return ofNullable(products.get(id));
    }

    @Override
    public List<Product> findAll() {
        return new ArrayList<>(products.values());
    }
}
