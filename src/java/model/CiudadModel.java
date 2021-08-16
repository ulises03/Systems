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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import objclass.Ciudad;
import objclass.Usuario;
import respuesta.Respuesta;
import respuesta.RespuestaCiudad;
import sessions.Sesion;

/**
 *
 * @author admin
 */
public class CiudadModel {

    public CiudadModel() {
    }

    /*
    Te devuelve una respuesta ciudad con un estatus de respuesta y una lista ciudad 
    */
    public RespuestaCiudad getListCiudades() {
        PoolDB pool = new PoolDB();
        Respuesta objRespuesta = new Respuesta();
        RespuestaCiudad respuesta = new RespuestaCiudad();
        List<Ciudad> listCiudad = new ArrayList();
        Connection connection = null;
        String query = "";

        try {
            connection = pool.getConnection("activa");
            //WHERE ACTIVO = 1
            query = "SELECT ID_CIUDAD, DESCRIPCION, CODIGO, LADA, ACTIVO FROM C_CIUDAD with (nolock) Order by (ID_CIUDAD) ASC";

            PreparedStatement consulta = connection.prepareCall(query);
            ResultSet rs = consulta.executeQuery();

            while (rs.next()) {
                Ciudad ciudad = new Ciudad();
                ciudad.setId(rs.getInt("ID_CIUDAD"));
                ciudad.setDescripcion(rs.getString("DESCRIPCION"));
                ciudad.setLada(rs.getInt("LADA"));
                ciudad.setCodigo(rs.getString("CODIGO"));
                ciudad.setActivo(rs.getBoolean("ACTIVO"));

                listCiudad.add(ciudad);
            }
            rs.close();
            consulta.close();
            connection.close();

            if (listCiudad.size() > 0) {
                objRespuesta.setId(0);
                objRespuesta.setMensaje("Lista de ciudades llena");
            } else {
                objRespuesta.setId(1);
                objRespuesta.setMensaje("No se encontraron ciudades");
            }
        } catch (SQLException | NamingException e) {
            objRespuesta.setId(-1);
            objRespuesta.setMensaje("Error al conectarse");
        }
        respuesta.setListaCiudad(listCiudad);
        respuesta.setRespuesta(objRespuesta);

        return respuesta;
    }
    
    /*
    Te devuelve una respuesta despues de actualizar 
    */
    public Respuesta updateCiudad(Ciudad ciudad) {
        PoolDB pool = new PoolDB();
        Respuesta objRespuesta = new Respuesta();
        Connection connection = null;
        String query = "";

        try {
            connection = pool.getConnection("activa");
            query = "  UPDATE C_CIUDAD SET DESCRIPCION = ?, CODIGO = ?, LADA = ?  WHERE ID_CIUDAD = ? ";
            PreparedStatement consulta = connection.prepareStatement(query);

            consulta.setString(1, ciudad.getDescripcion());
            consulta.setString(2, ciudad.getCodigo());
            consulta.setInt(3, ciudad.getLada());
            consulta.setInt(4, ciudad.getId());

            if (consulta.executeUpdate() > 0) {
                objRespuesta.setId(0);
                objRespuesta.setMensaje("Se actualizo exitosamente");
            } else {
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
    Te devuelve una respuesta despues de eliminar 
    */
    public Respuesta deleteCiudad(int id) {
        PoolDB pool = new PoolDB();
        Respuesta objRespuesta = new Respuesta();
        Connection connection = null;
        String query = "";

        try {
            connection = pool.getConnection("activa");
            query = "DELETE FROM C_CIUDAD WHERE ID_CIUDAD = ?";
            PreparedStatement consulta = connection.prepareStatement(query);
            consulta.setInt(1, id);

            if (consulta.executeUpdate() > 0) {
                objRespuesta.setId(0);
                objRespuesta.setMensaje("Se elimino exitosamente");
            } else {
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
    public Respuesta addCiudad(Ciudad ciudad) {
        PoolDB pool = new PoolDB();
        Respuesta objRespuesta = new Respuesta();
        Connection connection = null;
        String query = "";
        
        Sesion sesion = new Sesion();
        Usuario user = new Usuario();
        user = sesion.getUserSession();


        try {
            //ID_USUARIO FECHA_ALTA
            connection = pool.getConnection("activa");
            query = "INSERT INTO C_CIUDAD(DESCRIPCION, CODIGO, LADA, ACTIVO,ID_USUARIO, FECHA_ALTA) VALUES(?,?,?,?,?,SYSDATETIME())";
            PreparedStatement consulta = connection.prepareStatement(query);
            consulta.setString(1, ciudad.getDescripcion());
            consulta.setString(2, ciudad.getCodigo());
            consulta.setInt(3, ciudad.getLada());
            consulta.setBoolean(4, ciudad.isActivo());
            consulta.setInt(5, user.getId());

            if (consulta.executeUpdate() > 0) {
                objRespuesta.setId(0);
                objRespuesta.setMensaje("Nuevo registro insertado");
            } else {
                objRespuesta.setId(1);
                objRespuesta.setMensaje("No se pudo insertar el registro");
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
