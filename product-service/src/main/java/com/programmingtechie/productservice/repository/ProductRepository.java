package com.programmingtechie.productservice.repository;

//import com.programmingtechie.productservice.model.Product;
//import org.springframework.data.mongodb.repository.MongoRepository;
import com.programmingtechie.productservice.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
//import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ProductRepository extends MongoRepository<Product,String> {

}
