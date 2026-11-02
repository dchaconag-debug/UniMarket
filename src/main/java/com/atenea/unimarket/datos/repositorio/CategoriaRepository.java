package com.atenea.unimarket.datos.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.atenea.unimarket.datos.modelo.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {

    List<Categoria> findAllByOrderByNombreAsc();
}
