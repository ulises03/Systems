package entidades;

import entidades.SAccesos;
import entidades.SPerfiles;
import entidades.SPerfilesAccesosPK;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.7.v20200504-rNA", date="2021-08-31T16:59:27")
@StaticMetamodel(SPerfilesAccesos.class)
public class SPerfilesAccesos_ { 

    public static volatile SingularAttribute<SPerfilesAccesos, SPerfilesAccesosPK> sPerfilesAccesosPK;
    public static volatile SingularAttribute<SPerfilesAccesos, SPerfiles> sPerfiles;
    public static volatile SingularAttribute<SPerfilesAccesos, Integer> idUsuarioModifica;
    public static volatile SingularAttribute<SPerfilesAccesos, Date> fechaServidor;
    public static volatile SingularAttribute<SPerfilesAccesos, SAccesos> sAccesos;

}