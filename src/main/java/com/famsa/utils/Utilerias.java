/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.famsa.utils;

import com.famsa.pojos.Usuario;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author Jhonny
 */
public class Utilerias {
    
    public static Usuario getUsuarioSession() {
        return (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
    }

    public static void setUsuarioSession(Usuario usuarioSession) {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuario", usuarioSession);
    }

    public static FacesMessage getFacesMessage(FacesMessage.Severity severity, String mensaje) {
        return new FacesMessage(severity, mensaje, null);
    }

    public static Logger log(String clazz, Level level) {
        Logger log = Logger.getLogger(clazz);
        log.setLevel(level);
        return log;
    }

    public static String getInformacion(int size) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd MMMM yyyy", new Locale("es", "MX"));
        DecimalFormat df = new DecimalFormat("###,###,###,###");
        return "Se encontraron: " + df.format(size) + " registro(s). " + sdf.format(new Date()) + ".";
    }

    public static List<String> convertFileToStr() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static Date restarSumarMes(Date fecha, int meses) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.add(Calendar.MONTH, meses);
        return calendar.getTime();
    }
}
