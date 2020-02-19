package edu.egg.Tinder.Servicios;

import edu.egg.Tinder.entidades.Usuario;
import edu.egg.Tinder.errores.ErrorServicio;
import edu.egg.Tinder.Repositorios.UsuarioR;
import edu.egg.Tinder.controladores.PortalControlador;
import edu.egg.Tinder.entidades.Foto;
import edu.egg.Tinder.entidades.Zona;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UsuarioS implements UserDetailsService {

    @Autowired
    private UsuarioR usuarioR;
    @Autowired
    private NotificacionS notificacionS;

    @Autowired
    private FotoS fotoS;

    @Transactional
    public void registrar(MultipartFile archivo, String nombre, String apellido, String mail, String clave) throws ErrorServicio {
        Validar(nombre, apellido, mail, clave);

        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setMail(mail);
        

        String encriptada = new BCryptPasswordEncoder().encode(clave);
        usuario.setClave(encriptada);
        usuario.setAlta(new Date());

        Foto foto = fotoS.guardar(archivo);
        usuario.setFoto(foto);

        usuarioR.save(usuario);
        
        notificacionS.enviar("Bienvenido al tinder de mascotas", "Tinder de Mascotas", usuario.getMail());
    }

    @Transactional
    public void Modificar(MultipartFile archivo, String id, String nombre, String apellido, String mail, String clave) throws ErrorServicio {
        Validar(nombre, apellido, mail, clave);
        Optional<Usuario> respuesta = usuarioR.findById(id);
        if (respuesta.isPresent()) {
            Usuario usuario = respuesta.get();
            usuario.setApellido(apellido);
            usuario.setNombre(nombre);
            usuario.setMail(mail);
            String encriptada = new BCryptPasswordEncoder().encode(clave);
            usuario.setClave(encriptada);

            String idFoto = null;
            if (usuario.getFoto() != null) {
                idFoto = usuario.getFoto().getId();
            }
            Foto foto = fotoS.actualizar(idFoto, archivo);
            usuario.setFoto(foto);
            usuarioR.save(usuario);
        } else {
            throw new ErrorServicio("No se encontro el usuario solicitado.");
        }
    }

    private void Validar(String nombre, String apellido, String mail, String clave) throws ErrorServicio {

        if (nombre == null || nombre.isEmpty()) {
            throw new ErrorServicio("El nombre del usuario no puede ser nulo.");
        }
        if (apellido == null || apellido.isEmpty()) {
            throw new ErrorServicio("El apellido del usuario no puede ser nulo.");
        }
        if (mail == null || mail.isEmpty()) {
            throw new ErrorServicio("El mail del usuario no puede ser nulo.");
        }
        if (clave == null || clave.isEmpty() || clave.length() < 6) {
            throw new ErrorServicio("La clave del usuario no puede ser nula y debe contener almenos 6 caracteres.");
        }

    }

    @Transactional
    public void deshabilitar(String id) throws ErrorServicio {
        Optional<Usuario> respuesta = usuarioR.findById(id);
        if (respuesta.isPresent()) {
            Usuario usuario = respuesta.get();
            usuario.setBaja(new Date());
            usuarioR.save(usuario);
        } else {
            throw new ErrorServicio("No se encontro el usuario solicitado.");
        }
    }
    
    @Transactional
    public void habilitar(String id) throws ErrorServicio {
        Optional<Usuario> respuesta = usuarioR.findById(id);
        if (respuesta.isPresent()) {
            Usuario usuario = respuesta.get();
            usuario.setBaja(null);
            usuarioR.save(usuario);
        } else {
            throw new ErrorServicio("No se encontro el usuario solicitado.");
        }
    }
    
     @Transactional
    public void login(String mail, String contraseña) throws ErrorServicio {
        Usuario usuario = usuarioR.BuscarPorMail(mail);
        if (usuario!=null){
            if(usuario.getClave()==contraseña){
               PortalControlador p= new PortalControlador();
               p.inicio();
            }
        } else {
            throw new ErrorServicio("No se encontro el usuario solicitado.");
        }
    }


    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
        Usuario usuario = usuarioR.BuscarPorMail(mail);

        if (usuario != null) {
            List<GrantedAuthority> permisos = new ArrayList<>();
            GrantedAuthority p1 = new SimpleGrantedAuthority("Modulo_Fotos");
            GrantedAuthority p2 = new SimpleGrantedAuthority("Modulo_Mascotas");
            GrantedAuthority p3 = new SimpleGrantedAuthority("Modulo_Votos");
            permisos.add(p1);
            permisos.add(p2);
            permisos.add(p3);
            User user = new User(usuario.getMail(), usuario.getClave(), permisos);
            return user;
        } else {
            return null;
        }

    }
}
