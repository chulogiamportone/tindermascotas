package edu.egg.Tinder.controladores;

import edu.egg.Tinder.Repositorios.ZonaR;
import edu.egg.Tinder.Servicios.UsuarioS;
import edu.egg.Tinder.entidades.Zona;
import edu.egg.Tinder.errores.ErrorServicio;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/")
public class PortalControlador {
    
    @Autowired
    private UsuarioS usuarioS;
    
    @Autowired
    private ZonaR zonaR;
    
    
    @GetMapping("/")
    public String index(){
        return "index.html";
    }
    
    
    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @GetMapping("/inicio")
    public String inicio(){
        return "inicio.html";
    }
    
    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, @RequestParam(required = false) String logout, ModelMap model){
        if(error != null){
            model.put("error", "Nombre de usuario o clave incorrectos.");
        }
        
        if(logout != null){
            model.put("logout", "Ha salido correctamente de la plataforma.");
        }
        
        return "login.html";
    }
    
   
    
    @PostMapping("/registrar")
    public String registrar(ModelMap modelo, MultipartFile archivo, @RequestParam String nombre, @RequestParam String apellido, @RequestParam String mail, @RequestParam String clave1, @RequestParam String clave2, @RequestParam String idZona){
        try {
            usuarioS.registrar(archivo, nombre, apellido, mail, clave1);
        } catch (ErrorServicio ex) {
            

            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("apellido", apellido);
            modelo.put("mail", mail);
            modelo.put("clave1", clave1);
            modelo.put("clave2", clave2);
            
            return "registro.html";
        }
        
        modelo.put("titulo", "Bienvenido a Tinder de Mascotas.");
        modelo.put("descripcion", "Tu usuario fue registrado de manera satisfactoria");
        return "exito.html";
    }
}
