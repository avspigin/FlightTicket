package ru.anton.je.jdbc.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/dispatcher")
public class DispatcherServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var dispatcher = req.getRequestDispatcher("/flights");
        req.setAttribute("dispatcher", true);
        dispatcher.forward(req, resp);   //перенаправляет на флайт и ответ исходит от самого флайт
        dispatcher.include(req, resp);    //перенаправляет на флайт, флайт возвращает ответ диспатчеру, ответ от диспатчера
        resp.sendRedirect("/flights"); //перенаправляет на любой урл.
    }
}
