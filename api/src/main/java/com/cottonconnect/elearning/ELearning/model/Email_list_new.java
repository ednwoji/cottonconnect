package com.cottonconnect.elearning.ELearning.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "email_list_new")
public class Email_list_new {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name ="email_id" )
    private Long email_id;
    @Column(name = "email_address")
    private String emailAddress;
}
