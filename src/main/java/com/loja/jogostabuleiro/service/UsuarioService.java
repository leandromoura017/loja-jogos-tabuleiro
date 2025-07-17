package com.loja.jogostabuleiro.service;

import com.loja.jogostabuleiro.model.Usuario;
import com.loja.jogostabuleiro.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    
    @Autowired
    private UsuarioRepository repository;
    
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    
    public List<Usuario> findAll() {
        return repository.findAll();
    }
    
    public Optional<Usuario> findById(Long id) {
        return repository.findById(id);
    }
    
    public Optional<Usuario> findByUsername(String username) {
        return repository.findByUsername(username);
    }
    
    public Usuario save(Usuario usuario) {
        // Criptografar a senha antes de salvar
        if (usuario.getPassword() != null && !usuario.getPassword().isEmpty()) {
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        }
        return repository.save(usuario);
    }
    
    public boolean existsByUsername(String username) {
        return repository.existsByUsername(username);
    }
    
    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }
    
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}

