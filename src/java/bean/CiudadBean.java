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
import javax.faces.context.FacesContext;
import model.CiudadModel;
import objclass.Ciudad;
import org.primefaces.event.RowEditEvent;
import respuesta.Respuesta;
import respuesta.RespuestaCiudad;
/**
 *
 * @author admin
 */

@ManagedBean(name="ciudad")
public class CiudadBean {
    private static List<Ciudad> lista;
    private static List<Ciudad> filtroCiudad;
    private String editDesc,editCodigo,editLada;
    private String addDesc, addCodigo;
    private int addLada;

    /*
    Carga la lista de ciudades para mostrar  
    */
    @PostConstruct
    public void init() {
        CiudadModel ciudad = new CiudadModel();
        RespuestaCiudad respuesta = ciudad.getListCiudades();
        if(respuesta.getRespuesta().getId() == 0){
            lista = respuesta.getListaCiudad();
        }
    }
    
    /*
    Manda a llamar la funcion de actualizar del model
    */
    public void actualizar(RowEditEvent event) throws IOException{
        Ciudad ciudadEdit = (Ciudad) event.getObject();
        FacesMessage message = null;
        
        if(!editDesc.equals("")){
            ciudadEdit.setDescripcion(editDesc);
        }
        if(!editLada.equals("")){
            ciudadEdit.setLada(Integer.parseInt(editLada));
        }
        if(!editCodigo.equals("")){
            ciudadEdit.setCodigo(editCodigo);
        }
        
        CiudadModel ciudad = new CiudadModel();
        Respuesta respuesta = ciudad.updateCiudad(ciudadEdit);
        
        if(respuesta.getId() == 0){
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Exitoso", respuesta.getMensaje());
            FacesContext.getCurrentInstance().addMessage(null, message);
        }else if(respuesta.getId() > 0){
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Alerta", respuesta.getMensaje());
            FacesContext.getCurrentInstance().addMessage(null, message);
        }else{
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", respuesta.getMensaje());
            FacesContext.getCurrentInstance().addMessage(null, message);
        }

    }
    
    /*
    Manda a llamar la funcion de eliminar del modelo 
    */
    public void deleteCiudad(int id) throws IOException{
        CiudadModel ciudad = new CiudadModel();
        Respuesta respuesta = ciudad.deleteCiudad(id);
        FacesMessage message = null;
        String ruta = "";
        
        if(respuesta.getId() == 0){
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Exitoso", respuesta.getMensaje());
            FacesContext.getCurrentInstance().addMessage(null, message);
            init();
        }else if(respuesta.getId() > 0){
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Alerta", respuesta.getMensaje());
            FacesContext.getCurrentInstance().addMessage(null, message);
        }else{
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", respuesta.getMensaje());
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
        
    }
    
    /*
    Manda a llamar la funcion de añadir del modelo 
    */
    public void addCidad(){
        Ciudad newciudad = new Ciudad();
        newciudad.setActivo(true);
        newciudad.setDescripcion(addDesc);
        
        
        newciudad.setLada(addLada);
        newciudad.setCodigo(addCodigo);
        
        CiudadModel ciudad = new CiudadModel();
        Respuesta respuesta = ciudad.addCiudad(newciudad);
        FacesMessage message = null;
        
        if(respuesta.getId() == 0){
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Exitoso", respuesta.getMensaje());
            FacesContext.getCurrentInstance().addMessage(null, message); 
            init();
            this.addDesc = "";
            this.addCodigo = "";
            this.addLada = 0;
        }else if(respuesta.getId() > 0){
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Alerta", respuesta.getMensaje());
            FacesContext.getCurrentInstance().addMessage(null, message);
        }else{
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", respuesta.getMensaje());
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
        
    }
    
  
    /*
    gets and set
    */
    
    public  List<Ciudad> getLista() {
        return lista;
    }

    public  void setLista(List<Ciudad> lista) {
        CiudadBean.lista = lista;
    }

    public  List<Ciudad> getFiltroUsuario() {
        return filtroCiudad;
    }

    public  void setFiltroUsuario(List<Ciudad> filtroCiudad) {
        CiudadBean.filtroCiudad = filtroCiudad;
    }
    
    public  List<Ciudad> getFiltroCiudad() {
        return filtroCiudad;
    }

    public  void setFiltroCiudad(List<Ciudad> filtroCiudad) {
        CiudadBean.filtroCiudad = filtroCiudad;
    }

    public String getEditDesc() {
        return editDesc;
    }

    public void setEditDesc(String editDesc) {
        this.editDesc = editDesc;
    }

    public String getEditCodigo() {
        return editCodigo;
    }

    public void setEditCodigo(String editCodigo) {
        this.editCodigo = editCodigo;
    }

    public String getEditLada() {
        return editLada;
    }

    public void setEditLada(String editLada) {
        this.editLada = editLada;
    }    
    
    public String getAddDesc() {
        return addDesc;
    }

    public void setAddDesc(String addDesc) {
        this.addDesc = addDesc;
    }

    public int getAddLada() {
        return addLada;
    }

    public void setAddLada(int addLada) {
        this.addLada = addLada;
    }

    public String getAddCodigo() {
        return addCodigo;
    }

    public void setAddCodigo(String addCodigo) {
        this.addCodigo = addCodigo;
    }
}
