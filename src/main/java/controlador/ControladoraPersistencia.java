package controlador;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Persona;

public class ControladoraPersistencia {

    EstacionamientoJpaController controlEstacionamiento = new EstacionamientoJpaController();
    PersonaJpaController controlPersona = new PersonaJpaController();
    VehiculoJpaController controlVehiculo = new VehiculoJpaController();

    public void CrearPersona(Persona persona) {
        try {
            this.controlPersona.create(persona);
        } catch (Exception ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
       public void ActualizarPersona(Persona persona) {
        try {
            this.controlPersona.edit(persona);
        } catch (Exception ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public List<Persona> obtenerPersonas() {
        return controlPersona.findPersonaEntities();
    }

    
    
    public Persona encontrarPersona(int cui){
    return controlPersona.findPersona(cui);
    }
}
