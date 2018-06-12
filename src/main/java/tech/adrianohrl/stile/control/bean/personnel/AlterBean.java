package tech.adrianohrl.stile.control.bean.personnel;

import tech.adrianohrl.stile.control.bean.DataSource;
import tech.adrianohrl.stile.control.bean.SessionUtils;
import tech.adrianohrl.stile.control.dao.personnel.LoggableDAO;
import tech.adrianohrl.stile.model.personnel.Loggable;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 * @param <T>
 */
public abstract class AlterBean<T extends Loggable> implements Serializable, Iterable<T> {
    
    private T loggable = null;
    private String login;
    private String password;    
    
    public String alter() {
        if (loggable == null || password.isEmpty())
        {
            return "";
        }
        loggable.setPassword(password);
        EntityManager em = DataSource.createEntityManager();
        LoggableDAO<T> loggableDAO = new LoggableDAO<>(em);
        loggableDAO.update(loggable);
        em.close();
        SessionUtils.invalidate();
        update();
        return "/index";
    }
    
    public void select() {
        loggable = null;
        if (login == null || login.isEmpty()) {
            return;
        }
        for (T l : this) {
            if (login.equals(l.getLogin())) {
                loggable = l;
                SessionUtils.setUser(loggable);
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
