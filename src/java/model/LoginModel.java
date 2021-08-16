/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;
import data.PoolDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import objclass.Usuario;
import respuesta.Respuesta;
import respuesta.RespuestaLogin;
import seguridad.HexDigest;

/**
 *
 * @author admin
 */
public class LoginModel {

    public LoginModel() {
    }
    
    /*
    Te devuelve una respuestaLogin con el estatus de la respuesta y el usuario logueado 
    */
    public RespuestaLogin requestLogin(String user, String password){
        
        PoolDB pool = new PoolDB();
        Respuesta objRespuesta = new Respuesta();
        RespuestaLogin respuesta = new RespuestaLogin();
        Connection connection = null; 
        String query = "";
        String passHex = HexDigest.hexDigest(password);
         Usuario objuser = null;
        try {
            connection = pool.getConnection("activa");
            query = "SELECT ID_USUARIO, USUARIO, NOMBRE_USUARIO, CORREO, ACTIVO FROM S_USUARIOS with(nolock) WHERE PASSWORD = ? AND USUARIO = ?";
            PreparedStatement consulta = connection.prepareStatement(query);
            consulta.setString(1, passHex);
            consulta.setString(2, user);
            ResultSet rs = consulta.executeQuery();
            
            while (rs.next()) {
                
                objRespuesta.setId(0);
                objRespuesta.setMensaje("Usuario encontrado");
                
                objuser = new Usuario();
                objuser.setId(rs.getInt("ID_USUARIO"));
                objuser.setUsuario(rs.getString("USUARIO"));
                objuser.setNombre(rs.getString("NOMBRE_USUARIO"));
                objuser.setCorreo(rs.getString("CORREO"));
                objuser.setActivo(rs.getBoolean("ACTIVO"));
            }
            if(objuser == null){
                objRespuesta.setId(1);
                objRespuesta.setMensaje("No se encontro al usuario");
            }
            
            rs.close();
            consulta.close();
            connection.close();
            
        } catch (SQLException | NamingException e) {
            objRespuesta.setId(-1);
            objRespuesta.setMensaje("Error al conectar");
            Logger.getLogger(LoginModel.class.getName()).log(Level.SEVERE, null, e);
        }
        
        respuesta.setUsuario(objuser);
        respuesta.setRespuesta(objRespuesta);
        return respuesta;
    }
    
}
