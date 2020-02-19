package edu.egg.Tinder.Repositorios;

import edu.egg.Tinder.entidades.Mascota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MascotaR extends JpaRepository<Mascota, String> {
    
    
}
