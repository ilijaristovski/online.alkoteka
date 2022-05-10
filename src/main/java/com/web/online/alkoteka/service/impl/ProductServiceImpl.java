package com.web.online.alkoteka.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.web.online.alkoteka.model.Category;
import com.web.online.alkoteka.model.Product;
import com.web.online.alkoteka.model.exceptions.CategoryNotFoundException;
import com.web.online.alkoteka.model.exceptions.ProductNotFoundException;
import com.web.online.alkoteka.repository.CategoryRepository;
import com.web.online.alkoteka.repository.ProductRepository;
import com.web.online.alkoteka.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ObjectMapper objectMapper;

    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository, ObjectMapper objectMapper) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public List<Product> findAll() {
        return this.productRepository.findAll();
    }

    @Override
    public Optional<Product> findById(Long id) {
        return this.productRepository.findById(id);
    }

    @Override
    public Optional<Product> findByName(String name) {
        return this.productRepository.findByName(name);
    }

    @Override
    @Transactional
    public Optional<Product> save(String name, Double price, Integer quantity, Long categoryId, Long manufacturerId) {
        Category category = this.categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));

        this.productRepository.deleteByName(name);
        return Optional.of(this.productRepository.save(new Product(name, price, quantity,category)));
    }

    @Override
    public Optional<Product> save(JsonNode productDto) throws JsonProcessingException {
        Category category = this.categoryRepository.findById(productDto.get("CategoryID").asLong())
                .orElseThrow(() -> new CategoryNotFoundException(productDto.get("CategoryID").asLong()));

        this.productRepository.deleteByName(productDto.get("Name").asText());
        return Optional.of(this.productRepository.save(objectMapper.treeToValue(productDto, Product.class)));
    }

    @Override
    @Transactional
    public Optional<Product> edit(Long id, String name, Double price, Integer quantity, Long categoryId, Long manufacturerId) {

        Product product = this.productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));

        product.setName(name);
        product.setPrice(price);
        product.setQuantity(quantity);

        Category category = this.categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));
        product.setCategory(category);


        return Optional.of(this.productRepository.save(product));
    }

    @Override
    public Optional<Product> edit(Long id, JsonNode productObject) {
        Product product = this.productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));

        product.setName(productObject.get("Name").asText());
        product.setPrice(productObject.get("Price").asDouble());
        product.setQuantity(productObject.get("Quantity").asInt());

        Category category = this.categoryRepository.findById(productObject.get("CategoryID").asLong())
                .orElseThrow(() -> new CategoryNotFoundException(productObject.get("CategoryID").asLong()));
        product.setCategory(category);


        return Optional.of(this.productRepository.save(product));
    }

    @Override
    public void deleteById(Long id) {
        this.productRepository.deleteById(id);
    }
}
