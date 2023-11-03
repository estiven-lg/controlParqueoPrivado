package modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Persona implements Serializable {

    @Id
    private long cui;

    @Basic
    private String nit;
    private String domicilio;
    private String nombre;
    private String apellido;
    private int telefono;

    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;

    public Persona(int cui, String nit, String domicilio, String nombre, String apellido, int telefono, Date fechaNacimiento) {
        this.cui = cui;
        this.nit = nit;
        this.domicilio = domicilio;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.fechaNacimiento = fechaNacimiento;
    }

    public Persona() {
    }

    public long getCui() {
        return cui;
    }

    public void setCui(long cui) {
        this.cui = cui;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    @Override
    public String toString() {
        return this.nombre + " " + this.apellido;
    }

}
