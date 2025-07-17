package com.loja.jogostabuleiro.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaDTO {
    
    private Long id;
    
    @NotBlank(message = "Nome da categoria é obrigatório")
    @Size(min = 2, max = 50, message = "Nome deve ter entre 2 e 50 caracteres")
    private String nome;
    
    @Size(max = 200, message = "Descrição deve ter no máximo 200 caracteres")
    private String descricao;
    
    private Boolean ativa;
    private Integer totalJogos;
}

