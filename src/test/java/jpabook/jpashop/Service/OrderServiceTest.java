package jpabook.jpashop.Service;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class OrderServiceTest {

    @Autowired
    EntityManager em;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;

    @Test
    public void 상품주문() throws Exception{

        Member member = createMember();

        Book book = createBook("시골 jpa", 10000, 10);

        int orderCount = 2;

        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        Order getOrder = orderRepository.findOne(orderId);

        //검증
        assertEquals(OrderStatus.ORDER, getOrder.getStatus(), "상품주문시 상태는 order");
        assertEquals(1,getOrder.getOrderItems().size(),"주문한 상품 종류 수가 정확해야한다.");
        assertEquals(10000 * orderCount, getOrder.getTotalPrice() ,"주문가격은 가격 * 수량이다.");
        assertEquals(8,book.getStockQuantity(),"주문 수량만큼 재고가 줄어야한다.");
    }


    @Test
    public void 상품주문_재고수량초과() throws Exception{

        Member member = createMember();
        Item item = createBook("시골 jpa", 10000, 10);

        int orderCount = 11;
        NotEnoughStockException exception = assertThrows(NotEnoughStockException.class, () -> orderService.order(member.getId(), item.getId(), orderCount));
       // assertEquals( exception.getMessage(),"재고 수량 부족 예외가 발생해야한다.");
        fail("재고 수량 부족 예외가 발생해야한다.");
    }

    @Test
    public void 주문취소() throws Exception{

        Member member = createMember();
        Book item = createBook("시골 jpa", 10000, 10);

        int orderCount =2;
        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        orderService.cancelOrder(orderId);

       Order getOrder=  orderRepository.findOne(orderId);
       assertEquals(OrderStatus.CANCLE, getOrder.getStatus(),"주문 취소시 상태는 cancle 이다");
       assertEquals(10, item.getStockQuantity(),"주문이 취소된 상품은 재고가 증가해야한다. ");
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("지은");
        member.setAddress(new Address("서울", "마포구", "123-123"));
        em.persist(member);
        return member;
    }

    private Book createBook(String name, int price, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        em.persist(book);
        return book;
    }
}