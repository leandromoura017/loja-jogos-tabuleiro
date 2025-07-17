package com.loja.jogostabuleiro.repository;

import com.loja.jogostabuleiro.model.JogoTabuleiro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JogoTabuleiroRepository extends JpaRepository<JogoTabuleiro, Long> {
    
    // Buscar jogos não deletados
    @Query("SELECT j FROM JogoTabuleiro j WHERE j.isDeleted IS NULL")
    List<JogoTabuleiro> findAllNotDeleted();
    
    // Buscar jogos não deletados com paginação
    @Query("SELECT j FROM JogoTabuleiro j WHERE j.isDeleted IS NULL")
    Page<JogoTabuleiro> findAllNotDeleted(Pageable pageable);
    
    // Buscar todos os jogos (incluindo deletados)
    List<JogoTabuleiro> findAll();
    
    // Buscar jogos por categoria (não deletados)
    @Query("SELECT j FROM JogoTabuleiro j WHERE j.categoria = :categoria AND j.isDeleted IS NULL")
    List<JogoTabuleiro> findByCategoriaAndNotDeleted(String categoria);
    
    // Buscar jogos por categoria com paginação
    @Query("SELECT j FROM JogoTabuleiro j WHERE j.categoriaObj.id = :categoriaId AND j.isDeleted IS NULL")
    Page<JogoTabuleiro> findByCategoriaIdAndNotDeleted(@Param("categoriaId") Long categoriaId, Pageable pageable);
    
    // Buscar jogos por faixa de preço (não deletados)
    @Query("SELECT j FROM JogoTabuleiro j WHERE j.preco BETWEEN :precoMin AND :precoMax AND j.isDeleted IS NULL")
    List<JogoTabuleiro> findByPrecoRangeAndNotDeleted(java.math.BigDecimal precoMin, java.math.BigDecimal precoMax);
    
    // Buscar jogos por nome com paginação
    @Query("SELECT j FROM JogoTabuleiro j WHERE j.nome LIKE %:nome% AND j.isDeleted IS NULL")
    Page<JogoTabuleiro> findByNomeContainingAndNotDeleted(@Param("nome") String nome, Pageable pageable);
    
    // Buscar jogos por tag
    @Query("SELECT j FROM JogoTabuleiro j JOIN j.tags t WHERE t.id = :tagId AND j.isDeleted IS NULL")
    Page<JogoTabuleiro> findByTagIdAndNotDeleted(@Param("tagId") Long tagId, Pageable pageable);
}

