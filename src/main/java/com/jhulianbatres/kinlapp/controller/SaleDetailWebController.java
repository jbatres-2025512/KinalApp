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
import java.util.Optional;

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

    @GetMapping
    public String index() {
        return "redirect:/sale";
    }

    @GetMapping("/{saleCode}")
    public String viewSaleInvoice(@PathVariable Long saleCode, Model model) {
        Sale sale = saleService.findBySaleCode(saleCode).orElse(null);
        if (sale == null) return "redirect:/sale?error=Venta no encontrada";

        model.addAttribute("sale", sale);
        model.addAttribute("details", saleDetailService.findBySaleCode(saleCode));
        model.addAttribute("products", productService.listAll());

        SaleDetail newDetail = new SaleDetail();
        newDetail.setSaleCode(sale);
        model.addAttribute("newDetail", newDetail);
        return "saledetail";
    }

    @PostMapping("/add/{saleCode}")
    public String addProductToSale(@PathVariable Long saleCode, @ModelAttribute("newDetail") SaleDetail detail) {
        try {
            saleService.findBySaleCode(saleCode).ifPresent(detail::setSaleCode);
            // Lógica para traer el precio del producto automáticamente
            if (detail.getProductCode() != null) {
                productService.findProductByCode(detail.getProductCode().getProductCode()).ifPresent(p -> {
                    detail.setUnit_price(p.getPrice());
                });
            }
            saleDetailService.save(detail);
            return "redirect:/saleDetail/" + saleCode;
        } catch (Exception e) {
            return "redirect:/saleDetail/" + saleCode + "?error=" + e.getMessage();
        }
    }

    @GetMapping ("/edit/search")
    public String searchDetailToEdit(@RequestParam("saleCode") Long saleCode,
                                     @RequestParam("detailCode") Long detailCode,
                                     Model model) {
        Sale sale = saleService.findBySaleCode(saleCode).orElse(null);

        if (sale == null) {
            return "redirect:/sale?error=Venta no encontrada";
        }

        Optional<SaleDetail> detailOpt = saleDetailService.findBySaleDetailCode(detailCode);

        model.addAttribute("sale", sale);
        model.addAttribute("details", saleDetailService.findBySaleCode(saleCode));
        model.addAttribute("products", productService.listAll());
        model.addAttribute("newDetail", new SaleDetail());

        if (detailOpt.isPresent() && detailOpt.get().getSaleCode().getSaleCode().equals(saleCode)) {
            model.addAttribute("editDetail", detailOpt.get());
        } else {
            return "redirect:/saleDetail/" + saleCode + "?error=No existe el detalle " + detailCode;
        }

        return "saleDetail";
    }

    @PostMapping("/edit/{saleCode}/{detailCode}")
    public String updateDetail(@PathVariable Long saleCode, @PathVariable Long detailCode, @ModelAttribute("editDetail") SaleDetail detail) {
        try {
            detail.setCodeSaleDetail(detailCode);
            saleService.findBySaleCode(saleCode).ifPresent(detail::setSaleCode);
            saleDetailService.save(detail);
            return "redirect:/saleDetail/" + saleCode;
        } catch (Exception e) {
            return "redirect:/saleDetail/" + saleCode + "?error=" + e.getMessage();
        }
    }

    @PostMapping("/delete/{saleCode}/{detailCode}")
    public String removeProduct(@PathVariable Long saleCode, @PathVariable Long detailCode) {
        try {
            saleDetailService.delete(detailCode);
            return "redirect:/saleDetail/" + saleCode;
        } catch (Exception e) {
            return "redirect:/saleDetail/" + saleCode + "?error=No se pudo eliminar";
        }
    }
}