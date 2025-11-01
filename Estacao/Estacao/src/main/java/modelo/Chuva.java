package modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "CHUVA")
@NamedQueries({
    @NamedQuery(name = "Chuva.findAll", query = "SELECT c FROM Chuva c"),
    @NamedQuery(name = "Chuva.findByIDChuva", query = "SELECT c FROM Chuva c WHERE c.iDChuva = :iDChuva"),
    @NamedQuery(name = "Chuva.findByDatahora", query = "SELECT c FROM Chuva c WHERE c.datahora = :datahora"),
    @NamedQuery(name = "Chuva.findByBooleanochuva", query = "SELECT c FROM Chuva c WHERE c.booleanochuva = :booleanochuva")
})
public class Chuva implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_Chuva")
    private Integer iDChuva;

    @NotNull(message = "A data e hora devem ser informadas.")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "Data_hora")
    private Date datahora;

    @NotNull(message = "O campo booleano de chuva deve ser informado (true ou false).")
    @Column(name = "Booleano_chuva")
    private Boolean booleanochuva;

    @JoinColumn(name = "ID_Temperatura", referencedColumnName = "ID_Temperatura")
    @ManyToOne
    @NotNull(message = "Cada registro de chuva deve estar associado a um registro de clima.")
    private Clima iDTemperatura;

    public Chuva() {
    }

    public Chuva(Integer iDChuva) {
        this.iDChuva = iDChuva;
    }

    public Integer getIDChuva() {
        return iDChuva;
    }

    public void setIDChuva(Integer iDChuva) {
        this.iDChuva = iDChuva;
    }

    public Date getDatahora() {
        return datahora;
    }

    public void setDatahora(Date datahora) {
        this.datahora = datahora;
    }

    public Boolean getBooleanochuva() {
        return booleanochuva;
    }

    public void setBooleanochuva(Boolean booleanochuva) {
        this.booleanochuva = booleanochuva;
    }

    public Clima getIDTemperatura() {
        return iDTemperatura;
    }

    public void setIDTemperatura(Clima iDTemperatura) {
        this.iDTemperatura = iDTemperatura;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDChuva != null ? iDChuva.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Chuva)) {
            return false;
        }
        Chuva other = (Chuva) object;
        if ((this.iDChuva == null && other.iDChuva != null)
                || (this.iDChuva != null && !this.iDChuva.equals(other.iDChuva))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Chuva[ iDChuva=" + iDChuva + " ]";
    }
}
