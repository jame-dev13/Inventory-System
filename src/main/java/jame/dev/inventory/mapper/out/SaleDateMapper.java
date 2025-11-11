package jame.dev.inventory.mapper.out;

import jame.dev.inventory.annotations.Mapper;
import jame.dev.inventory.dtos.date.SaleDateDto;
import jame.dev.inventory.dtos.sale.out.SaleDto;
import jame.dev.inventory.mapper.in.OutputMapper;
import jame.dev.inventory.models.dao.SaleDateEntity;
import jame.dev.inventory.models.dao.SaleEntity;

import java.math.BigDecimal;

@Mapper
public class SaleDateMapper implements OutputMapper<SaleDateDto, SaleDateEntity> {

   private final OutputMapper<SaleDto, SaleEntity> saleMapper;

   public SaleDateMapper(OutputMapper<SaleDto, SaleEntity> saleMapper) {
      this.saleMapper = saleMapper;
   }

   @Override
   public SaleDateDto toDto(SaleDateEntity entity) {
      BigDecimal amount = entity.getSales().stream()
              .map(SaleEntity::getTotalCost)
              .reduce(BigDecimal.ZERO, BigDecimal::add);
      return SaleDateDto.builder()
              .date(entity.getSaleDate())
              .sales(entity.getSales().stream().map(saleMapper::toDto).toList())
              .amount(amount)
              .build();
   }

   @Override
   public SaleDateEntity toEntity(SaleDateDto dto) {
      return null;
   }
}
