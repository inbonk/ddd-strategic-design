package camp.nextstep.edu.kitchenpos.model;

import java.math.BigDecimal;

import static java.util.Optional.ofNullable;

public class Product {
    private Long id;
    private String name;
    private BigDecimal price;

    public static Product of(Long id, String name, Long price) {
        Product product = new Product();
        product.id = id;
        product.name = name;
        product.price = ofNullable(price).map(BigDecimal::valueOf).orElse(null);

        return product;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(final BigDecimal price) {
        this.price = price;
    }
}
