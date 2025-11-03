package jame.dev.inventory.mapper.out;

import jame.dev.inventory.dtos.employee.out.EmployeeDto;
import jame.dev.inventory.dtos.sale.in.SaleDtoIn;
import jame.dev.inventory.dtos.sale.out.SaleDto;
import jame.dev.inventory.dtos.sale.out.SaleOrderDto;
import jame.dev.inventory.exceptions.EmployeeNotFoundException;
import jame.dev.inventory.exceptions.SaleOrderNotFoundException;
import jame.dev.inventory.mapper.in.InputMapper;
import jame.dev.inventory.mapper.in.OutputMapper;
import jame.dev.inventory.models.EmployeeEntity;
import jame.dev.inventory.models.SaleEntity;
import jame.dev.inventory.models.SaleOrderEntity;
import jame.dev.inventory.service.in.EmployeeService;
import jame.dev.inventory.service.in.SaleOrderService;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SaleMapper implements
        OutputMapper<SaleDto, SaleEntity>,
        InputMapper<SaleEntity, SaleDtoIn> {

   private final OutputMapper<EmployeeDto, EmployeeEntity> employeeMapper;
   private final OutputMapper<SaleOrderDto, SaleOrderEntity> saleOrderMapper;
   private final EmployeeService employeeService;
   private final SaleOrderService saleOrderService;

   public SaleMapper(OutputMapper<EmployeeDto, EmployeeEntity> employeeMapper, OutputMapper<SaleOrderDto, SaleOrderEntity> saleOrderMapper, EmployeeService employeeService, SaleOrderService saleOrderService) {
      this.employeeMapper = employeeMapper;
      this.saleOrderMapper = saleOrderMapper;
      this.employeeService = employeeService;
      this.saleOrderService = saleOrderService;
   }

   @Override
   public SaleEntity inputToEntity(SaleDtoIn dto) {
      List<SaleOrderEntity> orders = getSaleOrders(dto.saleOrderIds());
      return SaleEntity.builder()
              .saleOrders(orders)
              .employee(getEmployee(dto.employeeId()))
              .saleDate(LocalDate.now())
              .active(true)
              .build();
   }

   @Override
   public SaleDto toDto(SaleEntity entity) {
      return SaleDto.builder()
              .id(entity.getId())
              .orders(getSaleOrderDtoList(entity.getSaleOrders()))
              .employee(employeeMapper.toDto(getEmployee(entity.getEmployee().getId())))
              .saleDate(entity.getSaleDate())
              .saleCost(entity.getTotalCost())
              .build();
   }

   @Override
   public SaleEntity toEntity(SaleDto dto) {
      return SaleEntity.builder()
              .id(dto.id())
              .saleOrders(getSaleOrderList(dto.orders()))
              .employee(employeeMapper.toEntity(dto.employee()))
              .saleDate(dto.saleDate())
              .active(true)
              .build();
   }

   private List<SaleOrderEntity> getSaleOrderList(List<SaleOrderDto> dtoList){
      return dtoList.stream()
              .map(saleOrderMapper::toEntity)
              .collect(Collectors.toList());
   }

   private List<SaleOrderDto> getSaleOrderDtoList(List<SaleOrderEntity> entityList){
      return entityList.stream()
              .map(saleOrderMapper::toDto)
              .collect(Collectors.toList());
   }

   private EmployeeEntity getEmployee(Long id){
      return employeeService.getEmployeeById(id)
              .orElseThrow(() -> new EmployeeNotFoundException(("Employee's not found.")));
   }

   private List<SaleOrderEntity> getSaleOrders(List<Long> ids){
      List<SaleOrderEntity> orders = saleOrderService.getAllByIds(ids);
      if(ids.size() != orders.size()){
         throw new SaleOrderNotFoundException("One or more orders can't be founded.");
      }
      return orders;
   }
}
