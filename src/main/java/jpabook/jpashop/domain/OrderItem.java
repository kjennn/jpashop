package jpabook.jpashop.domain;

import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class OrderItem {

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice; // 주문가격
    private int count; //주문 수량

    protected  OrderItem(){
        // 다른곳에서 직접 생성자 생성하지 마라. 유지보수 하기 힘들다.
    }
    // 생성메소드 여기있어용 이거 쓰세요
    public static OrderItem createOrderItem(Item item, int orderPrice, int count){
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);
        return orderItem;
    }
    //  비지니스 로직
    public void cancle() {
        getItem().addStock(count);

    }

    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }
}
