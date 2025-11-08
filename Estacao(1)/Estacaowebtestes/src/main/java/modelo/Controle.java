/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.util.List;

/**
 * Classe de controle responsável por orquestrar as operações de leitura,
 * atualização e listagem de dados ambientais.
 *
 * Segue o princípio da responsabilidade única: delega a persistência ao DAO
 * e não faz validação manual (já é tratada pelas anotações da entidade).
 *
 * @author dayan
 */
public class Controle {

    private final LeiturasDAO leiturasDAO = new LeiturasDAO();

    /**
     * Salva uma nova leitura no banco de dados.
     *
     * @param leitura objeto Leituras preenchido com dados válidos.
     */
    public void salvarLeitura(Leituras leitura) {
        leiturasDAO.salvar(leitura);
    }

    /**
     * Lista todas as leituras armazenadas.
     *
     * @return lista de objetos Leituras
     */
    public List<Leituras> listarLeituras() {
        return leiturasDAO.listarTodas();
    }

    /**
     * Retorna a última leitura registrada no banco.
     *
     * @return última leitura registrada ou null se não houver registros
     */
    public Leituras buscarUltimaLeitura() {
        return leiturasDAO.buscarUltima();
    }
}
