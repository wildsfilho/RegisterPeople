package com.register.people.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "contact")
public class Contact {

    @Id
    @GeneratedValue
    private Integer id;

    @NotBlank
    @Column(nullable = false)
    private String nome;

    @NotBlank
    @Column(nullable = false)
    private String telefone;

    @Email(message = "Email invalido")
    @NotBlank
    @Column(nullable = false)
    private String email;

    @ManyToOne
    @JoinColumn(name = "id_people", referencedColumnName = "id", nullable = false)
    @JsonBackReference
    private People people;
}
