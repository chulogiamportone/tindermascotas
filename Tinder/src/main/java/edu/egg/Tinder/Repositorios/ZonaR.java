package edu.egg.Tinder.Repositorios;

import edu.egg.Tinder.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ZonaR extends JpaRepository<Usuario, String>{
    
}
