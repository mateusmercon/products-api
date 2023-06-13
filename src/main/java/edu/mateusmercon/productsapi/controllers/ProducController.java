package edu.mateusmercon.productsapi.controllers;

import edu.mateusmercon.productsapi.repositories.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProducController {
    @Autowired
    ProductRepository productRepository;
}
