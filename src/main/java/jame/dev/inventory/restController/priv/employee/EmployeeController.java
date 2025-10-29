package jame.dev.inventory.restController.priv.employee;


import jame.dev.inventory.dtos.product.out.ProductDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${app.mapping.employee}")
@PreAuthorize("hasRole('EMPLOYEE')")
public class EmployeeController {

   @GetMapping("/products")
   public ResponseEntity<List<ProductDto>> getProducts(){
      return ResponseEntity.ok(null);
   }
}