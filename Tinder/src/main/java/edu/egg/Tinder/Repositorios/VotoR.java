package edu.egg.Tinder.Repositorios;

import edu.egg.Tinder.entidades.Voto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VotoR  extends JpaRepository<Voto, String>{
    
    @Query ("Select c From Voto c Where c.mascota1.id=:id ORDER BY c.fecha DESC")
    public List<Voto> buscarVotosPropios (@Param ("id")String id);
    
    @Query ("Select c From Voto c Where c.mascota2.id=:id ORDER BY c.fecha DESC")
    public List<Voto> buscarVotosRecibidos (@Param ("id")String id);
}
