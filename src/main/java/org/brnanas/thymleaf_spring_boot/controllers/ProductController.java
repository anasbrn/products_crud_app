package org.brnanas.thymleaf_spring_boot.controllers;

import org.brnanas.thymleaf_spring_boot.entities.Product;
import org.brnanas.thymleaf_spring_boot.repositories.ProductRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {
    private ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("")
    public String listProducts(Model model) {
        List<Product> products = productRepository.findAll();
        model.addAttribute("products", products);
        return "products_view";
    }

    @PostMapping("/delete/{id}")
    public String deleteProduct(@PathVariable(name = "id") Long id) {
        productRepository.deleteById(id);
        return "redirect:/products";
    }

    @PostMapping("/store")
    public String storeProduct(@RequestParam(name = "name") String name, @RequestParam(name = "price") Double price, @RequestParam(name = "description") String description) {
        Product product = Product.builder().name(name).price(price).description(description).build();
        productRepository.save(product);
        return "redirect:/products";
    }

    @PostMapping("/update/{id}")
    public String updateProduct(@PathVariable(name = "id") Long id, @RequestParam(name = "name") String name, @RequestParam(name = "price") Double price, @RequestParam(name = "description") String description) {
        Product product = productRepository.findById(id).get();
        product.setName(name);
        product.setPrice(price);
        product.setDescription(description);
        productRepository.save(product);
        return "redirect:/products";
    }

    @GetMapping("/all")
    @ResponseBody
    public List<Product> all() {
        return productRepository.findAll();
    }

    @GetMapping("/api/store")
    @ResponseBody
    public Product storeApi() {
        Product product = Product.builder().name("name").price(200.0).description("description").build();
        return productRepository.save(product);
    }
    @GetMapping("/api/delete/{id}")
    @ResponseBody
    public void deleteApi(@PathVariable(name = "id") Long id) {
        productRepository.deleteById(id);
    }
}
