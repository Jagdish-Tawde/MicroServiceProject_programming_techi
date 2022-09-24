package com.programmingtechie.productservice.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
// import org.springframework.data.annotation.Id;
// import org.springframework.data.mongodb.core.mapping.Document;

//import javax.annotation.Generated;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;

@Document(value = "product")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Product {
  @Id
//  @Generated
  private String id;

  private String name;
  private String description;
  private String price;
}
