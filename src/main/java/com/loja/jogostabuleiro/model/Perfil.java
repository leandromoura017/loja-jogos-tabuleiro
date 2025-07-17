package com.loja.jogostabuleiro.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "perfis")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Perfil {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Size(max = 15, message = "Telefone deve ter no máximo 15 caracteres")
    @Column(length = 15)
    private String telefone;
    
    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;
    
    @Size(max = 500, message = "Bio deve ter no máximo 500 caracteres")
    @Column(length = 500)
    private String bio;
    
    @Size(max = 100, message = "Cidade deve ter no máximo 100 caracteres")
    @Column(length = 100)
    private String cidade;
    
    @Size(max = 2, message = "Estado deve ter no máximo 2 caracteres")
    @Column(length = 2)
    private String estado;
    
    @Column(name = "avatar_url")
    private String avatarUrl;
    
    @Column(name = "receber_newsletter")
    private Boolean receberNewsletter = true;
    
    // Relacionamento 1-1: Um perfil pertence a um usuário
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    private Usuario usuario;
}

