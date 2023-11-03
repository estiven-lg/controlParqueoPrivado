package controlador;

import controlador.exceptions.NonexistentEntityException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Estacionamiento;
import modelo.Persona;
import modelo.Vehiculo;

public class ControladoraPersistencia {

    EstacionamientoJpaController controlEstacionamiento = new EstacionamientoJpaController();
    PersonaJpaController controlPersona = new PersonaJpaController();
    VehiculoJpaController controlVehiculo = new VehiculoJpaController();

    // metodos de persona
    public void crearPersona(Persona persona) {
        try {
            this.controlPersona.create(persona);
        } catch (Exception ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void actualizarPersona(Persona persona) {
        try {
            this.controlPersona.edit(persona);
        } catch (Exception ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public List<Persona> obtenerPersonas() {
        return this.controlPersona.findPersonaEntities();
    }

    public Persona encontrarPersona(long cui) {
        return this.controlPersona.findPersona(cui);
    }

    public void eliminarPersona(long id) {
        try {
            this.controlPersona.destroy(id);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean yaExistePersona(long cui) {
        return (this.controlPersona.findPersona(cui) != null);
    }

    // Metodos Vehiculo
    public void crearVehiculo(Vehiculo vehiculo) {
        try {
            this.controlVehiculo.create(vehiculo);
        } catch (Exception ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void actualizarVehiculo(Vehiculo Vehiculo) {
        try {
            this.controlVehiculo.edit(Vehiculo);
        } catch (Exception ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public List<Vehiculo> obtenerVehiculos() {
        return controlVehiculo.findVehiculoEntities(0, 0);
    }

    public Vehiculo encontrarVehiculo(String placa) {
        return this.controlVehiculo.findVehiculo(placa);
    }

    public void eliminarVehiculo(String placa) {
        try {
            this.controlVehiculo.destroy(placa);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean yaExisteVehiculo(String placa) {
        return (this.controlVehiculo.findVehiculo(placa) != null);
    }

    //estacionamietos metodos
    public Estacionamiento encontrarEstacionamieto(int id) {
        Estacionamiento estacionamiento = this.controlEstacionamiento.findEstacionamiento(id);
        if (estacionamiento == null) {
            return new Estacionamiento(id, null, null, true);
        }
        return estacionamiento;
    }

    public Estacionamiento llenarEstacionamiento(int numEstacionamiento, Vehiculo vehiculo) {
        Date today = new Date(System.currentTimeMillis());
        Estacionamiento estacionamiento;

        List<Estacionamiento> estacionamientosPresentes = controlEstacionamiento.findEstacionamientoByVehiculo(vehiculo.getPlaca());

        for (Estacionamiento estacionamientoOcupado : estacionamientosPresentes) {
            this.vaciarEstacionamiento(estacionamientoOcupado.getNumeroParqueo());
        }
        try {
            if (this.controlEstacionamiento.findEstacionamiento(numEstacionamiento) == null) {
                estacionamiento = new Estacionamiento(numEstacionamiento, vehiculo, today, false);
                this.controlEstacionamiento.create(estacionamiento);
                return estacionamiento;
            } else {
                estacionamiento = new Estacionamiento(numEstacionamiento, vehiculo, today, false);
                this.controlEstacionamiento.edit(estacionamiento);
                return estacionamiento;
            }
        } catch (Exception ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public Estacionamiento vaciarEstacionamiento(int numEstacionamiento) {
        Estacionamiento estacionamiento;
        try {
            if (this.controlEstacionamiento.findEstacionamiento(numEstacionamiento) == null) {
                estacionamiento = new Estacionamiento(numEstacionamiento, null, null, true);
                this.controlEstacionamiento.create(estacionamiento);
                return estacionamiento;
            } else {
                estacionamiento = new Estacionamiento(numEstacionamiento, null, null, true);
                this.controlEstacionamiento.edit(estacionamiento);
                return estacionamiento;
            }
        } catch (Exception ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

}
