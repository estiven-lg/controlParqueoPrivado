package controlador;

import controlador.exceptions.NonexistentEntityException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    public Persona encontrarPersona(int cui) {
        return this.controlPersona.findPersona(cui);
    }

    public void eliminarPersona(int id) {
        try {
            this.controlPersona.destroy(id);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
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
}
