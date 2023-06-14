package edu.mateusmercon.productsapi.controllers;

import edu.mateusmercon.productsapi.dtos.ProductRecordDto;
import edu.mateusmercon.productsapi.models.ProductModel;
import edu.mateusmercon.productsapi.repositories.ProductRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


import jakarta.validation.Valid;



@RestController
public class ProductController {
    @Autowired
    ProductRepository productRepository;

    @PostMapping("/products")
    public ResponseEntity<ProductModel> saveProduct(@RequestBody @Valid ProductRecordDto productRecordDto) {
        var productModel = new ProductModel();

        BeanUtils.copyProperties(productRecordDto, productModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(productRepository.save(productModel));
    }

    @GetMapping("/products")
     public ResponseEntity<List<ProductModel>> getAllProducts(){
        List<ProductModel> productsList = productRepository.findAll();

        if (!productsList.isEmpty()) {
            for (ProductModel product : productsList) {
                UUID id = product.getIdProduct();
                product.add(linkTo(methodOn(ProductController.class).getOneProduct(id)).withSelfRel());
            }
        }


        return ResponseEntity.status(HttpStatus.OK).body(productsList);
    }

     @GetMapping("/products/{id}")
     public ResponseEntity<Object> getOneProduct(@PathVariable(value="id") UUID id){
        Optional<ProductModel> productOptional = productRepository.findById(id);

        if(productOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }

        productOptional.get().add(linkTo(methodOn(ProductController.class).getAllProducts()).withSelfRel());

        return ResponseEntity.status(HttpStatus.OK).body(productOptional.get());
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<Object> updateProduct(@PathVariable(value="id") UUID id, @RequestBody @Valid ProductRecordDto productRecordDto) {
        Optional<ProductModel> productOptional = productRepository.findById(id);

        if(productOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
        }

        var productModel = productOptional.get();

        BeanUtils.copyProperties(productRecordDto, productModel);

        return ResponseEntity.status(HttpStatus.OK).body(productRepository.save(productModel));
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable(value = "id") UUID id) {
        Optional<ProductModel> productOptional = productRepository.findById(id);

        if(productOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
        }

        productRepository.delete(productOptional.get());

        return ResponseEntity.status(HttpStatus.OK).body("Product deleted sucessfully.");
    }

}
