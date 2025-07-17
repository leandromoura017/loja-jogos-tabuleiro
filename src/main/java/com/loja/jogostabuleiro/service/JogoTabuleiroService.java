package com.loja.jogostabuleiro.service;

import com.loja.jogostabuleiro.model.JogoTabuleiro;
import com.loja.jogostabuleiro.repository.JogoTabuleiroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class JogoTabuleiroService {

    @Autowired
    private JogoTabuleiroRepository repository;

    private final String[] imagensDisponiveis = {
        "/images/jogo1.jpg",
        "/images/jogo2.jpg",
        "/images/jogo3.jpg",
        "/images/jogo4.jpg",
        "/images/jogo5.jpg",
        "/images/jogo6.jpg",
        "/images/jogo7.jpg",
        "/images/jogo8.jpg"
    };

    public List<JogoTabuleiro> findAllNotDeleted() {
        return repository.findAllNotDeleted();
    }

    public List<JogoTabuleiro> findAll() {
        return repository.findAll();
    }

    public Optional<JogoTabuleiro> findById(Long id) {
        return repository.findById(id);
    }

    public JogoTabuleiro save(JogoTabuleiro jogo) {
        // Selecionar imagem aleatória se não foi definida
        if (jogo.getImgUrl() == null || jogo.getImgUrl().isEmpty()) {
            Random random = new Random();
            jogo.setImgUrl(imagensDisponiveis[random.nextInt(imagensDisponiveis.length)]);
        }
        return repository.save(jogo);
    }

    public void deleteById(Long id) {
        Optional<JogoTabuleiro> jogo = repository.findById(id);
        if (jogo.isPresent()) {
            JogoTabuleiro jogoTabuleiro = jogo.get();
            jogoTabuleiro.setIsDeleted(new Date());
            repository.save(jogoTabuleiro);
        }
    }

    public void restoreById(Long id) {
        Optional<JogoTabuleiro> jogo = repository.findById(id);
        if (jogo.isPresent()) {
            JogoTabuleiro jogoTabuleiro = jogo.get();
            jogoTabuleiro.setIsDeleted(null);
            repository.save(jogoTabuleiro);
        }
    }

    public List<JogoTabuleiro> findByCategoria(String categoria) {
        return repository.findByCategoriaAndNotDeleted(categoria);
    }

    public List<JogoTabuleiro> findByPrecoRange(java.math.BigDecimal precoMin, java.math.BigDecimal precoMax) {
        return repository.findByPrecoRangeAndNotDeleted(precoMin, precoMax);
    }
}

