/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package converter;

import controller.SAccesosJpaController;
import entidades.SAccesos;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.*;

/**
 *
 * @author admin
 */
@FacesConverter("accesoConverter")
public class AccesoConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String idAcceso) {
        SAccesosJpaController jpaController = new SAccesosJpaController();
        return jpaController.findSAccesos(Integer.valueOf(idAcceso));
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object acceso) {
        return ((SAccesos) acceso).getIdAcceso().toString();
    }

}
