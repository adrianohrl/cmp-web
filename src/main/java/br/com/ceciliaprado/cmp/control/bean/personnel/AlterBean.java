/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.bean.personnel;

import br.com.ceciliaprado.cmp.control.bean.DataSource;
import br.com.ceciliaprado.cmp.control.bean.SessionUtils;
import br.com.ceciliaprado.cmp.control.dao.personnel.LoggableDAO;
import br.com.ceciliaprado.cmp.model.personnel.Loggable;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpSession;

/**
 *
 * @author adrianohrl
 * @param <T>
 */
public abstract class AlterBean<T extends Loggable> implements Serializable {
    
    private T loggable;
    private String login;
    private String password;    
    
    public String alter() {
        if (password.isEmpty())
        {
            return "";
        }
        loggable.setPassword(password);
        EntityManager em = DataSource.createEntityManager();
        LoggableDAO<T> loggableDAO = new LoggableDAO<>(em);
        loggableDAO.update(loggable);
        em.close();
        HttpSession session = SessionUtils.getSession();
        session.invalidate();
        update();
        return "/index";
    }
    
    public void select() {
        loggable = null;
        if (login == null || login.isEmpty()) {
            return;
        }
        for (T l : getLoggables()) {
            if (login.equals(l.getLogin())) {
                loggable = l;
                HttpSession session = SessionUtils.getSession();
                session.setAttribute("loggedEmployee", loggable);
            }
        }
    }
    
    protected abstract void update();
    
    protected abstract List<T> getLoggables();

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
}
