package com.jwt.project.service;

import com.jwt.project.dto.SupplierDto;
import com.jwt.project.entity.Supplier;
import com.jwt.project.exception.ResourceNotFoundException;
import com.jwt.project.repository.SupplierRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SupplierService {
    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private ModelMapper modelMapper;


    public SupplierDto createSupplier(SupplierDto suplier) {
        Supplier supp= modelMapper.map(suplier,Supplier.class);
        Supplier save= supplierRepository.save(supp);
        return modelMapper.map(save,SupplierDto.class);
    }



    public SupplierDto updateSupplier(int supplierId, SupplierDto updatedSupplier) {


        Supplier supp=supplierRepository.findById(supplierId).orElseThrow(()->new ResourceNotFoundException(supplierId + " is not found"));
        supp.setSupplier_name(updatedSupplier.getSupplier_name());
        Supplier save= supplierRepository.save(supp);
        return modelMapper.map(save,SupplierDto.class);

    }

    public void deleteSupplier(int supplierId) {
        Supplier suppById=supplierRepository.findById(supplierId).orElseThrow(()->new ResourceNotFoundException(supplierId + " is not found"));
        supplierRepository.delete(suppById);

    }

    public SupplierDto getSupplierById(int supplierId) {

        Supplier findById= supplierRepository.findById(supplierId).orElseThrow(()->new ResourceNotFoundException(supplierId + " is not found"));
        return this.modelMapper.map(findById,SupplierDto.class);

    }


    public List<SupplierDto> getAllSuppliers() {

        List<Supplier> findAll = supplierRepository.findAll();
        List<SupplierDto> findAllDto= findAll.stream().map(supplier -> modelMapper.map(supplier,SupplierDto.class)).collect(Collectors.toList());
        return  findAllDto ;

    }

}


