package pl.gesieniec.ed.asocjacja;

import java.util.List;

public class Cart {

    List<Item> cart;

    public Cart(final List<Item> cart) {
        this.cart = cart;
    }

    public List<Item> getCart() {
        return cart;
    }

    @Override
    public String toString() {
        return cart.toString();
    }
}
