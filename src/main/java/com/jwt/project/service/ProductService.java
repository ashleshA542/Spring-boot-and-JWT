package com.jwt.project.service;

import com.jwt.project.dto.ProductDto;
import com.jwt.project.entity.Product;
import com.jwt.project.entity.Supplier;
import com.jwt.project.exception.ResourceNotFoundException;
import com.jwt.project.repository.ProductRepository;
import com.jwt.project.repository.SupplierRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private SupplierRepository supplierRepository;

    public ProductDto createProduct (ProductDto product, int supplierId) {

        Supplier sup=supplierRepository.findById(supplierId).orElseThrow(()->new ResourceNotFoundException("This Supplier id is not found "));

        Product prod= modelMapper.map(product,Product.class);
        prod.setSupplier(sup);
        Product save= productRepository.save(prod);
        return modelMapper.map(save,ProductDto.class);
    }
    public List<ProductDto> viewAllProducts() {
        List<Product> findAll = productRepository.findAll();
        List<ProductDto> findAllDto= findAll.stream().map(product -> modelMapper.map(product,ProductDto.class)).collect(Collectors.toList());
        return  findAllDto ;

    }
    public ProductDto viewProductById(int productId) {

        Product findById= productRepository.findById(productId).orElseThrow(()->new ResourceNotFoundException(productId + " is not found"));
        return this.modelMapper.map(findById,ProductDto.class);

    }

    public void deleteProduct(int productId) {
        Product byId=productRepository.findById(productId).orElseThrow(()->new ResourceNotFoundException(productId + " is not found"));
        productRepository.delete(byId);
    }


    public ProductDto updateProduct(int productId, ProductDto updatedProduct) {
        Product oldp=productRepository.findById(productId).orElseThrow(()->new ResourceNotFoundException(productId+ " is not found"));
        oldp.setProduct_name(updatedProduct.getProduct_name());
        oldp.setProduct_price(updatedProduct.getProduct_price());
        oldp.setProduct_quantity(updatedProduct.getProduct_quantity());
        oldp.setLive(updatedProduct.isLive());
        oldp.setStock(updatedProduct.isStock());
        Product save= productRepository.save(oldp);
        return modelMapper.map(save,ProductDto.class);

    }



    //Find product by supplier
    public List<ProductDto> findProductBySupplier(int supplierId){
        Supplier supp=supplierRepository.findById(supplierId).orElseThrow(()->new ResourceNotFoundException("this id supplier is not found"));
        List<Product> findBySupplier=   productRepository.findBySupplier(supp);
        List<ProductDto> save=findBySupplier.stream().map(product -> modelMapper.map(product,ProductDto.class)).collect(Collectors.toList());
        return save;

    }
}
