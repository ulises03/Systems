/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import util.MyUtil;

/**
 *
 * @author admin
 */
@ManagedBean(name="app")
@ApplicationScoped
public class AppBean {

    public AppBean() {
    }
    /*
    Devuelve la ruta base
    */
    public String getBaseUrl(){
        return MyUtil.baseurl();
    }
    /*
    Devuelve la ruta despues del login
    */
    public String getBaseLogin(){
        return MyUtil.basePathLogin();
    }
}
