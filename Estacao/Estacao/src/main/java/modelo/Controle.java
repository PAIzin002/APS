package modelo;

import DAL.ClimaDAO;
import DAL.ChuvaDAO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

public class Controle {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    public void salvarLeituraClima(Clima clima) {
        if (!validar(clima)) return;

        ClimaDAO dao = new ClimaDAO();
        dao.cadastrarClima(clima);
    }

    public void salvarLeituraChuva(Chuva chuva) {
        if (!validar(chuva)) return;

        ChuvaDAO dao = new ChuvaDAO();
        dao.cadastrarChuva(chuva);
    }

    public void atualizarClima(Clima clima) {
        if (!validar(clima)) return;

        ClimaDAO dao = new ClimaDAO();
        dao.atualizarClima(clima);
    }

    public void atualizarChuva(Chuva chuva) {
        if (!validar(chuva)) return;

        ChuvaDAO dao = new ChuvaDAO();
        dao.atualizarChuva(chuva);
    }

    public void removerClima(Integer id) {
        new ClimaDAO().removerClima(id);
    }

    public void removerChuva(Integer id) {
        new ChuvaDAO().removerChuva(id);
    }

    public void listarClima() {
        new ClimaDAO().listarClima();
    }

    public void listarChuva() {
        new ChuvaDAO().listarChuva();
    }

    private <T> boolean validar(T entidade) {
        for (ConstraintViolation<T> v : validator.validate(entidade)) {
            System.out.println(" " + v.getMessage());
            return false;
        }
        return true;
    }
}
