package modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author lafer
 */
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

    /**
     *
     * @param cui
     * @param nit
     * @param domicilio
     * @param nombre
     * @param apellido
     * @param telefono
     * @param fechaNacimiento
     */
    public Persona(int cui, String nit, String domicilio, String nombre, String apellido, int telefono, Date fechaNacimiento) {
        this.cui = cui;
        this.nit = nit;
        this.domicilio = domicilio;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.fechaNacimiento = fechaNacimiento;
    }

    /**
     *
     */
    public Persona() {
    }

    /**
     *
     * @return
     */
    public long getCui() {
        return cui;
    }

    /**
     *
     * @param cui
     */
    public void setCui(long cui) {
        this.cui = cui;
    }

    /**
     *
     * @return
     */
    public String getNit() {
        return nit;
    }

    /**
     *
     * @param nit
     */
    public void setNit(String nit) {
        this.nit = nit;
    }

    /**
     *
     * @return
     */
    public String getDomicilio() {
        return domicilio;
    }

    /**
     *
     * @param domicilio
     */
    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    /**
     *
     * @return
     */
    public String getNombre() {
        return nombre;
    }

    /**
     *
     * @param nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     *
     * @return
     */
    public String getApellido() {
        return apellido;
    }

    /**
     *
     * @param apellido
     */
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    /**
     *
     * @return
     */
    public int getTelefono() {
        return telefono;
    }

    /**
     *
     * @param telefono
     */
    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    /**
     *
     * @return
     */
    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    /**
     *
     * @param fechaNacimiento
     */
    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return this.nombre + " " + this.apellido;
    }
}
