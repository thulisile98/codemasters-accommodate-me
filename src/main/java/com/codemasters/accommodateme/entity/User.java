package com.codemasters.accommodateme.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String email;
    private String password;
   // private String role;

    @JsonIgnore
    @OneToMany(mappedBy = "users", fetch = FetchType.EAGER)
    private List<Application> applications;

    @ManyToOne(cascade = CascadeType.ALL)
    private Role role;

}
