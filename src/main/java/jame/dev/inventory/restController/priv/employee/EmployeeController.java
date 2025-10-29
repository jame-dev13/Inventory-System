package jame.dev.inventory.restController.priv.employee;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${app.mapping.employee}")
@PreAuthorize("hasRole('EMPLOYEE')")
public class EmployeeController {

}
