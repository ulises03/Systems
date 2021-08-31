/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import controller.CTelefoniaJpaController;
import controller.CTipoTelefonoJpaController;
import controller.exceptions.NonexistentEntityException;
import entidades.CTelefonia;
import entidades.CTipoTelefono;
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
public class TipoTelefoniaBean {

    private CTipoTelefono tipoTelefonoObj;
    private static List<CTipoTelefono> dataTable;
    private static List<CTipoTelefono> filtroTable;
    private static List<CTelefonia> comboBox;
    private long id_telefonia;
    private CTelefonia objTelefonia;

    public TipoTelefoniaBean() {
        tipoTelefonoObj = new CTipoTelefono();
        objTelefonia = new CTelefonia();
    }

    @PostConstruct
    public void init() {
        CTipoTelefonoJpaController model = new CTipoTelefonoJpaController();
        CTelefoniaJpaController modelTelefonia = new CTelefoniaJpaController();

        dataTable = model.findCTipoTelefonoEntities();
        comboBox = modelTelefonia.findCTelefoniaEntities();
    }

    public void addTipoTelefonia() {
        FacesMessage message = null;
        CTipoTelefonoJpaController model = new CTipoTelefonoJpaController();
        CTelefoniaJpaController modelTelefonia = new CTelefoniaJpaController();
        CTelefonia objTelefonia = modelTelefonia.findCTelefonia(id_telefonia);

        tipoTelefonoObj.setFechaServidor(new Date());
        tipoTelefonoObj.setActivo(true);
        tipoTelefonoObj.setIdTelefonia(objTelefonia);
        try {
            model.create(tipoTelefonoObj);
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Telefono Añadido", "");
            FacesContext.getCurrentInstance().addMessage(null, message);
            tipoTelefonoObj = null;
            id_telefonia = 0;
            init();
        } catch (Exception e) {
            Logger.getLogger(TelefoniaBean.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void deleteTipoTelefonia(Long id) {
        CTipoTelefonoJpaController model = new CTipoTelefonoJpaController();
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

    public void updateTelefono(RowEditEvent event) {
        FacesMessage message = null;
        CTipoTelefonoJpaController model = new CTipoTelefonoJpaController();
        CTipoTelefono objEditTelefonia = (CTipoTelefono) event.getObject();

        if (id_telefonia != 0) {
            CTelefoniaJpaController modelTelefonia = new CTelefoniaJpaController();
            CTelefonia objTelefonia = modelTelefonia.findCTelefonia(id_telefonia);
            objEditTelefonia.setIdTelefonia(objTelefonia);
        }
        try {
            model.edit(objEditTelefonia);
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Telefono Editado", "");
            FacesContext.getCurrentInstance().addMessage(null, message);
        } catch (Exception e) {
        }
    }

    public long getId_telefonia() {
        return id_telefonia;
    }

    public void setId_telefonia(long id_telefonia) {
        this.id_telefonia = id_telefonia;
    }

    public List<CTelefonia> getComboBox() {
        return comboBox;
    }

    public void setComboBox(List<CTelefonia> comboBox) {
        TipoTelefoniaBean.comboBox = comboBox;
    }

    public CTipoTelefono getTipoTelefonoObj() {
        return tipoTelefonoObj;
    }

    public void setTipoTelefonoObj(CTipoTelefono tipoTelefonoObj) {
        this.tipoTelefonoObj = tipoTelefonoObj;
    }

    public List<CTipoTelefono> getDataTable() {
        return dataTable;
    }

    public void setDataTable(List<CTipoTelefono> dataTable) {
        TipoTelefoniaBean.dataTable = dataTable;
    }

    public List<CTipoTelefono> getFiltroTable() {
        return filtroTable;
    }

    public void setFiltroTable(List<CTipoTelefono> filtroTable) {
        TipoTelefoniaBean.filtroTable = filtroTable;
    }

    public CTelefonia getObjTelefonia() {
        return objTelefonia;
    }

    public void setObjTelefonia(CTelefonia objTelefonia) {
        this.objTelefonia = objTelefonia;
    }
}
