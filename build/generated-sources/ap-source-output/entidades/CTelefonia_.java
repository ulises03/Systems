package entidades;

import entidades.CTipoTelefono;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.7.v20200504-rNA", date="2021-08-31T16:59:27")
@StaticMetamodel(CTelefonia.class)
public class CTelefonia_ { 

    public static volatile SingularAttribute<CTelefonia, String> descripcion;
    public static volatile SingularAttribute<CTelefonia, String> clave;
    public static volatile CollectionAttribute<CTelefonia, CTipoTelefono> cTipoTelefonoCollection;
    public static volatile SingularAttribute<CTelefonia, Long> idTelefonia;
    public static volatile SingularAttribute<CTelefonia, Date> fechaServidor;
    public static volatile SingularAttribute<CTelefonia, Boolean> activo;

}