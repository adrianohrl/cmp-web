/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.adrianohrl.stile.control.bean;

import tech.adrianohrl.stile.model.personnel.Loggable;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author adrianohrl
 */
public class SessionUtils {
    
    private final static String loggableAtributeName = "loggedEmployee";

    public static HttpSession getSession() {
        return (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
    }

    public static HttpServletRequest getRequest() {
        return (HttpServletRequest) FacesContext.getCurrentInstance()
                .getExternalContext().getRequest();
    }
    
    public static boolean setUser(Loggable loggable) {
        HttpSession session = SessionUtils.getSession();
        if (session != null) {
            session.setAttribute(loggableAtributeName, loggable);        
            return true;
        }
        return false;
    }
    
    public static void invalidate() {
        HttpSession session = SessionUtils.getSession();
        if (session != null) {
            session.invalidate();
        }
    }

    public static String getUserName() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
        Loggable loggable = null;
        if (session != null) {
            loggable = (Loggable) session.getAttribute(loggableAtributeName);
        }
        return loggable != null ? loggable.getName() : null;
    }

    public static String getUserLogin() {
        HttpSession session = getSession();
        Loggable loggable = null;
        if (session != null) {
            loggable = (Loggable) session.getAttribute(loggableAtributeName);
        }
        return loggable != null ? loggable.getLogin() : null;
    }
    
}