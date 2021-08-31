/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import activacionesEntidades.HActivacion;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author admin
 */
@Entity
@Table(name = "C_TIPO_TELEFONO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CTipoTelefono.findAll", query = "SELECT c FROM CTipoTelefono c"),
    @NamedQuery(name = "CTipoTelefono.findById", query = "SELECT c FROM CTipoTelefono c WHERE c.id = :id"),
    @NamedQuery(name = "CTipoTelefono.findByDescripcion", query = "SELECT c FROM CTipoTelefono c WHERE c.descripcion = :descripcion"),
    @NamedQuery(name = "CTipoTelefono.findByClave", query = "SELECT c FROM CTipoTelefono c WHERE c.clave = :clave"),
    @NamedQuery(name = "CTipoTelefono.findByActivo", query = "SELECT c FROM CTipoTelefono c WHERE c.activo = :activo"),
    @NamedQuery(name = "CTipoTelefono.findByFechaServidor", query = "SELECT c FROM CTipoTelefono c WHERE c.fechaServidor = :fechaServidor")})
public class CTipoTelefono implements Serializable {

    @OneToMany(mappedBy = "idTipoTelefonia")
    private Collection<HActivacion> hActivacionCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Column(name = "CLAVE")
    private String clave;
    @Column(name = "ACTIVO")
    private Boolean activo;
    @Column(name = "FECHA_SERVIDOR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaServidor;
    @JoinColumn(name = "ID_TELEFONIA", referencedColumnName = "ID_TELEFONIA")
    @ManyToOne
    private CTelefonia idTelefonia;
    
    
    public CTipoTelefono() {
    }

    public CTipoTelefono(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Date getFechaServidor() {
        return fechaServidor;
    }

    public void setFechaServidor(Date fechaServidor) {
        this.fechaServidor = fechaServidor;
    }

    public CTelefonia getIdTelefonia() {
        return idTelefonia;
    }

    public void setIdTelefonia(CTelefonia idTelefonia) {
        this.idTelefonia = idTelefonia;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CTipoTelefono)) {
            return false;
        }
        CTipoTelefono other = (CTipoTelefono) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.CTipoTelefono[ id=" + id + " ]";
    }

    @XmlTransient
    public Collection<HActivacion> getHActivacionCollection() {
        return hActivacionCollection;
    }

    public void setHActivacionCollection(Collection<HActivacion> hActivacionCollection) {
        this.hActivacionCollection = hActivacionCollection;
    }
    
}
