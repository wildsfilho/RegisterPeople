package com.register.people.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "people")
public class People {

    @Id
    @GeneratedValue
    private Integer id;

    @NotBlank
    @Column(nullable = false)
    private String nome;

    @CPF(message = "CPF invalido")
    @NotBlank
    @Column(nullable = false)
    private String cpf;


    @Column(nullable = false)
    @NotBlank
    @JsonProperty("data_nascimento")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate data_nascimento;

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, mappedBy = "people")
    @JsonManagedReference
    private List<Contact> contact;

}
