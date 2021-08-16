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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import objclass.Usuario;
import respuesta.Respuesta;
import respuesta.RespuestaUsuario;
import seguridad.HexDigest;
import sessions.Sesion;

/**
 *
 * @author admin
 */
public class UsuarioModel {
    
    public UsuarioModel() {
    }
    
   /*
    Te devuelve una respuestaUsuario con el estatus de una respuesta y una lista de usuarios 
    */
    public RespuestaUsuario getListUsuarios() {
        PoolDB pool = new PoolDB();
        Respuesta objRespuesta = new Respuesta();
        RespuestaUsuario respuesta = new RespuestaUsuario();
        List<Usuario> listUsuario = new ArrayList();
        Connection connection = null; 
        String query = "";
        
        try {
            connection = pool.getConnection("activa");
            query = "SELECT ID_USUARIO, USUARIO,NOMBRE_USUARIO,CORREO FROM S_USUARIOS with (nolock) Order by (ID_PERFIL) DESC";
            
            PreparedStatement consulta = connection.prepareCall(query);
            ResultSet rs = consulta.executeQuery();
            while (rs.next()) {
                Usuario user = new Usuario();
                user.setId(rs.getInt("ID_USUARIO"));
                user.setUsuario(rs.getString("USUARIO"));
                user.setNombre(rs.getString("NOMBRE_USUARIO"));
                user.setCorreo(rs.getString("CORREO"));
                listUsuario.add(user);
            }
            rs.close();
            consulta.close();
            connection.close();
            
            if(listUsuario.size() > 0){
                objRespuesta.setId(0);
                objRespuesta.setMensaje("Lista de usuarios llena");
            }else{
                objRespuesta.setId(1);
                objRespuesta.setMensaje("No se encontraron usuarios");
            }
        } catch (SQLException | NamingException e) {
            objRespuesta.setId(-1);
            objRespuesta.setMensaje("Error al conectarse");
        }
        respuesta.setListaUsuario(listUsuario);
        respuesta.setRespuesta(objRespuesta);
        
        return respuesta;
    }
    
    /*
    Te devuelve una respuesta despues de eliminar 
    */
    public Respuesta deleteUsuario(int id){
        PoolDB pool = new PoolDB();
        Respuesta objRespuesta = new Respuesta();
        Connection connection = null; 
        String query = "";
        
        try {
            connection = pool.getConnection("activa");
            query = "DELETE FROM S_USUARIOS WHERE ID_USUARIO = ?";
            PreparedStatement consulta = connection.prepareStatement(query);
            consulta.setInt(1, id);           
            if(consulta.executeUpdate() > 0){
                objRespuesta.setId(0);
                objRespuesta.setMensaje("Se elimino exitosamente");
            }else{
                objRespuesta.setId(1);
                objRespuesta.setMensaje("No se encontro el registro");
            }
            consulta.close();
            connection.close();            
            
        } catch (SQLException | NamingException e) {
            objRespuesta.setId(-1);
            objRespuesta.setMensaje("Error al momento de conectar");
            Logger.getLogger(LoginModel.class.getName()).log(Level.SEVERE, null, e);
        }
        return objRespuesta;
    }
    
    /*
    Te devuelve una respuesta despues de añadir 
    */
    public Respuesta addUsuario(Usuario user){
        PoolDB pool = new PoolDB();
        Respuesta objRespuesta = new Respuesta();
        Connection connection = null; 
        String query = "";
        String passHex = HexDigest.hexDigest(user.getPassword());
        
        Sesion sesion = new Sesion();
        Usuario usersesion = new Usuario();
        usersesion = sesion.getUserSession();

        try {
            connection = pool.getConnection("activa");
            query = "INSERT INTO S_USUARIOS(USUARIO, NOMBRE_USUARIO, CORREO, ACTIVO, PASSWORD, ID_USUARIO_MODIFICA, FECHA_ALTA) VALUES(?,?,?,?,?,?,SYSDATETIME())";
            PreparedStatement consulta = connection.prepareStatement(query);
            consulta.setString(1, user.getUsuario());
            consulta.setString(2, user.getNombre());
            consulta.setString(3, user.getCorreo());
            consulta.setBoolean(4, user.isActivo());
            consulta.setString(5, passHex);
            consulta.setInt(6, usersesion.getId());
            
            if(consulta.executeUpdate() > 0){
                objRespuesta.setId(0);
                objRespuesta.setMensaje("Se añadio exitosamente");
            }else{
                objRespuesta.setId(1);
                objRespuesta.setMensaje("No se encontro el registro");
            }
            consulta.close();
            connection.close();            
            
        } catch (SQLException | NamingException e) {
            objRespuesta.setId(-1);
            objRespuesta.setMensaje("Error al momento de conectar");
            Logger.getLogger(LoginModel.class.getName()).log(Level.SEVERE, null, e);
        }
        return objRespuesta;
    }
    /*
    Te devuelve una respuesta despues de actualizar 
    */
    public Respuesta updateUsuario(Usuario user){
        PoolDB pool = new PoolDB();
        Respuesta objRespuesta = new Respuesta();
        Connection connection = null; 
        String query = "";
        
        try {
            connection = pool.getConnection("activa");
            query = "UPDATE S_USUARIOS SET USUARIO = ?, NOMBRE_USUARIO = ?, CORREO = ? WHERE ID_USUARIO = ? ";
            PreparedStatement consulta = connection.prepareStatement(query);
            consulta.setString(1, user.getUsuario());
            consulta.setString(2, user.getNombre());
            consulta.setString(3, user.getCorreo());
            consulta.setInt(4, user.getId());
            
            if(consulta.executeUpdate() > 0){
                objRespuesta.setId(0);
                objRespuesta.setMensaje("Se actualizo exitosamente");
            }else{
                objRespuesta.setId(1);
                objRespuesta.setMensaje("No se encontro el registro");
            }
            consulta.close();
            connection.close();            
            
        } catch (SQLException | NamingException e) {
            objRespuesta.setId(-1);
            objRespuesta.setMensaje("Error al momento de conectar");
            Logger.getLogger(LoginModel.class.getName()).log(Level.SEVERE, null, e);
        }

        return objRespuesta;
    }
}
