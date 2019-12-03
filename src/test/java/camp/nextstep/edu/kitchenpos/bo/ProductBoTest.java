package camp.nextstep.edu.kitchenpos.bo;

import camp.nextstep.edu.kitchenpos.dao.ProductDao;
import camp.nextstep.edu.kitchenpos.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by inbonk on 2019/12/03.
 */
@ExtendWith(MockitoExtension.class)
class ProductBoTest {

    @Mock
    private ProductDao productDao;

    private ProductBo productBo;

    @BeforeEach
    void setUp() {
        productBo = new ProductBo(productDao);
    }

    @DisplayName("상품을 등록할 수 있다.")
    @Test
    void create() {

        Product product = Product.of(1L, "치킨키친치킹", 20000L);
        given(productDao.save(any(Product.class))).willReturn(product);

        Product saved = productBo.create(product);

        assertAll(
                () -> assertThat(saved).isNotNull(),
                () -> assertThat(saved.getName()).isEqualTo(product.getName())
        );

        verify(productDao, times(1)).save(any(Product.class));

        then(productDao).should(times(1)).save(any(Product.class));
    }

    @DisplayName("가격이 0원보다 작으면 안된다.")
    @ParameterizedTest
    @ValueSource(longs = {-1})
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
        List<Product> products = Arrays.asList(
                Product.of(1L, "치킨키친치킹", 20000L),
                Product.of(2L, "두루루루두루치기", 30000L));

        given(productDao.findAll()).willReturn(products);

        List<Product> list = productBo.list();

        assertThat(list).hasSize(products.size());
        assertThat(list).containsExactlyInAnyOrderElementsOf(products);
    }
}
