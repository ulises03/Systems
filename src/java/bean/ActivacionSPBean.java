/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import activacionesEntidades.HActivacion;
import controller.SUsuariosJpaController;
import controllerActivacion.HActivacionJpaController;
import entidades.SUsuarios;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author admin
 */
@ManagedBean
public class ActivacionSPBean {

    private SUsuarios usuario;
    private static List<HActivacion> dataTable;
    private static List<HActivacion> filterTable;
    private static List<SUsuarios> listComboBox;

    public ActivacionSPBean() {
        usuario = new SUsuarios();
    }

    @PostConstruct
    public void init() {
        SUsuariosJpaController modelUsuarios = new SUsuariosJpaController();
        listComboBox = modelUsuarios.findSUsuariosEntities();
    }

    public void exProcedure() {
        if(usuario.getIdUsuario() != null && usuario.getIdUsuario() > 0){
            HActivacionJpaController modelActivacion = new HActivacionJpaController();
            dataTable = modelActivacion.activacionesProcedure(usuario);
        }
    }

    public List<SUsuarios> getListComboBox() {
        return listComboBox;
    }

    public void setListComboBox(List<SUsuarios> listComboBox) {
        ActivacionSPBean.listComboBox = listComboBox;
    }

    public SUsuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(SUsuarios usuario) {
        this.usuario = usuario;
    }

    public List<HActivacion> getDataTable() {
        return dataTable;
    }

    public void setDataTable(List<HActivacion> dataTable) {
        ActivacionSPBean.dataTable = dataTable;
    }

    public List<HActivacion> getFilterTable() {
        return filterTable;
    }

    public void setFilterTable(List<HActivacion> filterTable) {
        ActivacionSPBean.filterTable = filterTable;
    }

}
