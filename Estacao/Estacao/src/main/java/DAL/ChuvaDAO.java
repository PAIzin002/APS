package DAL;

import jakarta.persistence.*;
import java.util.List;
import modelo.Chuva;

public class ChuvaDAO {

    private final EntityManager em;
    public String mensagem = " ";

    // ✅ Cria EntityManager usando a conexão única
    public ChuvaDAO() {
        this.em = ConexaoHibernate.getEntityManager();
    }

    private void mensagem(String texto) {
        mensagem = texto;
        System.out.println(texto);
    }

    public void cadastrarChuva(Chuva chuva) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(chuva);
            tx.commit();
            mensagem("Registro de chuva cadastrado com sucesso!");
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            mensagem("Erro ao cadastrar registro de chuva: " + e.getMessage());
            throw new PersistenceException("Erro ao cadastrar registro de chuva.", e);
        }
    }

    public void atualizarChuva(Chuva chuva) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(chuva);
            tx.commit();
            mensagem("Registro de chuva atualizado com sucesso!");
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            mensagem("Erro ao atualizar registro de chuva: " + e.getMessage());
            throw new PersistenceException("Erro ao atualizar registro de chuva.", e);
        }
    }

    public void removerChuva(Integer id) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Chuva chuva = em.find(Chuva.class, id);
            if (chuva == null) {
                mensagem("Registro de chuva com ID " + id + " não encontrado.");
                return;
            }
            em.remove(chuva);
            tx.commit();
            mensagem("Registro de chuva removido com sucesso!");
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            mensagem("Erro ao remover registro de chuva: " + e.getMessage());
            throw new PersistenceException("Erro ao remover registro de chuva.", e);
        }
    }

    public List<Chuva> listarChuva() {
        try {
            return em.createNamedQuery("Chuva.findAll", Chuva.class)
                     .getResultList();
        } catch (Exception e) {
            mensagem("Erro ao listar registros de chuva: " + e.getMessage());
            throw new PersistenceException("Erro ao listar registros de chuva.", e);
        }
    }

    public List<Chuva> buscarPorDataChuva(java.util.Date data) {
        try {
            return em.createNamedQuery("Chuva.findByDatahora", Chuva.class)
                     .setParameter("datahora", data)
                     .getResultList();
        } catch (Exception e) {
            mensagem("Erro ao buscar registros de chuva por data: " + e.getMessage());
            throw new PersistenceException("Erro ao buscar registros de chuva por data.", e);
        }
    }
}
