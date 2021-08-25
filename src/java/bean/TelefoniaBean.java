/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import controller.CTelefoniaJpaController;
import controller.exceptions.NonexistentEntityException;
import entidades.CTelefonia;
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
@ManagedBean()
public class TelefoniaBean {

    private CTelefonia telefonoObj;
    private static List<CTelefonia> dataTable;
    private static List<CTelefonia> filtroTable;

    public TelefoniaBean() {
        telefonoObj = new CTelefonia();
    }

    @PostConstruct
    public void init() {
        CTelefoniaJpaController model = new CTelefoniaJpaController();
        dataTable = model.findCTelefoniaEntities();
    }

    public void addTelefono() {
        FacesMessage message = null;
        CTelefoniaJpaController model = new CTelefoniaJpaController();
        telefonoObj.setFechaServidor(new Date());
        telefonoObj.setActivo(true);
        try {
            model.create(telefonoObj);
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Telefono Añadido", "");
            FacesContext.getCurrentInstance().addMessage(null, message);
        } catch (Exception e) {
            Logger.getLogger(TelefoniaBean.class.getName()).log(Level.SEVERE, null, e);
        }

    }

    public void deleteTelefono(long id) {
        CTelefoniaJpaController model = new CTelefoniaJpaController();
        FacesMessage message = null;
        try {
            model.destroy(id);
            init();
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Telefono Eliminado", "");
            FacesContext.getCurrentInstance().addMessage(null, message);
        } catch (NonexistentEntityException e) {
            Logger.getLogger(TelefoniaBean.class.getName()).log(Level.SEVERE, null, e);
        }

    }
    
    public void updateTelefono(RowEditEvent event){
        FacesMessage message = null;
        CTelefoniaJpaController model = new CTelefoniaJpaController();
        CTelefonia objEditTelefonia = (CTelefonia) event.getObject();
        try {
            model.edit(objEditTelefonia);
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Telefono Editado", "");
            FacesContext.getCurrentInstance().addMessage(null, message);
        } catch (Exception e) {
        }
    }

    public CTelefonia getTelefonoObj() {
        return telefonoObj;
    }

    public void setTelefonoObj(CTelefonia telefonoObj) {
        this.telefonoObj = telefonoObj;
    }

    public List<CTelefonia> getDataTable() {
        return dataTable;
    }

    public void setDataTable(List<CTelefonia> dataTable) {
        this.dataTable = dataTable;
    }

    public List<CTelefonia> getFiltroTable() {
        return filtroTable;
    }

    public void setFiltroTable(List<CTelefonia> filtroTable) {
        this.filtroTable = filtroTable;
    }
}
