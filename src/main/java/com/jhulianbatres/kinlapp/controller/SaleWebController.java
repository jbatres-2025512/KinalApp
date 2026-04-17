package com.jhulianbatres.kinlapp.controller;

import com.jhulianbatres.kinlapp.entity.Sale;
import com.jhulianbatres.kinlapp.service.SaleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/sale")
public class SaleWebController {

    private final SaleService saleService;

    public SaleWebController(SaleService saleService) {
        this.saleService = saleService;
    }

    @GetMapping
    public String viewSales(Model model) {
        List<Sale> sales = saleService.listAll();
        model.addAttribute("listSales", sales);

        Sale newSale = new Sale();
        newSale.setSaleState(1);
        model.addAttribute("sale", newSale);
        return "sale";
    }

    @GetMapping("/search")
    public String searchSale(@RequestParam(value = "code", required = false) String code, Model model) {
        List<Sale> sales;
        if (code != null && !code.trim().isEmpty()) {
            try {
                Long saleCode = Long.parseLong(code);
                Optional<Sale> found = saleService.findBySaleCode(saleCode);
                sales = found.map(List::of).orElse(List.of());
                if (found.isEmpty()) return "redirect:/sale?error=Venta no encontrada";
            } catch (NumberFormatException e) {
                return "redirect:/sale?error=El código debe ser numérico";
            }
        } else {
            sales = saleService.listAll();
        }
        model.addAttribute("listSales", sales);
        model.addAttribute("sale", new Sale());
        return "sale";
    }

    @PostMapping("/add")
    public String addSale(@ModelAttribute("sale") Sale sale) {
        try {
            saleService.save(sale);
            return "redirect:/sale";
        } catch (IllegalArgumentException e) {
            return "redirect:/sale?error=" + e.getMessage();
        }
    }

    @GetMapping("/edit/search")
    public String searchSaleToEdit(@RequestParam("code") String code, Model model) {
        model.addAttribute("listSales", saleService.listAll());
        model.addAttribute("sale", new Sale());
        try {
            Long saleCode = Long.parseLong(code);
            Optional<Sale> saleOpt = saleService.findBySaleCode(saleCode);
            if (saleOpt.isPresent()) {
                model.addAttribute("editSale", saleOpt.get());
            } else {
                return "redirect:/sale?error=No existe la venta " + code;
            }
        } catch (NumberFormatException e) {
            return "redirect:/sale?error=Código inválido";
        }
        return "sale";
    }

    @PostMapping("/edit/{code}")
    public String updateSale(@PathVariable("code") Long code, @ModelAttribute("editSale") Sale sale) {
        try {
            saleService.update(code, sale);
            return "redirect:/sale";
        } catch (IllegalArgumentException e) {
            return "redirect:/sale?error=" + e.getMessage();
        }
    }

    @PostMapping("/delete/{code}")
    public String deleteSale(@PathVariable("code") Long code) {
        try {
            saleService.delete(code);
            return "redirect:/sale";
        } catch (Exception e) {
            return "redirect:/sale?error=No se pudo eliminar la venta";
        }
    }
}