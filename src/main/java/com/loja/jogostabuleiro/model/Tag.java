package com.loja.jogostabuleiro.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Set;

@Entity
@Table(name = "tags")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"jogos"})
@ToString(exclude = {"jogos"})
public class Tag {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Nome da tag é obrigatório")
    @Size(min = 2, max = 30, message = "Nome deve ter entre 2 e 30 caracteres")
    @Column(unique = true, nullable = false, length = 30)
    private String nome;
    
    @Column(length = 7)
    private String cor = "#007bff"; // Cor em hexadecimal para exibição
    
    // Relacionamento N-N: Uma tag pode estar em muitos jogos e um jogo pode ter muitas tags
    @ManyToMany(mappedBy = "tags", fetch = FetchType.LAZY)
    private Set<JogoTabuleiro> jogos;
}

