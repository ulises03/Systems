/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import controller.SAccesosJpaController;
import controller.SPerfilesJpaController;
import entidades.SAccesos;
import entidades.SPerfiles;
import entidades.SPerfilesAccesos;
import entidades.SPerfilesAccesosPK;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.model.DualListModel;
import sessions.Sesion;

/**
 *
 * @author admin
 */
@ManagedBean
@ViewScoped
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

    public void accesFromNewPerfil() {
        SAccesosJpaController modelAccesos = new SAccesosJpaController();
        sourcesAcces = modelAccesos.findSAccesosEntities();
        targetAcces = new ArrayList();
        accesosDual = new DualListModel<SAccesos>(sourcesAcces, targetAcces);
    }

    public void createPerfilAcces() {
        FacesMessage message = null;
        SPerfilesJpaController modelPerfil = new SPerfilesJpaController();
        Sesion sesion = new Sesion();
        List<SPerfilesAccesos> listaPerfilAccesos = new ArrayList();
        try {
            if (!accesosDual.getTarget().isEmpty()) {
                perfilNew.setActivo(true);
                perfilNew.setFechaServidor(new Date());
                perfilNew.setFechaAlta(new Date());
                perfilNew.setIdUsuarioModifica(sesion.getUserSessionID());
                
                for (SAccesos obj : accesosDual.getTarget()) {
                    SPerfilesAccesos entidad = new SPerfilesAccesos();

                    entidad.setSAccesos(obj);
                    
                    entidad.setFechaServidor(new Date());
                    entidad.setIdUsuarioModifica(sesion.getUserSessionID());
                    listaPerfilAccesos.add(entidad);
                }
                
                perfilNew.setSPerfilesAccesosCollection(listaPerfilAccesos);
                
                modelPerfil.create(perfilNew);
                perfilNew = null;
                init();
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Se añadio el perfil", "");
                FacesContext.getCurrentInstance().addMessage(null, message);
            } else {
                perfilNew.setActivo(true);
                perfilNew.setFechaServidor(new Date());
                perfilNew.setFechaAlta(new Date());
                perfilNew.setIdUsuarioModifica(sesion.getUserSessionID());
                modelPerfil.create(perfilNew);
                perfilNew = null;
                init();
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Se añadio el perfil", "");
                FacesContext.getCurrentInstance().addMessage(null, message);
            }
        } catch (Exception e) {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ocurrio un error", "");
            FacesContext.getCurrentInstance().addMessage(null, message);
            Logger.getLogger(PerfilBean.class.getName()).log(Level.SEVERE, null, e);
        }
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
