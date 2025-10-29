package jame.dev.inventory.mapper.out;

import jame.dev.inventory.dtos.employee.out.EmployeeDto;
import jame.dev.inventory.dtos.sale.out.SaleDto;
import jame.dev.inventory.dtos.sale.out.SaleOrderDto;
import jame.dev.inventory.mapper.in.DtoMapper;
import jame.dev.inventory.models.EmployeeEntity;
import jame.dev.inventory.models.SaleEntity;
import jame.dev.inventory.models.SaleOrderEntity;
import org.springframework.stereotype.Component;

@Component
public class SaleMapper implements DtoMapper<SaleDto, SaleEntity> {

   private final DtoMapper<EmployeeDto, EmployeeEntity> employeeMapper;
   private final DtoMapper<SaleOrderDto, SaleOrderEntity> saleOrderMapper;

   public SaleMapper(DtoMapper<EmployeeDto, EmployeeEntity> employeeMapper,
                     DtoMapper<SaleOrderDto, SaleOrderEntity> saleOrderMapper) {
      this.employeeMapper = employeeMapper;
      this.saleOrderMapper = saleOrderMapper;
   }

   @Override
   public SaleDto mapToDto(SaleEntity entity) {
      return SaleDto.builder()
              .id(entity.getId())
              .orders(entity.getSaleOrders().stream()
                      .map(saleOrderMapper::mapToDto)
                      .toList())
              .employee(employeeMapper.mapToDto(entity.getEmployee()))
              .saleDate(entity.getSaleDate())
              .saleCost(entity.getTotalCost())
              .build();
   }
}
