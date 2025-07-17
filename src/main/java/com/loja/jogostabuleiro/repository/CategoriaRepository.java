package com.loja.jogostabuleiro.repository;

import com.loja.jogostabuleiro.model.Categoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    
    List<Categoria> findByAtivaTrue();
    
    Optional<Categoria> findByNome(String nome);
    
    boolean existsByNome(String nome);
    
    @Query("SELECT c FROM Categoria c WHERE c.ativa = true")
    Page<Categoria> findAllActive(Pageable pageable);
    
    @Query("SELECT c FROM Categoria c WHERE c.nome LIKE %:nome% AND c.ativa = true")
    Page<Categoria> findByNomeContainingAndAtivaTrue(String nome, Pageable pageable);
}

