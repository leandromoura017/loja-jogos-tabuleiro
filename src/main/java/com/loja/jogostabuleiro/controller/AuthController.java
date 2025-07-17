package com.loja.jogostabuleiro.controller;

import com.loja.jogostabuleiro.model.Usuario;
import com.loja.jogostabuleiro.service.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "logout", required = false) String logout,
                        Model model) {

        if (error != null) {
            model.addAttribute("erro", "Usuário ou senha inválidos!");
        }

        if (logout != null) {
            model.addAttribute("sucesso", "Logout realizado com sucesso!");
        }

        model.addAttribute("title", "Login");
        return "login";
    }

    // Endpoint adicional para logout via GET (opcional, para compatibilidade)
    @GetMapping("/logout")
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout=true";
    }

    @GetMapping("/cadusuario")
    public String cadastroUsuario(Model model) {
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("title", "Cadastro de Usuário");
        return "cadastro-usuario";
    }

    @PostMapping("/salvarUsuario")
    public String salvarUsuario(@Valid @ModelAttribute Usuario usuario,
                                BindingResult result,
                                Model model,
                                RedirectAttributes redirectAttributes) {

        // Verificar se já existe usuário com o mesmo username
        if (usuarioService.existsByUsername(usuario.getUsername())) {
            result.rejectValue("username", "error.usuario", "Este username já está em uso!");
        }

        // Verificar se já existe usuário com o mesmo email
        if (usuarioService.existsByEmail(usuario.getEmail())) {
            result.rejectValue("email", "error.usuario", "Este email já está em uso!");
        }

        if (result.hasErrors()) {
            model.addAttribute("title", "Cadastro de Usuário");
            return "cadastro-usuario";
        }

        try {
            usuarioService.save(usuario);
            redirectAttributes.addFlashAttribute("sucesso", "Usuário cadastrado com sucesso! Faça login para continuar.");
            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("erro", "Erro ao cadastrar usuário: " + e.getMessage());
            model.addAttribute("title", "Cadastro de Usuário");
            return "cadastro-usuario";
        }
    }
}

