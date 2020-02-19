package edu.egg.Tinder.Servicios;

import edu.egg.Tinder.Repositorios.FotoR;
import edu.egg.Tinder.entidades.Foto;
import edu.egg.Tinder.errores.ErrorServicio;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;

@Service
public class FotoS {

    @Autowired
    private FotoR fotoR;
    
    @Transactional
    public Foto guardar(MultipartFile archivo) throws ErrorServicio {
        if (archivo != null) {
            try {
                Foto foto = new Foto();
                foto.setMime(archivo.getContentType());
                foto.setNombre(archivo.getName());
                foto.setContenido(archivo.getBytes());

                return fotoR.save(foto);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return null;

    }
    
    @Transactional
    public Foto actualizar(String idFoto, MultipartFile archivo) throws ErrorServicio{
        if (archivo != null) {
            try {
                Foto foto = new Foto();
                
                if(idFoto != null){
                    Optional<Foto> respuesta= fotoR.findById(idFoto);
                    if(respuesta.isPresent()){
                        foto= respuesta.get();
                    }
                }
                foto.setMime(archivo.getContentType());
                foto.setNombre(archivo.getName());
                foto.setContenido(archivo.getBytes());

                return fotoR.save(foto);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return null;
    }
    
}