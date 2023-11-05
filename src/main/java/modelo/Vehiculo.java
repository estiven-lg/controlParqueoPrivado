package modelo;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author lafer
 */
@Entity
public class Vehiculo implements Serializable {

    @Id
    private String placa;

    @Basic
    private String marca;
    private String linea;
    private String modelo;
    private String color;

    @ManyToOne
    private Persona propietario;

    /**
     *
     * @param placa
     * @param marca
     * @param linea
     * @param modelo
     * @param color
     * @param propietario
     */
    public Vehiculo(String placa, String marca, String linea, String modelo, String color, Persona propietario) {
        this.placa = placa;
        this.marca = marca;
        this.linea = linea;
        this.modelo = modelo;
        this.color = color;
        this.propietario = propietario;
    }

    /**
     *
     */
    public Vehiculo() {
    }

    /**
     *
     * @return
     */
    public String getPlaca() {
        return placa;
    }

    /**
     *
     * @param placa
     */
    public void setPlaca(String placa) {
        this.placa = placa;
    }

    /**
     *
     * @return
     */
    public String getMarca() {
        return marca;
    }

    /**
     *
     * @param marca
     */
    public void setMarca(String marca) {
        this.marca = marca;
    }

    /**
     *
     * @return
     */
    public String getLinea() {
        return linea;
    }

    /**
     *
     * @param linea
     */
    public void setLinea(String linea) {
        this.linea = linea;
    }

    /**
     *
     * @return
     */
    public String getModelo() {
        return modelo;
    }

    /**
     *
     * @param modelo
     */
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    /**
     *
     * @return
     */
    public String getColor() {
        return color;
    }

    /**
     *
     * @param color
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     *
     * @return
     */
    public Persona getPropietario() {
        return propietario;
    }

    /**
     *
     * @param propietario
     */
    public void setPropietario(Persona propietario) {
        this.propietario = propietario;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return this.placa + " " + this.marca;
    }
}
