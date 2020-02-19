package edu.egg.Tinder.Repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import edu.egg.Tinder.entidades.Usuario;

@Repository
public interface UsuarioR extends JpaRepository<Usuario, String> {
    
    @Query ("Select c From Usuario c Where c.Mail= :mail")
    public Usuario BuscarPorMail (@Param("mail") String mail);
}
