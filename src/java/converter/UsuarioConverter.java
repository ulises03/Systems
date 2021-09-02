/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package converter;

import controller.SUsuariosJpaController;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import entidades.SUsuarios;
/**
 *
 * @author admin
 */
@FacesConverter("usuarioConverter")
public class UsuarioConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String idUsuario) {
        SUsuariosJpaController jpaController = new SUsuariosJpaController();
        return jpaController.findSUsuarios(Integer.valueOf(idUsuario));
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object usuario) {
        return ((SUsuarios) usuario).getIdUsuario().toString();
    }
    
}
