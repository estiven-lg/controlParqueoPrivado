package modelo;

import java.util.Date;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.Vehiculo;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2023-11-03T18:59:44", comments="EclipseLink-2.7.10.v20211216-rNA")
@StaticMetamodel(Estacionamiento.class)
public class Estacionamiento_ { 

    public static volatile SingularAttribute<Estacionamiento, Integer> numeroParqueo;
    public static volatile SingularAttribute<Estacionamiento, Date> fechaRegistro;
    public static volatile SingularAttribute<Estacionamiento, Vehiculo> vehiculo;
    public static volatile SingularAttribute<Estacionamiento, Boolean> disponible;

}