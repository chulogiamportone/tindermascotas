package edu.egg.Tinder.Servicios;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.egg.Tinder.Enumeraciones.Sexo;
import edu.egg.Tinder.Repositorios.MascotaR;
import edu.egg.Tinder.Repositorios.UsuarioR;
import edu.egg.Tinder.entidades.Foto;
import edu.egg.Tinder.entidades.Mascota;
import edu.egg.Tinder.entidades.Usuario;
import edu.egg.Tinder.errores.ErrorServicio;
import javax.transaction.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class MascotaS {

    @Autowired
    private UsuarioR usuarioR;
    @Autowired
    private MascotaR mascotaR;
    @Autowired
    private FotoS fotoS;

    @Transactional
    public void agregarMascota(MultipartFile archivo, String idUsuario, String nombre, Sexo sexo) throws ErrorServicio {
        Usuario usuario = usuarioR.findById(idUsuario).get();
        validar(nombre, sexo);

        Mascota mascota = new Mascota();
        mascota.setNombre(nombre);
        mascota.setSexo(sexo);
        mascota.setAlta(new Date());

        Foto foto = fotoS.guardar(archivo);
        usuario.setFoto(foto);

        mascotaR.save(mascota);

    }

    @Transactional
    public void modificarr(MultipartFile archivo, String idUsuario, String idMascota, String nombre, Sexo sexo) throws ErrorServicio {
        validar(nombre, sexo);
        Optional<Mascota> respuesta = mascotaR.findById(idMascota);
        if (respuesta.isPresent()) {
            Mascota mascota = respuesta.get();
            if (mascota.getUsuario().getId().equals(idUsuario)) {
                mascota.setNombre(nombre);
                mascota.setSexo(sexo);

                String idFoto = null;
                if (mascota.getFoto() != null) {
                    idFoto = mascota.getFoto().getId();
                }
                Foto foto = fotoS.actualizar(idFoto, archivo);
                mascota.setFoto(foto);

                mascotaR.save(mascota);

            } else {
                throw new ErrorServicio("No tiene permisos suficientes para realizar la operacion ");
            }

        } else {
            throw new ErrorServicio("No existe una mascota con el id solicitado");
        }
    }

    @Transactional
    public void eliminar(String idUsuario, String idMascota) throws ErrorServicio {

        Optional<Mascota> respuesta = mascotaR.findById(idMascota);
        if (respuesta.isPresent()) {
            Mascota mascota = respuesta.get();
            if (mascota.getUsuario().getId().equals(idUsuario)) {
                mascota.setBaja(new Date());
                mascotaR.save(mascota);

            } else {
                throw new ErrorServicio("No tiene permisos suficientes para realizar la operacion ");
            }
        } else {
            throw new ErrorServicio("No existe una mascota con el id solicitado");

        }
    }

    private void validar(String nombre, Sexo sexo) throws ErrorServicio {
        if (nombre == null || nombre.isEmpty()) {
            throw new ErrorServicio("El nombre de la mascota no puede ser nulo.");
        }
        if (sexo == null) {
            throw new ErrorServicio("El sexo de la mascota no puede ser nulo.");
        }

    }
}
