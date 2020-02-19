package edu.egg.Tinder.Servicios;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.egg.Tinder.Repositorios.MascotaR;
import edu.egg.Tinder.Repositorios.VotoR;
import edu.egg.Tinder.entidades.Mascota;
import edu.egg.Tinder.entidades.Voto;
import edu.egg.Tinder.errores.ErrorServicio;

@Service
public class VotoS {

    @Autowired
    private VotoR votoR;
    @Autowired
    private NotificacionS notificacionS;
    @Autowired
    private MascotaR mascotaR;

    public void votar(String idUsuario, String idMascota1, String idMascota2) throws ErrorServicio {
        Voto voto = new Voto();
        voto.setFecha(new Date());

        Optional<Mascota> respuesta = mascotaR.findById(idMascota1);
        if (respuesta.isPresent()) {
            Mascota mascota1 = respuesta.get();
            if (mascota1.getUsuario().getId().equals(idUsuario)) {
                voto.setMascota1(mascota1);
            } else {
                throw new ErrorServicio("No tiene permisos para realizar esta operacion");

            }

        } else {
            throw new ErrorServicio("No exite una mascota vinculada a ese identificador");

        }
        Optional<Mascota> respuesta2 = mascotaR.findById(idMascota2);
        if (respuesta2.isPresent()) {
            Mascota mascota2 = respuesta2.get();
            voto.setMascota1(mascota2);

            notificacionS.enviar("La votacion se efectuo con exito", "Tinder de Mascotas", mascota2.getUsuario().getClave());

        } else {
            throw new ErrorServicio("No exite una mascota vinculada a ese identificador");

        }
        votoR.save(voto);
    }

    public void responder(String idUsuario, String idVoto) throws ErrorServicio {
        Optional<Voto> respuesta = votoR.findById(idVoto);
        if (respuesta.isPresent()) {
            Voto voto = respuesta.get();
            voto.setRespuesta(new Date());

            if (voto.getMascota2().getUsuario().getId().equals(idUsuario)) {
                notificacionS.enviar("Tu voto fue correspondido", "Tinder de Mascotas", voto.getMascota1().getUsuario().getMail());

                votoR.save(voto);

            } else {
                throw new ErrorServicio("No tiene permisos para realizar esta operacion");
            }

        } else {
            throw new ErrorServicio("No existe el voto solicitado");
        }
    }
}
