package com.loja.jogostabuleiro.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JogoTabuleiroDTO {
    
    private Long id;
    
    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 2, max = 100, message = "Nome deve ter entre 2 e 100 caracteres")
    private String nome;
    
    @NotBlank(message = "Descrição é obrigatória")
    @Size(min = 10, max = 500, message = "Descrição deve ter entre 10 e 500 caracteres")
    private String descricao;
    
    @NotNull(message = "Preço é obrigatório")
    @DecimalMin(value = "0.01", message = "Preço deve ser maior que zero")
    private BigDecimal preco;
    
    @NotNull(message = "Idade mínima é obrigatória")
    @Min(value = 3, message = "Idade mínima deve ser pelo menos 3 anos")
    @Max(value = 99, message = "Idade mínima não pode ser maior que 99 anos")
    private Integer idadeMinima;
    
    @NotNull(message = "Número de jogadores é obrigatório")
    @Min(value = 1, message = "Deve ter pelo menos 1 jogador")
    @Max(value = 20, message = "Máximo de 20 jogadores")
    private Integer numeroJogadores;
    
    @NotNull(message = "Tempo de jogo é obrigatório")
    @Min(value = 5, message = "Tempo mínimo de jogo é 5 minutos")
    @Max(value = 600, message = "Tempo máximo de jogo é 600 minutos")
    private Integer tempoJogoMinutos;
    
    @NotBlank(message = "Categoria é obrigatória")
    @Size(min = 3, max = 50, message = "Categoria deve ter entre 3 e 50 caracteres")
    private String categoria;
    
    private String imgUrl;
    private LocalDate dataCadastro;
    private boolean deleted;
    
    // Relacionamentos
    private Long categoriaId;
    private String categoriaNome;
    private Set<TagDTO> tags;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class TagDTO {
    private Long id;
    private String nome;
    private String cor;
}

