package edu.egg.Tinder.entidades;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class Voto {
    @Id
    @GeneratedValue(generator ="uuid")
    @GenericGenerator(name="uuid",strategy = "uuid2")
    private String Id;
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Temporal(TemporalType.TIMESTAMP)
    private Date respuesta;
    @ManyToOne
    private Mascota mascota1;
    @ManyToOne
    private Mascota mascota2;
    /**
     * @return the Id
     */
    public String getId() {
        return Id;
    }

    /**
     * @param Id the Id to set
     */
    public void setId(String Id) {
        this.Id = Id;
    }

    /**
     * @return the fecha
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    /**
     * @return the respuesta
     */
    public Date getRespuesta() {
        return respuesta;
    }

    /**
     * @param respuesta the respuesta to set
     */
    public void setRespuesta(Date respuesta) {
        this.respuesta = respuesta;
    }

	public Mascota getMascota1() {
		return mascota1;
	}

	public void setMascota1(Mascota mascota1) {
		this.mascota1 = mascota1;
	}

	public Mascota getMascota2() {
		return mascota2;
	}

	public void setMascota2(Mascota mascota2) {
		this.mascota2 = mascota2;
	}
    
}