/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.IOException;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import objclass.Usuario;
import sessions.Sesion;
import util.MyUtil;

/**
 *
 * @author admin
 */
@ManagedBean(name="layoutBean")
public class LayoutBean {
    private Usuario usuario;
    
    /*
    Devuelve el nombre del usuario logueado 
    */
    public String getNameUser(){
        Sesion session = new Sesion();
        Usuario usuario = new Usuario();
        usuario = session.getUserSession();
        return usuario.getNombre();
    }
    
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
}
