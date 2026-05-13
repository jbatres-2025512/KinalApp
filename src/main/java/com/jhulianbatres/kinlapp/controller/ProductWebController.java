package com.jhulianbatres.kinlapp.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jhulianbatres.kinlapp.entity.Product;
import com.jhulianbatres.kinlapp.service.ProductService;

@Controller
@RequestMapping("/product")
public class ProductWebController {

    private final ProductService productService;

    public ProductWebController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String viewProducts(Model model) {
        List<Product> products = productService.listAll();
        model.addAttribute("listProducts", products);

        Product newProduct = new Product();
        newProduct.setProductState(1);
        model.addAttribute("product", newProduct);

        return "product";
    }

    @GetMapping("/search")
    public String searchProduct(@RequestParam(value = "code", required = false) String code, Model model) {
        List<Product> products;

        if (code != null && !code.trim().isEmpty()) {
            try {
                Long productCode = Long.parseLong(code);
                Optional<Product> found = productService.findProductByCode(productCode);
                if (found.isPresent()) {
                    products = List.of(found.get());
                } else {
                    products = List.of();
                }
            } catch (NumberFormatException e) {
                products = List.of();
            }
        } else {
            products = productService.listAll();
        }

        model.addAttribute("listProducts", products);

        Product newProduct = new Product();
        newProduct.setProductState(1);
        model.addAttribute("product", newProduct);

        return "product";
    }

    @PostMapping("/add")
    public String addProduct(@ModelAttribute("product") Product product) {
        try {

            if (product.getProductName() == null || product.getProductName().trim().isEmpty()) {
                return "redirect:/product?error=El nombre es obligatorio";
            }
            if (product.getPrice() == null) {
                return "redirect:/product?error=El precio es obligatorio";
            }

            productService.save(product);
            return "redirect:/product";
        } catch (IllegalArgumentException e) {
            return "redirect:/product?error=" + e.getMessage();
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/edit/search")
    public String searchProductToEdit(@RequestParam("code") String code, Model model) {
        List<Product> products = productService.listAll();
        model.addAttribute("listProducts", products);

        Product newProduct = new Product();
        newProduct.setProductState(1);
        model.addAttribute("product", newProduct);

        try {
            Long productCode = Long.parseLong(code);
            Optional<Product> productOptional = productService.findProductByCode(productCode);
            if (productOptional.isPresent()) {
                model.addAttribute("editProduct", productOptional.get());
            }
        } catch (NumberFormatException e) {
        }

        return "product";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/edit/{code}")
    public String updateProduct(@PathVariable("code") Long code, @ModelAttribute("editProduct") Product product) {
        if (product.getProductName() == null || product.getProductName().trim().isEmpty()) {
            return "redirect:/product?error=nombre";
        }
        if (product.getPrice() == null) {
            return "redirect:/product?error=precio";
        }

        boolean exists = productService.existByProductCode(code);
        if (exists) {
            product.setProductCode(code);
            productService.update(code, product);
        }
        return "redirect:/product";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/delete/{code}")
    public String deleteProduct(@PathVariable("code") Long code) {
        boolean exists = productService.existByProductCode(code);
        if (exists) {
            productService.delete(code);
        }
        return "redirect:/product";
    }
}