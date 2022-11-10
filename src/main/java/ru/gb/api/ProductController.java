package ru.gb.api;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.gb.model.Product;
import ru.gb.repository.ProductRepository;

import java.util.*;

@Controller
public class ProductController {

    private List<Product> products = new ArrayList<>();
    ConfigurableApplicationContext context = new AnnotationConfigApplicationContext("ru.gb.config");
    ProductRepository productRepository = null;
    {
        for (int i = 0; i < 5; i++) {
            productRepository = context.getBean(ProductRepository.class);
        }
    }

    @GetMapping("/products/{id}")
    public String getProduct(@PathVariable Long id, Model model) {
        Product product = products.stream()
                .filter(it -> Objects.equals(id, it.getId()))
                .findFirst()
                .orElse(null);
        model.addAttribute("product", product);
        return "product";
    }

    @GetMapping("/products")
    public String getProducts(Model model) {
        products = productRepository.getProduct();
        Product product = new Product();
        model.addAttribute("product", product);
        model.addAttribute("productList", this.products);
        return "products";
    }

    @PostMapping("/products")
    public String addProduct(@ModelAttribute Product product, Model model) {
        Long id = products.stream().max(Comparator.comparing(m -> m.getId())).get().getId();
        product.setId(++id);
        products.add(product);
        Product newProduct = new Product();
        model.addAttribute("product", newProduct);
        model.addAttribute("productList", products);
        return "products";
    }

}
