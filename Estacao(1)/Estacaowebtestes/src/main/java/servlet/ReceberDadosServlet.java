package servlet;

import modelo.Leituras;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modelo.Controle;

/**
 * Servlet responsável por receber dados do Arduino e salvar no banco.
 */
@WebServlet("/receber-dados")
public class ReceberDadosServlet extends HttpServlet {

    private final Controle controle = new Controle();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            BigDecimal temperatura = new BigDecimal(request.getParameter("temperatura"));
            BigDecimal umidade = new BigDecimal(request.getParameter("umidade"));
            boolean chuva = Boolean.parseBoolean(request.getParameter("chuva"));

            Leituras leitura = new Leituras();
            leitura.setDataHora(new Date());
            leitura.setTemperatura(temperatura);
            leitura.setUmidade(umidade);
            leitura.setChuva(chuva);

            controle.salvarLeitura(leitura);
            response.getWriter().write("Dados recebidos e salvos com sucesso!");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Erro ao salvar dados.");
        }
    }
}
