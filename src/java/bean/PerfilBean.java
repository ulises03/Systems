/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import controller.SAccesosJpaController;
import controller.SPerfilesJpaController;
import controllerActivacion.HActivacionJpaController;
import entidades.SAccesos;
import entidades.SPerfiles;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import org.primefaces.model.DualListModel;

/**
 *
 * @author admin
 */
@ManagedBean
public class PerfilBean {

    private static List<SPerfiles> dataTable;
    private static List<SPerfiles> filtroTable;
    private static SPerfiles perfilTable, perfilNew;
    private static DualListModel<SAccesos> accesosDual;
    private static List<SAccesos> sourcesAcces, targetAcces;

    public PerfilBean() {
        perfilTable = new SPerfiles();
        perfilNew = new SPerfiles();
    }

    @PostConstruct
    public void init() {
        SPerfilesJpaController modelPerfil = new SPerfilesJpaController();
        dataTable = modelPerfil.findSPerfilesEntities();
        sourcesAcces = new ArrayList();
        targetAcces = new ArrayList();
        accesosDual = new DualListModel<SAccesos>(sourcesAcces, targetAcces);
    }
    
    public void accesFromNewPerfil(){
        HActivacionJpaController modelactivacion = new HActivacionJpaController();
        Date fecha2 = new Date(119, 10,3);
        modelactivacion.activaciones(fecha2, new Date());
    }

    public List<SAccesos> getSourcesAcces() {
        return sourcesAcces;
    }

    public void setSourcesAcces(List<SAccesos> sourcesAcces) {
        PerfilBean.sourcesAcces = sourcesAcces;
    }

    public List<SAccesos> getTargetAcces() {
        return targetAcces;
    }

    public void setTargetAcces(List<SAccesos> targetAcces) {
        PerfilBean.targetAcces = targetAcces;
    }

    public DualListModel<SAccesos> getAccesosDual() {
        return accesosDual;
    }

    public void setAccesosDual(DualListModel<SAccesos> accesosDual) {
        PerfilBean.accesosDual = accesosDual;
    }

    public List<SPerfiles> getDataTable() {
        return dataTable;
    }

    public void setDataTable(List<SPerfiles> dataTable) {
        PerfilBean.dataTable = dataTable;
    }

    public List<SPerfiles> getFiltroTable() {
        return filtroTable;
    }

    public void setFiltroTable(List<SPerfiles> filtroTable) {
        PerfilBean.filtroTable = filtroTable;
    }

    public SPerfiles getPerfilTable() {
        return perfilTable;
    }

    public void setPerfilTable(SPerfiles perfilTable) {
        PerfilBean.perfilTable = perfilTable;
    }

    public SPerfiles getPerfilNew() {
        return perfilNew;
    }

    public void setPerfilNew(SPerfiles perfilNew) {
        PerfilBean.perfilNew = perfilNew;
    }

}
