package com.loja.jogostabuleiro.repository;

import com.loja.jogostabuleiro.model.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    
    Optional<Tag> findByNome(String nome);
    
    boolean existsByNome(String nome);
    
    @Query("SELECT t FROM Tag t WHERE t.nome LIKE %:nome%")
    Page<Tag> findByNomeContaining(String nome, Pageable pageable);
    
    @Query("SELECT t FROM Tag t WHERE t.id IN :ids")
    Set<Tag> findByIdIn(List<Long> ids);
}

