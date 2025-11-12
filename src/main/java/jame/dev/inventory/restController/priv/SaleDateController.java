package jame.dev.inventory.restController.priv;

import jakarta.annotation.Nonnull;
import jame.dev.inventory.cache.in.Cache;
import jame.dev.inventory.dtos.date.SaleDateDto;
import jame.dev.inventory.dtos.date.SellerNameDto;
import jame.dev.inventory.dtos.date.TotalAmountSellDto;
import jame.dev.inventory.mapper.in.OutputMapper;
import jame.dev.inventory.models.dao.SaleDateEntity;
import jame.dev.inventory.service.in.SaleDateService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static jame.dev.inventory.cache.CacheKeys.SALE_DATE;

@RestController
@RequestMapping("${app.mapping}/dates")
@PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
public class SaleDateController {

   private final SaleDateService saleDateService;
   private final OutputMapper<SaleDateDto, SaleDateEntity> mapperOut;
   private final Cache<SaleDateDto> cache;

   public SaleDateController(SaleDateService saleDateService, OutputMapper<SaleDateDto, SaleDateEntity> mapperOut, Cache<SaleDateDto> cache) {
      this.saleDateService = saleDateService;
      this.mapperOut = mapperOut;
      this.cache = cache;
   }

   @GetMapping
   public ResponseEntity<List<SaleDateDto>> getDataDate(){
      Optional<List<SaleDateDto>> cacheList = cache.getCache(SALE_DATE.getName());
      if(cacheList.isPresent()){
         return ResponseEntity.ok()
                 .contentType(MediaType.APPLICATION_JSON)
                 .body(cacheList.get());
      }
      List<SaleDateDto> dtoList = saleDateService.getAll()
              .stream()
              .map(mapperOut::toDto)
              .toList();
      cache.saveCache(SALE_DATE.getName(), dtoList);
      return ResponseEntity.ok()
              .contentType(MediaType.APPLICATION_JSON)
              .body(dtoList);
   }

   @GetMapping("{date}/seller-names")
   public ResponseEntity<SellerNameDto> getSellerNames(@PathVariable @Nonnull final LocalDate date){
      return ResponseEntity.ok()
              .contentType(MediaType.APPLICATION_JSON)
              .body(new SellerNameDto(saleDateService.getEmployeesWhoSellByDate(date)));
   }

   @GetMapping("{date}/total")
   public ResponseEntity<TotalAmountSellDto> getTotalAmountSellForDate(@PathVariable @Nonnull final LocalDate date){
      return ResponseEntity.ok()
              .contentType(MediaType.APPLICATION_JSON)
              .body(new TotalAmountSellDto(saleDateService.getTotalAmountSellByDate(date)));
   }

}
