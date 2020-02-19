package edu.egg.Tinder.entidades;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(generator ="uuid")
    @GenericGenerator(name="uuid",strategy = "uuid2")
    private String id;
    private String Nombre;
    private String Apellido;
    private String Mail;
    private String Clave;
    @Temporal(TemporalType.TIMESTAMP)
    private Date Alta;
    @Temporal(TemporalType.TIMESTAMP)
    private Date Baja;
    @ManyToOne
    private Zona zona;

    @OneToOne
    private Foto foto;
    
    public String getId() {
        return id;
    }

   
    public void setId(String id) {
        this.id = id;
    }

    
    public String getNombre() {
        return Nombre;
    }

    
    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    
    public String getApellido() {
        return Apellido;
    }

    
    public void setApellido(String Apellido) {
        this.Apellido = Apellido;
    }

   
    public String getMail() {
        return Mail;
    }

    
    public void setMail(String Mail) {
        this.Mail = Mail;
    }

    public String getClave() {
        return Clave;
    }

    
    public void setClave(String Clave) {
        this.Clave = Clave;
    }

    
    public Date getAlta() {
        return Alta;
    }

    
    public void setAlta(Date Alta) {
        this.Alta = Alta;
    }

   
    public Date getBaja() {
        return Baja;
    }

    
    public void setBaja(Date Baja) {
        this.Baja = Baja;
    }

    /**
     * @return the zona
     */
    public Zona getZona() {
        return zona;
    }

   
    public void setZona(Zona zona) {
        this.zona = zona;
    }

    /**
     * @return the foto
     */
    public Foto getFoto() {
        return foto;
    }

    /**
     * @param foto the foto to set
     */
    public void setFoto(Foto foto) {
        this.foto = foto;
    }

}
