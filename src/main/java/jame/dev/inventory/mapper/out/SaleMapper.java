package jame.dev.inventory.mapper.out;

import jame.dev.inventory.dtos.employee.out.EmployeeDto;
import jame.dev.inventory.dtos.sale.in.SaleDtoIn;
import jame.dev.inventory.dtos.sale.out.SaleDto;
import jame.dev.inventory.dtos.sale.out.SaleOrderDto;
import jame.dev.inventory.exceptions.EmployeeNotFoundException;
import jame.dev.inventory.mapper.in.DtoMapper;
import jame.dev.inventory.mapper.in.EntityMapper;
import jame.dev.inventory.models.EmployeeEntity;
import jame.dev.inventory.models.SaleEntity;
import jame.dev.inventory.models.SaleOrderEntity;
import jame.dev.inventory.service.in.EmployeeService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class SaleMapper implements
        DtoMapper<SaleDto, SaleEntity>,
        EntityMapper<SaleEntity, SaleDtoIn> {

   private final DtoMapper<EmployeeDto, EmployeeEntity> employeeMapper;
   private final DtoMapper<SaleOrderDto, SaleOrderEntity> saleOrderMapper;
   private final EntityMapper<SaleOrderEntity, SaleOrderDto> entityMapperSaleOrder;
   private final EmployeeService employeeService;

   public SaleMapper(DtoMapper<EmployeeDto, EmployeeEntity> employeeMapper,
                     DtoMapper<SaleOrderDto, SaleOrderEntity> saleOrderMapper,
                     EntityMapper<SaleOrderEntity, SaleOrderDto> entityMapperSaleOrder,
                     EmployeeService employeeService) {
      this.employeeMapper = employeeMapper;
      this.saleOrderMapper = saleOrderMapper;
      this.entityMapperSaleOrder = entityMapperSaleOrder;
      this.employeeService = employeeService;
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

   @Override
   public SaleEntity mapToEntity(SaleDtoIn dto) {
      BigDecimal total = dto.orders().stream()
              .map(SaleOrderDto::orderCost)
              .reduce(BigDecimal.ZERO, BigDecimal::add);

      EmployeeEntity employeeEntity = employeeService.getEmployeeById(dto.employeeId())
              .orElseThrow(() -> new EmployeeNotFoundException("Employee not found."));

      return SaleEntity.builder()
              .saleOrders(dto.orders().stream().map(entityMapperSaleOrder::mapToEntity).toList())
              .employee(employeeEntity)
              .saleDate(LocalDate.now())
              .totalCost(total)
              .build();
   }
}
