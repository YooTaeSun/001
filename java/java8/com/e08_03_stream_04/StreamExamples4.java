package www.townsi.com.e08_03_stream_04;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import www.townsi.data.Order;
import www.townsi.data.OrderedItem;
import www.townsi.data.Product;

/**
 * 
 */
public class StreamExamples4 {
  public static void main(String[] args) {
    final List<Product> products =
        Arrays.asList(
            new Product(1L, "A", new BigDecimal("100.50")),
            new Product(2L, "B", new BigDecimal("23.00")),
            new Product(3L, "C", new BigDecimal("31.45")),
            new Product(4L, "D", new BigDecimal("80.20")),
            new Product(5L, "E", new BigDecimal("7.50"))
        );

    System.out.println("Products.price >= 30: \n" +
            products.stream()
                .filter(product -> product.getPrice().compareTo(new BigDecimal("30")) >= 0)
                .collect(toList())
    );
    System.out.println("\n==================================");
    System.out.println("Products.price >= 30 (with joining(\"\\n\")): \n" +
            products.stream()
                .filter(product -> product.getPrice().compareTo(new BigDecimal("30")) >= 0)
                .map(product -> product.toString())
                .collect(joining("\n"))
    );

    System.out.println("\n===========================");
    System.out.println("IntStream.sum: " +
            IntStream.of(1, 2, 3, 4, 5)
                     .sum()
    );

    System.out.println("\n===========================");
    System.out.println("Total Price: " +
            products.stream()
                    .map(product -> product.getPrice())
//                    .reduce(BigDecimal.ZERO, (price1, price2) -> price1.add(price2))
                    .reduce(BigDecimal.ZERO, (a,b) -> a.add(b))
    );

    System.out.println("\n===========================");
    System.out.println("Total price (where Product.price >= 30): " +
        products.stream()
                .filter(product -> product.getPrice().compareTo(new BigDecimal("30")) >= 0)
                .map(product -> product.getPrice())
                .reduce(BigDecimal.ZERO, (price1, price2) -> price1.add(price2))
    );

    System.out.println("\n===========================");
    /*
     * BigDecimal타입과 Product타입을 동시에 사용하는 방법입니다만,
     * 불필요한 코드가 늘고 가독성이 떨어지기 때문에 권장하지 않습니다
     */
    System.out.println("Total price (where Product.price >= 30) (not recommended): " +
        products.stream()
                .filter(product -> product.getPrice().compareTo(new BigDecimal("30")) >= 0)
                .reduce(BigDecimal.ZERO,
                        (price, product) -> price.add(product.getPrice()), // (BigDecimal, Product) -> BigDecimal
                        (price1, price2) -> price1.add(price2)) // (BigDecimal, BigDecimal) -> BigDecimal
    );

    System.out.println("\n===========================");
    System.out.println("The number of Products (where Product.price >= 30): " +
        products.stream()
                .filter(product -> product.getPrice().compareTo(new BigDecimal("30")) >= 0)
                .count()
    );

    final OrderedItem item1 = new OrderedItem(1L, products.get(0), 1);
    final OrderedItem item2 = new OrderedItem(2L, products.get(2), 3);
    final OrderedItem item3 = new OrderedItem(3L, products.get(4), 10);

    final Order order = new Order(1L, "1", Arrays.asList(item1, item2, item3));

    System.out.println("\n===========================");
    System.out.println("order.totalPrice(): " + order.totalPrice());
  }
}
