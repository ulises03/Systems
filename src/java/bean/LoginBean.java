/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.IOException;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import model.LoginModel;
import org.primefaces.context.PrimeRequestContext;
import org.primefaces.PrimeFaces;
import respuesta.RespuestaLogin;
import util.MyUtil;

/**
 *
 * @author admin
 */
@ManagedBean(name = "login")
@SessionScoped
public class LoginBean {
    private String username;
    private String password;

    /*
    Manda a validar las credenciales al modelo
    */
    public void login() throws IOException {
        PrimeRequestContext context = PrimeRequestContext.getCurrentInstance();
        FacesMessage message = null;
        LoginModel login = new LoginModel();
        RespuestaLogin respuesta = new RespuestaLogin();
        respuesta = login.requestLogin(username, password);
        String ruta = "";

        if (respuesta.getRespuesta().getId() == 0) {
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("user", respuesta.getUsuario());
            ruta = MyUtil.basePathLogin() + "index.xhtml";
            FacesContext.getCurrentInstance().getExternalContext().redirect(ruta);
        } else if (respuesta.getRespuesta().getId() > 0) {
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Alerta", respuesta.getRespuesta().getMensaje());
            FacesContext.getCurrentInstance().addMessage(null, message);
        } else {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", respuesta.getRespuesta().getMensaje());
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

    /*
    Elimina la session actualmente inicada 
    */
    public void logOut() throws IOException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        facesContext.getExternalContext().invalidateSession();
        String ruta = "";
        ruta = MyUtil.basePathLogin() + "login.xhtml";
        FacesContext.getCurrentInstance().getExternalContext().redirect(ruta);
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
