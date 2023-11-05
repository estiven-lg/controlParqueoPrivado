package modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
public class Estacionamiento implements Serializable {

    @Id
    private int numeroParqueo;

    @OneToOne
    private Vehiculo vehiculo;

    @Temporal(TemporalType.DATE)
    private Date fechaRegistro;

    @Basic
    private boolean disponible;

    /**
     *
     * @param numeroParqueo
     * @param vehiculo
     * @param fechaRegistro
     * @param disponible
     */
    public Estacionamiento(int numeroParqueo, Vehiculo vehiculo, Date fechaRegistro, boolean disponible) {
        this.numeroParqueo = numeroParqueo;
        this.vehiculo = vehiculo;
        this.fechaRegistro = fechaRegistro;
        this.disponible = disponible;
    }

    /**
     *
     */
    public Estacionamiento() {
    }

    /**
     *
     * @return
     */
    public int getNumeroParqueo() {
        return numeroParqueo;
    }

    /**
     *
     * @param numeroParqueo
     */
    public void setNumeroParqueo(int numeroParqueo) {
        this.numeroParqueo = numeroParqueo;
    }

    /**
     *
     * @return
     */
    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    /**
     *
     * @param vehiculo
     */
    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    /**
     *
     * @return
     */
    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    /**
     *
     * @param fechaRegistro
     */
    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    /**
     *
     * @return
     */
    public boolean isDisponible() {
        return disponible;
    }

    /**
     *
     * @param disponible
     */
    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    /**
     *
     * @param estacionamiento
     */
    public void add(Estacionamiento estacionamiento) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    /**
     *
     * @param estacionamiento
     */
    public void remove(Estacionamiento estacionamiento) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
