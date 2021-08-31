package entidades;

import activacionesEntidades.HActivacion;
import entidades.CTelefonia;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.7.v20200504-rNA", date="2021-08-31T16:59:27")
@StaticMetamodel(CTipoTelefono.class)
public class CTipoTelefono_ { 

    public static volatile SingularAttribute<CTipoTelefono, String> descripcion;
    public static volatile SingularAttribute<CTipoTelefono, String> clave;
    public static volatile CollectionAttribute<CTipoTelefono, HActivacion> hActivacionCollection;
    public static volatile SingularAttribute<CTipoTelefono, Long> id;
    public static volatile SingularAttribute<CTipoTelefono, CTelefonia> idTelefonia;
    public static volatile SingularAttribute<CTipoTelefono, Date> fechaServidor;
    public static volatile SingularAttribute<CTipoTelefono, Boolean> activo;

}