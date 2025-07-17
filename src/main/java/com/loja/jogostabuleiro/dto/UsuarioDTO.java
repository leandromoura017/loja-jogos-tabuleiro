package com.loja.jogostabuleiro.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {
    
    private Long id;
    
    @NotBlank(message = "Username é obrigatório")
    @Size(min = 3, max = 50, message = "Username deve ter entre 3 e 50 caracteres")
    private String username;
    
    @NotBlank(message = "Nome completo é obrigatório")
    @Size(min = 2, max = 100, message = "Nome deve ter entre 2 e 100 caracteres")
    private String nomeCompleto;
    
    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email deve ter formato válido")
    private String email;
    
    private Boolean isAdmin;
    private Boolean enabled;
    private LocalDateTime dataCadastro;
    
    // Perfil relacionado
    private PerfilDTO perfil;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class PerfilDTO {
    private Long id;
    private String telefone;
    private String dataNascimento;
    private String bio;
    private String cidade;
    private String estado;
    private String avatarUrl;
    private Boolean receberNewsletter;
}

