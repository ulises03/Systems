/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import java.io.IOException;
import java.security.Principal;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import objclass.Usuario;
import util.MyUtil;

/**
 *
 * @author admin
 */
@ManagedBean(name = "sesion")
public class Sesion {

    private FacesContext contexto;
    private Usuario usuario;

    

    public void isLoggedIn() throws IOException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Usuario userlogged = (Usuario) facesContext.getExternalContext().getSessionMap().get("user");
        String ruta = "";
        if (userlogged == null) {
            ruta = MyUtil.basePathLogin() + "login.xhtml";
            FacesContext.getCurrentInstance().getExternalContext().redirect(ruta);
        }
    }

    public static Usuario getUserSession() {
        Usuario user = null;
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        Map requestParameterMap = externalContext.getSessionMap();

        if (requestParameterMap != null) {
            user = (Usuario) requestParameterMap.get("user");
        }
        return user;
    }

    public static ExternalContext getSession() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        return externalContext;
    }

    public FacesContext getContexto() {
        return contexto;
    }

    public void setContexto(FacesContext contexto) {
        this.contexto = contexto;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

}
