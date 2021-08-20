/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import model.LoginModel;
import objclass.Usuario;
import org.primefaces.context.PrimeRequestContext;
import org.primefaces.PrimeFaces;
import respuesta.RespuestaLogin;
import util.MyUtil;

/**
 *
 * @author admin
 */
@ManagedBean(name = "loginBean")
@SessionScoped
public class LoginBean {

    private String username, password;

    /*
    Fecha: 20/08/2021 
    Manda a validar las credenciales al modelo
     */
    public void logIn() {
        FacesMessage message = null;
        LoginModel login = new LoginModel();
        RespuestaLogin respuesta = new RespuestaLogin();
        respuesta = login.requestLogin(username, password);
        String ruta = "";
        try {
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
        } catch (IOException e) {
            Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /*
    Fecha: 20/08/2021 
    Elimina la session actualmente inicada 
     */
    public void logOut() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        facesContext.getExternalContext().invalidateSession();
        try {
            String ruta = "";
            ruta = MyUtil.basePathLogin() + "login.xhtml";

            FacesContext.getCurrentInstance().getExternalContext().redirect(ruta);
        } catch (IOException e) {
            Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    public void logInValidate() throws IOException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Usuario userlogged = (Usuario) facesContext.getExternalContext().getSessionMap().get("user");
        String ruta = "";
        
        if (userlogged != null) {
            ruta = MyUtil.basePathLogin() + "index.xhtml";
            FacesContext.getCurrentInstance().getExternalContext().redirect(ruta);
        }
    }

    //<editor-fold defaultstate="collapsed" desc="gets and sets">
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
//</editor-fold>
}
