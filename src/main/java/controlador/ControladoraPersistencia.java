package controlador;

import controlador.exceptions.NonexistentEntityException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Estacionamiento;
import modelo.Persona;
import modelo.Vehiculo;

/**
 *
 * @author lafer
 */
public class ControladoraPersistencia {

    EstacionamientoJpaController controlEstacionamiento = new EstacionamientoJpaController();
    PersonaJpaController controlPersona = new PersonaJpaController();
    VehiculoJpaController controlVehiculo = new VehiculoJpaController();

    // metodos de persona
    
    
    /**
     * Este metodo crea una persona en la tabla personas
     * @param persona - persona a registrar
     */
    public void crearPersona(Persona persona) {
        try {
            this.controlPersona.create(persona);
        } catch (Exception ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * 
     * @param persona 
     */
    public void actualizarPersona(Persona persona) {
        try {
            this.controlPersona.edit(persona);
        } catch (Exception ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @return
     */
    public List<Persona> obtenerPersonas() {
        return this.controlPersona.findPersonaEntities();
    }

    /**
     *
     * @param cui
     * @return
     */
    public Persona encontrarPersona(long cui) {
        return this.controlPersona.findPersona(cui);
    }

    /**
     *
     * @param cui
     */
    public void eliminarPersona(long cui) {
       
            List<Vehiculo> vehiculosDelPropietario = this.controlVehiculo.findVehiculosByPersona(cui);
            for (Vehiculo vehiculo : vehiculosDelPropietario) {
                System.out.println(vehiculo);
                this.eliminarVehiculo(vehiculo.getPlaca());
            }
            
        try {
            this.controlPersona.destroy(cui);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
     
    }

    /**
     *
     * @param cui
     * @return
     */
    public boolean yaExistePersona(long cui) {
        return (this.controlPersona.findPersona(cui) != null);
    }

    // Metodos de Vehiculo

    /**
     *
     * @param vehiculo
     */
    public void crearVehiculo(Vehiculo vehiculo) {
        try {
            this.controlVehiculo.create(vehiculo);
        } catch (Exception ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param Vehiculo
     */
    public void actualizarVehiculo(Vehiculo Vehiculo) {
        try {
            this.controlVehiculo.edit(Vehiculo);
        } catch (Exception ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     *
     * @return
     */
    public List<Vehiculo> obtenerVehiculos() {
        return controlVehiculo.findVehiculoEntities(0, 0);
    }

    /**
     *
     * @param placa
     * @return
     */
    public Vehiculo encontrarVehiculo(String placa) {
        return this.controlVehiculo.findVehiculo(placa);
    }

    /**
     *
     * @param placa
     */
    public void eliminarVehiculo(String placa) {
        try {
            List<Estacionamiento> estacionamientosPresentes = controlEstacionamiento.findEstacionamientoByVehiculo(placa);
            for (Estacionamiento estacionamientoOcupado : estacionamientosPresentes) {
                this.vaciarEstacionamiento(estacionamientoOcupado.getNumeroParqueo());
            }
            this.controlVehiculo.destroy(placa);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param placa
     * @return
     */
    public boolean yaExisteVehiculo(String placa) {
        return (this.controlVehiculo.findVehiculo(placa) != null);
    }

    //metodos de estacionamietos

    /**
     *
     * @param id
     * @return
     */
    public Estacionamiento encontrarEstacionamieto(int id) {
        Estacionamiento estacionamiento = this.controlEstacionamiento.findEstacionamiento(id);
        if (estacionamiento == null) {
            return new Estacionamiento(id, null, null, true);
        }
        return estacionamiento;
    }

    /**
     *
     * @param numEstacionamiento
     * @param vehiculo
     * @return
     */
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

    /**
     *
     * @param numEstacionamiento
     * @return
     */
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
