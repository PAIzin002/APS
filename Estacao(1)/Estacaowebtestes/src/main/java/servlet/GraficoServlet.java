package servlet;

import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import modelo.Leituras;
import modelo.LeiturasDAO;

@WebServlet("/grafico")
public class GraficoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        response.setContentType("application/json;charset=UTF-8");

        LeiturasDAO dao = new LeiturasDAO();
        List<Leituras> leituras = dao.listarTodas();

        String json = new Gson().toJson(leituras);
        response.getWriter().write(json);
    }
}
