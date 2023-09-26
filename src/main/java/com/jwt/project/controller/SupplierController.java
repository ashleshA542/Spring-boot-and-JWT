package com.jwt.project.controller;

import com.jwt.project.dto.SupplierDto;
import com.jwt.project.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"/api/suppliers"})
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    @PostMapping({"/create"})
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<SupplierDto> createSupplier(@RequestBody SupplierDto suplier) {
        SupplierDto createSupplier=supplierService.createSupplier(suplier);
        return new ResponseEntity<SupplierDto>(createSupplier, HttpStatus.CREATED) ;
    }


    @PutMapping("/{supplierId}")/* pathvariable ma dieko supplierId ra ya get mappingma diyeko supplierId same hunuparxa*/
    public ResponseEntity<SupplierDto> updateSupplier(@PathVariable int supplierId, @RequestBody SupplierDto updatedSupplier) {
        SupplierDto updateSupplier = supplierService.updateSupplier(supplierId, updatedSupplier);
        return new ResponseEntity<SupplierDto>(updateSupplier,HttpStatus.ACCEPTED);

    }

    @DeleteMapping("/{supplierId}")
    public ResponseEntity<String> deleteSupplier(@PathVariable int supplierId) {
        supplierService.deleteSupplier(supplierId);
        return new ResponseEntity<String>("Supplier is deleted successfully.",HttpStatus.OK);
    }


    @GetMapping("/{supplierId}")
    public ResponseEntity<SupplierDto> getSupplierById(@PathVariable int supplierId) {

        SupplierDto getById= supplierService.getSupplierById(supplierId);
        return new ResponseEntity<SupplierDto>(getById,HttpStatus.OK);

    }

    @GetMapping({"/viewAll"})
    public ResponseEntity<List<SupplierDto>> getAllSuppliers() {
        List<SupplierDto> viewAll = supplierService.getAllSuppliers();
        return new ResponseEntity<List<SupplierDto>>(viewAll,HttpStatus.ACCEPTED);

    }



}
