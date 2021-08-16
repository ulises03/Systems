/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package respuesta;

import respuesta.Respuesta;
import objclass.Usuario;

/**
 *
 * @author admin
 */
public class RespuestaLogin {
    private Respuesta respuesta;
    private Usuario usuario;

    public Respuesta getRespuesta() {
        return respuesta;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setRespuesta(Respuesta respuesta) {
        this.respuesta = respuesta;
    }
    
    public boolean isEmptyUser(){
        if(this.usuario.getId() != null){
            return true;
        }else{
            return false;
        }
    }
}
