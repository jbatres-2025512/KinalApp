package com.jhulianbatres.kinlapp.controller;

import com.jhulianbatres.kinlapp.entity.Sale;
import com.jhulianbatres.kinlapp.entity.User;
import com.jhulianbatres.kinlapp.service.ClientService;
import com.jhulianbatres.kinlapp.service.SaleService;
import com.jhulianbatres.kinlapp.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/sale")
public class SaleWebController {

    private final SaleService saleService;
    private final UserService userService;
    private final ClientService clientService;

    public SaleWebController(SaleService saleService, UserService userService, ClientService clientService) {
        this.saleService = saleService;
        this.userService = userService;
        this.clientService = clientService;
    }

    private void loadCommonModel(Model model, UserDetails userDetails) {
        model.addAttribute("listSales", saleService.listAll());
        model.addAttribute("listClients", clientService.findByClientsStates());


        if (userDetails != null) {
            User loggedUser = userService.findByUserName(userDetails.getUsername());
            model.addAttribute("loggedUser", loggedUser);
        }

        Sale newSale = new Sale();
        newSale.setSaleState(1);
        model.addAttribute("sale", newSale);
    }

    @GetMapping
    public String viewSales(Model model,
                            @AuthenticationPrincipal UserDetails userDetails) {
        loadCommonModel(model, userDetails);
        return "sale";
    }

    @GetMapping("/search")
    public String searchSale(@RequestParam(value = "code", required = false) String code,
                             Model model,
                             @AuthenticationPrincipal UserDetails userDetails) {
        if (code != null && !code.trim().isEmpty()) {
            try {
                Long saleCode = Long.parseLong(code);
                Optional<Sale> found = saleService.findBySaleCode(saleCode);
                if (found.isEmpty()) return "redirect:/sale?error=No se encontró ninguna venta con el código " + code;
                model.addAttribute("listSales", List.of(found.get()));
            } catch (NumberFormatException e) {
                return "redirect:/sale?error=El código de venta debe ser un número";
            }
        } else {
            model.addAttribute("listSales", saleService.listAll());
        }

        model.addAttribute("listClients", clientService.findByClientsStates());
        if (userDetails != null) {
            model.addAttribute("loggedUser", userService.findByUserName(userDetails.getUsername()));
        }
        Sale newSale = new Sale();
        newSale.setSaleState(1);
        model.addAttribute("sale", newSale);
        return "sale";
    }

    @PostMapping("/add")
    public String addSale(@ModelAttribute("sale") Sale sale,
                          @AuthenticationPrincipal UserDetails userDetails) {
        try {
            if (userDetails != null && (sale.getUserCode() == null || sale.getUserCode().getUserCode() == null)) {
                User loggedUser = userService.findByUserName(userDetails.getUsername());
                sale.setUserCode(loggedUser);
            }
            saleService.save(sale);
            return "redirect:/sale";
        } catch (IllegalArgumentException e) {
            return "redirect:/sale?error=" + e.getMessage();
        } catch (Exception e) {
            return "redirect:/sale?error=Error al registrar la venta. Verifique que el DPI del cliente exista.";
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/edit/search")
    public String searchSaleToEdit(@RequestParam("code") String code,
                                   Model model,
                                   @AuthenticationPrincipal UserDetails userDetails) {
        loadCommonModel(model, userDetails);
        try {
            Long saleCode = Long.parseLong(code);
            Optional<Sale> saleOpt = saleService.findBySaleCode(saleCode);
            if (saleOpt.isPresent()) {
                model.addAttribute("editSale", saleOpt.get());
            } else {
                return "redirect:/sale?error=No existe ninguna venta con el código " + code;
            }
        } catch (NumberFormatException e) {
            return "redirect:/sale?error=El código debe ser un número válido";
        }
        return "sale";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/edit/{code}")
    public String updateSale(@PathVariable("code") Long code,
                             @ModelAttribute("editSale") Sale sale) {
        try {
            saleService.update(code, sale);
            return "redirect:/sale";
        } catch (IllegalArgumentException e) {
            return "redirect:/sale?error=" + e.getMessage();
        } catch (Exception e) {
            return "redirect:/sale?error=No se pudo actualizar la venta";
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/delete/{code}")
    public String deleteSale(@PathVariable("code") Long code) {
        try {
            saleService.delete(code);
            return "redirect:/sale";
        } catch (Exception e) {
            return "redirect:/sale?error=No se pudo eliminar la venta. Puede tener detalles asociados.";
        }
    }
}