package com.loja.jogostabuleiro.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "jogos_tabuleiro")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JogoTabuleiro {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 2, max = 100, message = "Nome deve ter entre 2 e 100 caracteres")
    @Column(nullable = false, length = 100)
    private String nome;
    
    @NotBlank(message = "Descrição é obrigatória")
    @Size(min = 10, max = 500, message = "Descrição deve ter entre 10 e 500 caracteres")
    @Column(nullable = false, length = 500)
    private String descricao;
    
    @NotNull(message = "Preço é obrigatório")
    @DecimalMin(value = "0.01", message = "Preço deve ser maior que zero")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal preco;
    
    @NotNull(message = "Idade mínima é obrigatória")
    @Min(value = 3, message = "Idade mínima deve ser pelo menos 3 anos")
    @Max(value = 99, message = "Idade mínima não pode ser maior que 99 anos")
    @Column(nullable = false)
    private Integer idadeMinima;
    
    @NotNull(message = "Número de jogadores é obrigatório")
    @Min(value = 1, message = "Deve ter pelo menos 1 jogador")
    @Max(value = 20, message = "Máximo de 20 jogadores")
    @Column(nullable = false)
    private Integer numeroJogadores;
    
    @NotNull(message = "Tempo de jogo é obrigatório")
    @Min(value = 5, message = "Tempo mínimo de jogo é 5 minutos")
    @Max(value = 600, message = "Tempo máximo de jogo é 600 minutos")
    @Column(nullable = false)
    private Integer tempoJogoMinutos;
    
    @NotBlank(message = "Categoria é obrigatória")
    @Size(min = 3, max = 50, message = "Categoria deve ter entre 3 e 50 caracteres")
    @Column(nullable = false, length = 50)
    private String categoria;
    
    @Column(name = "is_deleted")
    @Temporal(TemporalType.TIMESTAMP)
    private Date isDeleted;
    
    @Column(name = "img_url", length = 255)
    private String imgUrl;
    
    @Column(name = "data_cadastro")
    private LocalDate dataCadastro;
    
    @PrePersist
    protected void onCreate() {
        dataCadastro = LocalDate.now();
    }
    
    // Relacionamento N-1: Muitos jogos podem ter uma categoria
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id")
    private Categoria categoriaObj;
    
    // Relacionamento N-N: Um jogo pode ter muitas tags
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "jogo_tags",
        joinColumns = @JoinColumn(name = "jogo_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags;
    
    // Método auxiliar para verificar se o jogo está deletado
    public boolean isDeleted() {
        return isDeleted != null;
    }

    public void setDeleted(boolean b) {

    }
}

