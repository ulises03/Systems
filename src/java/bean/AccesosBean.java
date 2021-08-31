/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import controller.SAccesosJpaController;
import controller.exceptions.IllegalOrphanException;
import controller.exceptions.NonexistentEntityException;
import entidades.SAccesos;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author admin
 */
@ManagedBean
public class AccesosBean {

    private SAccesos accesoObj;
    private static List<SAccesos> dataTable;
    private static List<SAccesos> filtroTable;

    public AccesosBean() {
        accesoObj = new SAccesos();
        
    }

    @PostConstruct
    public void init() {
        SAccesosJpaController modelo = new SAccesosJpaController();
        dataTable = modelo.findSAccesosEntities();
    }

    public void addAcceso() {
        FacesMessage message = null;
        SAccesosJpaController model = new SAccesosJpaController();
        accesoObj.setFechaServidor(new Date());
        accesoObj.setActivo(true);
        try {
            if (!model.existOrder(accesoObj.getOrden())) {
                model.create(accesoObj);
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Acceso Añadido", "");
                FacesContext.getCurrentInstance().addMessage(null, message);
                accesoObj = null;
            } else {
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error", "El acceso no se puede añadir porque la orden ya existe");
                FacesContext.getCurrentInstance().addMessage(null, message);
            }
        } catch (Exception e) {
            Logger.getLogger(AccesosBean.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void deleteAcceso(Integer id) {
        SAccesosJpaController model = new SAccesosJpaController();
        FacesMessage message = null;
        try {
            model.destroy(id);
            init();
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Acceso Eliminado", "");
            FacesContext.getCurrentInstance().addMessage(null, message);
        } catch (NonexistentEntityException e) {
            Logger.getLogger(TelefoniaBean.class.getName()).log(Level.SEVERE, null, e);
        } catch (IllegalOrphanException ex) {
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "No se puede eliminar porque el acceso esta relacionado a un perfil", "");
            FacesContext.getCurrentInstance().addMessage(null, message);
            Logger.getLogger(AccesosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void updateAcceso(RowEditEvent event){
        FacesMessage message = null;
        SAccesosJpaController model = new SAccesosJpaController();
        SAccesos objEditTelefonia = (SAccesos) event.getObject();
        try {
            model.edit(objEditTelefonia);
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Acceso Editado", "");
            FacesContext.getCurrentInstance().addMessage(null, message);
        } catch (Exception e) {
        }
    }

    public SAccesos getAccesoObj() {
        return accesoObj;
    }

    public void setAccesoObj(SAccesos accesoObj) {
        this.accesoObj = accesoObj;
    }

    public List<SAccesos> getDataTable() {
        return dataTable;
    }

    public void setDataTable(List<SAccesos> dataTable) {
        AccesosBean.dataTable = dataTable;
    }

    public List<SAccesos> getFiltroTable() {
        return filtroTable;
    }

    public void setFiltroTable(List<SAccesos> filtroTable) {
        AccesosBean.filtroTable = filtroTable;
    }

}
