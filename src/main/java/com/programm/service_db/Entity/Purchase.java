package com.programm.service_db.Entity;

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
    Long id;
    @OneToOne()
    @JoinColumn(name = "buyer_id")
    Buyer buyer;
    @OneToOne()
    @JoinColumn(name = "product_id")
    Product product;
    @Column
    String data;
}
