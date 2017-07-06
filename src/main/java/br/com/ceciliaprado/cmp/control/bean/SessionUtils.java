/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.bean;

import br.com.ceciliaprado.cmp.model.personnel.Loggable;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author adrianohrl
 */
public class SessionUtils {

    public static HttpSession getSession() {
        return (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
    }

    public static HttpServletRequest getRequest() {
        return (HttpServletRequest) FacesContext.getCurrentInstance()
                .getExternalContext().getRequest();
    }

    public static String getUserName() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
        return session != null ? ((Loggable) session.getAttribute("loggedEmployee")).getName() : null;
    }

    public static String getUserLogin() {
        HttpSession session = getSession();
        return session != null ? ((Loggable) session.getAttribute("loggedEmployee")).getLogin() : null;
    }
    
}