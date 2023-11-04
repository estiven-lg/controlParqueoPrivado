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

    public Estacionamiento(int numeroParqueo, Vehiculo vehiculo, Date fechaRegistro, boolean disponible) {
        this.numeroParqueo = numeroParqueo;
        this.vehiculo = vehiculo;
        this.fechaRegistro = fechaRegistro;
        this.disponible = disponible;
    }

    public Estacionamiento() {
    }

    public int getNumeroParqueo() {
        return numeroParqueo;
    }

    public void setNumeroParqueo(int numeroParqueo) {
        this.numeroParqueo = numeroParqueo;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public void add(Estacionamiento estacionamiento) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void remove(Estacionamiento estacionamiento) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
