package org.hanson.eddyshop.model;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal totalAmount=BigDecimal.ZERO;

    @OneToMany(mappedBy="cart", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<CartItem> cartItems;

    @OneToOne
    @JoinColumn(name="user_id")
    private User user;

    public void setTotalAmount(){
        this.totalAmount = cartItems.stream().map(item->{
            BigDecimal unitPrice=item.getUnitPrice();
            if (unitPrice==null){
                return BigDecimal.ZERO;
            }
            return unitPrice.multiply(BigDecimal.valueOf(item.getQuantity()));
        }).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
