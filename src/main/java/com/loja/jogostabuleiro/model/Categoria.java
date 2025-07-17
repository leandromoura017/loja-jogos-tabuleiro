package com.loja.jogostabuleiro.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "categorias")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"jogos"})
@ToString(exclude = {"jogos"})
public class Categoria {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Nome da categoria é obrigatório")
    @Size(min = 2, max = 50, message = "Nome deve ter entre 2 e 50 caracteres")
    @Column(unique = true, nullable = false, length = 50)
    private String nome;
    
    @Size(max = 200, message = "Descrição deve ter no máximo 200 caracteres")
    @Column(length = 200)
    private String descricao;
    
    @Column(nullable = false)
    private Boolean ativa = true;
    
    // Relacionamento 1-N: Uma categoria pode ter muitos jogos
    @OneToMany(mappedBy = "categoriaObj", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<JogoTabuleiro> jogos;
}

