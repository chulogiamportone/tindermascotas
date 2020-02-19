/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.egg.Tinder.entidades;


import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;

import edu.egg.Tinder.Enumeraciones.Sexo;
import javax.persistence.OneToOne;

@Entity
public class Mascota {

   
    @Id
    @GeneratedValue(generator ="uuid")
    @GenericGenerator(name="uuid",strategy = "uuid2")
    private String Id;
    private String nombre;

    @Temporal(TemporalType.TIMESTAMP)
    private Date Alta;
    @Temporal(TemporalType.TIMESTAMP)
   private Date Baja;
    @ManyToOne
    private Usuario usuario;
    
    @Enumerated(EnumType.STRING)
    private Sexo sexo;
    
    @OneToOne
    private Foto foto;
    
    public String getId() {
        return Id;
    }

    
    public void setId(String Id) {
        this.Id = Id;
    }

    public String getNombre() {
        return nombre;
    }

    
    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    
    public Usuario getUsuario() {
        return usuario;
    }

   
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    
    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }
    
   
    public Foto getFoto() {
        return foto;
    }

    public void setFoto(Foto foto) {
        this.foto = foto;
    }

}
