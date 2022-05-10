package com.web.online.alkoteka.service.impl;

import com.web.online.alkoteka.model.Product;
import com.web.online.alkoteka.model.ShoppingCart;
import com.web.online.alkoteka.model.User;
import com.web.online.alkoteka.model.enumerations.ShoppingCartStatus;
import com.web.online.alkoteka.model.exceptions.ProductAlreadyInShoppingCartException;
import com.web.online.alkoteka.model.exceptions.ProductNotFoundException;
import com.web.online.alkoteka.model.exceptions.ShoppingCartNotFoundException;
import com.web.online.alkoteka.model.exceptions.UserNotFoundException;
import com.web.online.alkoteka.repository.ShoppingCartRepository;
import com.web.online.alkoteka.repository.UserRepository;
import com.web.online.alkoteka.service.ProductService;
import com.web.online.alkoteka.service.ShoppingCartService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final UserRepository userRepository;
    private final ProductService productService;

    public ShoppingCartServiceImpl(ShoppingCartRepository shoppingCartRepository, UserRepository userRepository, ProductService productService) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.userRepository = userRepository;
        this.productService = productService;
    }

    @Override
    public List<Product> listAllProductsInShoppingCart(Long cartId) {
        if(this.shoppingCartRepository.findById(cartId).isEmpty())
            throw new ShoppingCartNotFoundException(cartId);
        return this.shoppingCartRepository.findById(cartId).get().getProducts();
    }

    @Override
    public ShoppingCart getActiveShoppingCart(String username) {
        User user = this.userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));

        return this.shoppingCartRepository
                .findByUserAndStatus(user, ShoppingCartStatus.CREATED)
                .orElseGet(() -> {
                    ShoppingCart cart = new ShoppingCart(user);
                    return this.shoppingCartRepository.save(cart);
                });
    }

    @Override
    public ShoppingCart addProductToShoppingCart(String username, Long productId) {
        ShoppingCart shoppingCart = this.getActiveShoppingCart(username);
        Product product = this.productService.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));
        if(shoppingCart.getProducts()
                .stream().filter(i -> i.getId().equals(productId)).toList().size() > 0)
            throw new ProductAlreadyInShoppingCartException(productId, username);
        shoppingCart.getProducts().add(product);
        return this.shoppingCartRepository.save(shoppingCart);
    }
}
