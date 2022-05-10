package com.web.online.alkoteka.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.web.online.alkoteka.model.Product;
import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<Product> findAll();

    Optional<Product> findById(Long id);

    Optional<Product> findByName(String name);

    Optional<Product> save(String name, Double price, Integer quantity, Long category, Long manufacturer);

    Optional<Product> save(JsonNode productDto) throws JsonProcessingException;

    Optional<Product> edit(Long id, String name, Double price, Integer quantity, Long category, Long manufacturer);

    Optional<Product> edit(Long id, JsonNode productObject);

    void deleteById(Long id);
}
