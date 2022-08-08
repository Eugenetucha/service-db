package com.programm.service_db.Model.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name="purchases")
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    Long id;
    @OneToOne()
    @JoinColumn(name = "buyer_id")
    Buyer buyer;
    @OneToOne()
    @JoinColumn(name = "product_id")
    Product product;
    @Column
    String date;
}
