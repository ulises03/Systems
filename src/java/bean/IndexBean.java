/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import objclass.Usuario;
import sessions.Sesion;

/**
 *
 * @author admin
 */
@ManagedBean(name = "indexBean")
public class IndexBean {

    private List<String> images;
    private String nombre;

    /*
    Llena la lista para mostrar en la galeria 
     */
    @PostConstruct
    public void init() {
        FacesMessage message = null;
        Usuario user = Sesion.getUserSession();
        
        images = new ArrayList<String>();

        for (int i = 1; i <= 3; i++) {
            images.add("imgIndex" + i + ".jpg");
        }
        nombre = user.getNombre();
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
