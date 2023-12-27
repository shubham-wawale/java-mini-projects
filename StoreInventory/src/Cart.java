import java.time.LocalDate;
import java.util.*;

public class Cart {
    enum CartType {PHYSICAL, VIRTUAL};
    private static int lastId = 1;
    private int id;
    private  LocalDate cartDate;
    private CartType type;
    private Map<String, Integer> products;

    public Cart(CartType type, int days){
        this.type = type;
        id = lastId++;
        cartDate = LocalDate.now().minusDays(days);
        products = new HashMap<>();
    }

    public Cart(int id){
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public LocalDate getCartDate() {
        return cartDate;
    }

    public Map<String, Integer> getProducts() {
        return products;
    }

    public void addItem(Inventory item, int qty){
        products.merge(item.getProduct().sku(), qty, Integer::sum);
        if(!item.reserveItem(qty)){
            System.out.println("Something went wrong while adding to the cart!");
        }
    }

    public void removeItem(Inventory item, int qty) {
        int currentQty = products.get(item.getProduct().sku());
        if(currentQty <= qty){
            qty = currentQty;
            products.remove(item.getProduct().sku());
            System.out.printf("Item [%s] removed from the basket%n", item.getProduct().name());

        } else {
            products.merge(item.getProduct().sku(), qty, (oldVal, newVal)-> oldVal-newVal);
            System.out.printf("%d [%s]s removed from the basket%n", qty, item.getProduct().name());
        }
        item.releaseItem(qty);
    }

    public void printSalesSlip(Map<String, Inventory> inventory){
        double total = 0;
        System.out.println("-----------------------------------");
        System.out.println("Thank you for your sale");
        for(var cartItem: products.entrySet()){
            var item = inventory.get(cartItem.getKey());
            int qty = cartItem.getValue();
            double itemizedPrice = item.getPrice()*qty;
            total += itemizedPrice;
            System.out.printf("\t%s %-10s (%d)@ %.2f = $%.2f%n", cartItem.getKey(), item.getProduct().name(),
                    qty, item.getPrice(), itemizedPrice);
        }

        System.out.printf("Total Sales: %.2f%n", total);
        System.out.println("-----------------------------------");
    }

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", cartDate=" + cartDate +
                ", products=" + products +
                "}";
    }





}
