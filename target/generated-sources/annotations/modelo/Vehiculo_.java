package modelo;

import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.Persona;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2023-11-04T17:25:20", comments="EclipseLink-2.7.10.v20211216-rNA")
@StaticMetamodel(Vehiculo.class)
public class Vehiculo_ { 

    public static volatile SingularAttribute<Vehiculo, String> marca;
    public static volatile SingularAttribute<Vehiculo, String> color;
    public static volatile SingularAttribute<Vehiculo, Persona> propietario;
    public static volatile SingularAttribute<Vehiculo, String> modelo;
    public static volatile SingularAttribute<Vehiculo, String> linea;
    public static volatile SingularAttribute<Vehiculo, String> placa;

}