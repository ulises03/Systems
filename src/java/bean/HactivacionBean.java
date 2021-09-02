/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import controller.SUsuariosJpaController;
import controllerActivacion.HActivacionJpaController;
import entidades.SUsuarios;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import activacionesEntidades.HActivacion;

/**
 *
 * @author admin
 */
@ManagedBean
public class HactivacionBean {

    private static SUsuarios usuario;
    private static List<SUsuarios> listComboBox;
    private static List<Date> range;
    private static List<HActivacion> dataTable, filtroDataTable;

    public HactivacionBean() {
        usuario = new SUsuarios();
    }

    @PostConstruct
    public void init() {
        SUsuariosJpaController modelUsuario = new SUsuariosJpaController();
        listComboBox = modelUsuario.findSUsuariosEntities();
    }

    public void getActivaciones() {
        FacesMessage message = null;
        if (range != null) {
            HActivacionJpaController modelActivacion = new HActivacionJpaController();
            dataTable = modelActivacion.activaciones(range.get(0), range.get(1), usuario);
        } else {
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error verifique las fechas", "");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }
    
    public void limpiarValores(){
        dataTable = null;
        range = null;
    }

    public List<HActivacion> getFiltroDataTable() {
        return filtroDataTable;
    }

    public void setFiltroDataTable(List<HActivacion> filtroDataTable) {
        HactivacionBean.filtroDataTable = filtroDataTable;
    }

    public List<HActivacion> getDataTable() {
        return dataTable;
    }

    public void setDataTable(List<HActivacion> dataTable) {
        HactivacionBean.dataTable = dataTable;
    }

    public SUsuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(SUsuarios usuario) {
        HactivacionBean.usuario = usuario;
    }

    public List<SUsuarios> getListComboBox() {
        return listComboBox;
    }

    public void setListComboBox(List<SUsuarios> listComboBox) {
        HactivacionBean.listComboBox = listComboBox;
    }

    public List<Date> getRange() {
        return range;
    }

    public void setRange(List<Date> range) {
        this.range = range;
    }

}
