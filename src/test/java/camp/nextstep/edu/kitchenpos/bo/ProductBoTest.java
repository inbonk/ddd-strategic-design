package camp.nextstep.edu.kitchenpos.bo;

import camp.nextstep.edu.kitchenpos.dao.ProductDao;
import camp.nextstep.edu.kitchenpos.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertAll;

/**
 * Created by inbonk on 2019/12/03.
 */
class ProductBoTest {

    private ProductDao productDao;

    private ProductBo productBo;

    @BeforeEach
    void setUp() {
        productDao = new InMemoryProductDao();
        productBo = new ProductBo(productDao);
    }

    @DisplayName("상품을 등록할 수 있다.")
    @Test
    void create() {

        Product product = Product.of(1L, "치킨키친치킹", 20000L);

        Product saved = productBo.create(product);

        assertAll(
                () -> assertThat(saved).isNotNull(),
                () -> assertThat(saved.getName()).isEqualTo(product.getName())
        );
    }

    @DisplayName("가격이 0원보다 작으면 안된다.")
    @ParameterizedTest
    @ValueSource(longs = {-1, Long.MIN_VALUE})
    void priceLessThanZero(Long price) {
        Product product = Product.of(1L, "치킨키친치킹", price);

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> productBo.create(product));
    }

    @DisplayName("가격이 있어야 한다.")
    @ParameterizedTest
    @NullSource
    void priceValidationNull(Long price) {
        Product product = Product.of(1L, "치킨키친치킹", price);

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> productBo.create(product));
    }

    @DisplayName("상품의 목록을 조회할 수 있다.")
    @Test
    void list() {
        Product product1 = Product.of(1L, "치킨키친치킹", 20000L);
        productDao.save(product1);
        Product product2 = Product.of(2L, "두루루루두루치기", 30000L);
        productDao.save(product2);

        List<Product> list = productBo.list();

        assertThat(list).hasSize(2);
        assertThat(list).containsExactlyInAnyOrder(product1, product2);
    }
}
