package com.jhulianbatres.kinlapp.controller;

import com.jhulianbatres.kinlapp.entity.Sale;
import com.jhulianbatres.kinlapp.entity.SaleDetail;
import com.jhulianbatres.kinlapp.service.ProductService;
import com.jhulianbatres.kinlapp.service.SaleDetailService;
import com.jhulianbatres.kinlapp.service.SaleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/saleDetail")
public class SaleDetailWebController {

    private final SaleDetailService saleDetailService;
    private final SaleService saleService;
    private final ProductService productService;

    public SaleDetailWebController(SaleDetailService saleDetailService, SaleService saleService, ProductService productService) {
        this.saleDetailService = saleDetailService;
        this.saleService = saleService;
        this.productService = productService;
    }

    @GetMapping("/{saleCode}")
    public String viewSaleInvoice(@PathVariable Long saleCode, Model model) {

        Sale sale = saleService.findBySaleCode(saleCode).orElse(null);

        if (sale == null) {
            return "redirect:/sale?error=Venta no encontrada";
        }

        model.addAttribute("sale", sale);
        model.addAttribute("details", saleDetailService.findBySaleCode(saleCode));
        model.addAttribute("products", productService.listAll());
        model.addAttribute("newDetail", new SaleDetail());

        return "sale-detail";
    }


    @PostMapping("/add/{saleCode}")
    public String addProductToSale(@PathVariable Long saleCode, @ModelAttribute("newDetail") SaleDetail detail) {
        try {
            saleService.findBySaleCode(saleCode).ifPresent(detail::setSaleCode);

            saleDetailService.save(detail);

            return "redirect:/saleDetail/" + saleCode;
        } catch (Exception e) {
            return "redirect:/saleDetail/" + saleCode + "?error=" + e.getMessage();
        }
    }

    @PostMapping("/delete/{saleCode}/{detailCode}")
    public String removeProductFromSale(@PathVariable Long saleCode, @PathVariable Long detailCode) {
        try {
            saleDetailService.delete(detailCode);
            return "redirect:/saleDetail/" + saleCode;
        } catch (Exception e) {
            return "redirect:/saleDetail/" + saleCode + "?error=No se pudo eliminar el item";
        }
    }
}