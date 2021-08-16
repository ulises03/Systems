/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.IOException;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import model.UsuarioModel;
import objclass.Usuario;
import org.primefaces.event.RowEditEvent;
import respuesta.Respuesta;
import respuesta.RespuestaUsuario;
import util.MyUtil;

/**
 *
 * @author admin
 */
@ManagedBean(name = "usuario")
@RequestScoped
public class UsuarioBean {

    private static List<Usuario> lista;
    private static List<Usuario> filtroUsuario;
    private String addUser, addPassword, addEmail, addName;
    private String editUser, editName, editEmail;

    /*
    Llena la lista pra mostrar en la tabla 
    */
    @PostConstruct
    public void init() {
        UsuarioModel usuario = new UsuarioModel();
        RespuestaUsuario respuesta = usuario.getListUsuarios();
        if (respuesta.getRespuesta().getId() == 0) {
            lista = respuesta.getListaUsuario();
        }
    }
    
    /*
    Manda a llamar la funcion de eliminar del modelo 
    */
    public void deleteUsuario(int id) {
        UsuarioModel usuario = new UsuarioModel();
        Respuesta respuesta = usuario.deleteUsuario(id);
        FacesMessage message = null;

        if (respuesta.getId() == 0) {
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Exitoso", respuesta.getMensaje());
            FacesContext.getCurrentInstance().addMessage(null, message);
            init();
        } else if (respuesta.getId() > 0) {
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Alerta", respuesta.getMensaje());
            FacesContext.getCurrentInstance().addMessage(null, message);
        } else {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", respuesta.getMensaje());
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }
    
    /*
    Manda a llamar la funcion de añadir del modelo 
    */
    public void addUsuario() {
        FacesMessage message = null;
        Usuario usuarioobj = new Usuario();

        usuarioobj.setActivo(true);
        usuarioobj.setUsuario(addUser);
        usuarioobj.setNombre(addName);
        usuarioobj.setCorreo(addEmail);
        usuarioobj.setPassword(addPassword);

        UsuarioModel usuario = new UsuarioModel();
        Respuesta respuesta = usuario.addUsuario(usuarioobj);

        if (respuesta.getId() == 0) {
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Exitoso", respuesta.getMensaje());
            FacesContext.getCurrentInstance().addMessage(null, message);
            this.addEmail = "";
            this.addName = "";
            this.addUser = "";
            this.addPassword = "";
            init();
        } else if (respuesta.getId() > 0) {
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Alerta", respuesta.getMensaje());
            FacesContext.getCurrentInstance().addMessage(null, message);
        } else {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", respuesta.getMensaje());
            FacesContext.getCurrentInstance().addMessage(null, message);
        }

    }
    
    /*
    Manda a llamar la funcion de actualizar del model
    */
    public void actualizar(RowEditEvent event) throws IOException {
        Usuario userEdit = (Usuario) event.getObject();
        FacesMessage message = null;

        if (!editEmail.equals("")) {
            userEdit.setCorreo(editEmail);
        }
        if (!editName.equals("")) {
            userEdit.setNombre(editName);
        }
        if (!editUser.equals("")) {
            userEdit.setUsuario(editUser);
        }

        UsuarioModel usuario = new UsuarioModel();
        Respuesta respuesta = usuario.updateUsuario(userEdit);

        if (respuesta.getId() == 0) {

            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Alerta", respuesta.getMensaje());
            FacesContext.getCurrentInstance().addMessage(null, message);

        } else if (respuesta.getId() > 0) {

            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Alerta", respuesta.getMensaje());
            FacesContext.getCurrentInstance().addMessage(null, message);

        } else {

            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", respuesta.getMensaje());
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

    public String getAddUser() {
        return addUser;
    }

    public void setAddUser(String addUser) {
        this.addUser = addUser;
    }

    public String getAddPassword() {
        return addPassword;
    }

    public void setAddPassword(String addPassword) {
        this.addPassword = addPassword;
    }

    public String getAddEmail() {
        return addEmail;
    }

    public void setAddEmail(String addEmail) {
        this.addEmail = addEmail;
    }

    public String getAddName() {
        return addName;
    }

    public void setAddName(String addName) {
        this.addName = addName;
    }

    public String getEditName() {
        return editName;
    }

    public void setEditName(String editName) {
        this.editName = editName;
    }

    public String getEditEmail() {
        return editEmail;
    }

    public void setEditEmail(String editEmail) {
        this.editEmail = editEmail;
    }

    public List<Usuario> getLista() {
        return lista;
    }

    public void setLista(List<Usuario> lista) {
        UsuarioBean.lista = lista;
    }

    public List<Usuario> getFiltroUsuario() {
        return filtroUsuario;
    }

    public void setFiltroUsuario(List<Usuario> filtroUsuario) {
        UsuarioBean.filtroUsuario = filtroUsuario;
    }

    public String getEditUser() {
        return editUser;
    }

    public void setEditUser(String editUser) {
        this.editUser = editUser;
    }
}
