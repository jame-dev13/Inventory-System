package jame.dev.inventory.restController.priv;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/emp")
@PreAuthorize("hasRole('EMPLOYEE')")
public class EmployeeController {

   @GetMapping("/greet")
   public String greet(){
      return "Hello Employee";
   }
}
