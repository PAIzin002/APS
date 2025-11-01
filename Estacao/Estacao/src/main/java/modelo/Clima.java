package modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "CLIMA")
@NamedQueries({
    @NamedQuery(name = "Clima.findAll", query = "SELECT c FROM Clima c"),
    @NamedQuery(name = "Clima.findByIDTemperatura", query = "SELECT c FROM Clima c WHERE c.iDTemperatura = :iDTemperatura"),
    @NamedQuery(name = "Clima.findByTemperatura", query = "SELECT c FROM Clima c WHERE c.temperatura = :temperatura"),
    @NamedQuery(name = "Clima.findByLocal", query = "SELECT c FROM Clima c WHERE c.local = :local"),
    @NamedQuery(name = "Clima.findByDatahora", query = "SELECT c FROM Clima c WHERE c.datahora = :datahora"),
    @NamedQuery(name = "Clima.findByUmidade", query = "SELECT c FROM Clima c WHERE c.umidade = :umidade")
})
public class Clima implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_Temperatura")
    private Integer iDTemperatura;

    @NotNull(message = "A temperatura não pode ser nula.")
    @DecimalMin(value = "-50.0", message = "Temperatura mínima inválida (deve ser >= -50°C).")
    @DecimalMax(value = "60.0", message = "Temperatura máxima inválida (deve ser <= 60°C).")
    @Column(name = "Temperatura")
    private BigDecimal temperatura;

    @NotBlank(message = "O campo 'Local' é obrigatório.")
    @Size(max = 100, message = "O nome do local não pode ultrapassar 100 caracteres.")
    @Column(name = "Local")
    private String local;

    @NotNull(message = "A data e hora devem ser informadas.")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "Data_hora")
    private Date datahora;

    @NotNull(message = "A umidade deve ser informada.")
    @Min(value = 0, message = "A umidade não pode ser menor que 0%.")
    @Max(value = 100, message = "A umidade não pode ultrapassar 100%.")
    @Column(name = "Umidade")
    private Integer umidade;

    @OneToMany(mappedBy = "iDTemperatura")
    private List<Chuva> chuvaList;

    public Clima() {
    }

    public Clima(Integer iDTemperatura) {
        this.iDTemperatura = iDTemperatura;
    }

    public Integer getIDTemperatura() {
        return iDTemperatura;
    }

    public void setIDTemperatura(Integer iDTemperatura) {
        this.iDTemperatura = iDTemperatura;
    }

    public BigDecimal getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(BigDecimal temperatura) {
        this.temperatura = temperatura;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public Date getDatahora() {
        return datahora;
    }

    public void setDatahora(Date datahora) {
        this.datahora = datahora;
    }

    public Integer getUmidade() {
        return umidade;
    }

    public void setUmidade(Integer umidade) {
        this.umidade = umidade;
    }

    public List<Chuva> getChuvaList() {
        return chuvaList;
    }

    public void setChuvaList(List<Chuva> chuvaList) {
        this.chuvaList = chuvaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDTemperatura != null ? iDTemperatura.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Clima)) {
            return false;
        }
        Clima other = (Clima) object;
        if ((this.iDTemperatura == null && other.iDTemperatura != null)
                || (this.iDTemperatura != null && !this.iDTemperatura.equals(other.iDTemperatura))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Clima[ iDTemperatura=" + iDTemperatura + " ]";
    }
}
