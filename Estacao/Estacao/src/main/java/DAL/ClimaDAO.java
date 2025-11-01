package DAL;

import jakarta.persistence.*;
import java.util.List;
import modelo.Clima;

public class ClimaDAO {

    private final EntityManager em;
    public String mensagem = "";

    public ClimaDAO() {
        this.em = ConexaoHibernate.getEntityManager();
    }

    private void mensagem(String texto) {
        mensagem = texto;
        System.out.println(texto);
    }

    public void cadastrarClima(Clima clima) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(clima);
            tx.commit();
            mensagem("Clima cadastrado com sucesso!");
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            mensagem("Erro ao inserir registro de clima: " + e.getMessage());
            throw new PersistenceException("Erro ao inserir registro de clima.", e);
        }
    }

    public void atualizarClima(Clima clima) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(clima);
            tx.commit();
            mensagem("Clima atualizado com sucesso!");
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            mensagem("Erro ao atualizar registro de clima: " + e.getMessage());
            throw new PersistenceException("Erro ao atualizar registro de clima.", e);
        }
    }

    public void removerClima(Integer id) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Clima clima = em.find(Clima.class, id);
            if (clima == null) {
                mensagem("Registro de clima com ID " + id + " não encontrado.");
                return;
            }
            em.remove(clima);
            tx.commit();
            mensagem("Registro de clima removido com sucesso!");
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            mensagem("Erro ao remover registro de clima: " + e.getMessage());
            throw new PersistenceException("Erro ao remover registro de clima.", e);
        }
    }

    public List<Clima> listarClima() {
        try {
            return em.createNamedQuery("Clima.findAll", Clima.class)
                     .getResultList();
        } catch (Exception e) {
            mensagem("Erro ao listar registros de clima: " + e.getMessage());
            throw new PersistenceException("Erro ao listar registros de clima.", e);
        }
    }

    public List<Clima> buscarPorDataClima(java.util.Date data) {
        try {
            return em.createNamedQuery("Clima.findByDatahora", Clima.class)
                     .setParameter("datahora", data)
                     .getResultList();
        } catch (Exception e) {
            mensagem("Erro ao buscar registros de clima por data: " + e.getMessage());
            throw new PersistenceException("Erro ao buscar registros de clima por data.", e);
        }
    }
}
