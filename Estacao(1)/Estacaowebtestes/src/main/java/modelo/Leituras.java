/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author dayan
 */
@Entity
@Table(name = "Leituras")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Leituras.findAll", query = "SELECT l FROM Leituras l"),
    @NamedQuery(name = "Leituras.findById", query = "SELECT l FROM Leituras l WHERE l.id = :id"),
    @NamedQuery(name = "Leituras.findByDataHora", query = "SELECT l FROM Leituras l WHERE l.dataHora = :dataHora"),
    @NamedQuery(name = "Leituras.findByTemperatura", query = "SELECT l FROM Leituras l WHERE l.temperatura = :temperatura"),
    @NamedQuery(name = "Leituras.findByUmidade", query = "SELECT l FROM Leituras l WHERE l.umidade = :umidade"),
    @NamedQuery(name = "Leituras.findByChuva", query = "SELECT l FROM Leituras l WHERE l.chuva = :chuva")})
public class Leituras implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "dataHora")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataHora;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "temperatura")
    private BigDecimal temperatura;
    @Basic(optional = false)
    @NotNull
    @Column(name = "umidade")
    private BigDecimal umidade;
    @Basic(optional = false)
    @NotNull
    @Column(name = "chuva")
    private boolean chuva;

    public Leituras() {
    }

    public Leituras(Integer id) {
        this.id = id;
    }

    public Leituras(Integer id, Date dataHora, BigDecimal temperatura, BigDecimal umidade, boolean chuva) {
        this.id = id;
        this.dataHora = dataHora;
        this.temperatura = temperatura;
        this.umidade = umidade;
        this.chuva = chuva;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDataHora() {
        return dataHora;
    }

    public void setDataHora(Date dataHora) {
        this.dataHora = dataHora;
    }

    public BigDecimal getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(BigDecimal temperatura) {
        this.temperatura = temperatura;
    }

    public BigDecimal getUmidade() {
        return umidade;
    }

    public void setUmidade(BigDecimal umidade) {
        this.umidade = umidade;
    }

    public boolean getChuva() {
        return chuva;
    }

    public void setChuva(boolean chuva) {
        this.chuva = chuva;
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
        if (!(object instanceof Leituras)) {
            return false;
        }
        Leituras other = (Leituras) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Leituras[ id=" + id + " ]";
    }
}
