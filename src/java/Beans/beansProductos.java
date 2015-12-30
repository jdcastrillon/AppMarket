/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import Modelo.Producto;
import cl.services.ws.MercaSuperWS;
import cl.services.ws.MercaSuperWS_Service;
import cl.services.ws.Productos;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.xml.rpc.encoding.XMLType;
import javax.xml.ws.WebServiceRef;

/**
 *
 * @author ADMIN
 */
@ManagedBean
@SessionScoped
public class beansProductos {

    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/192.168.10.200_8080/MercaSuper/MercaSuperWS.wsdl")
    private MercaSuperWS_Service service;

    /**
     * Creates a new instance of Productos
     */
    ArrayList<Producto> listaProducto = new ArrayList();
    ArrayList<Producto> Carrito = new ArrayList();
    Producto MiProducto;
    private int codigoProducto;

    public beansProductos() {
    }

    @PostConstruct
    public void cargarDatos() {
        listaProducto.clear();
        Carrito.clear();
        listaProducto.add(new Producto(100, "Pollo", 15000, 1));
        listaProducto.add(new Producto(101, "Carne", 15000, 1));
        listaProducto.add(new Producto(102, "Pan", 15000, 1));
        listaProducto.add(new Producto(103, "Pescado", 15000, 1));
        listaProducto.add(new Producto(104, "Carne bola negra", 15000, 1));
        listaProducto.add(new Producto(105, "Vasos", 15000, 1));
        listaProducto.add(new Producto(106, "Pc", 15000, 1));
    }

    public void pasarDatosCarrito() {
        System.out.println("Esta buscando " + codigoProducto);
        try {
            List<Productos> NomProducto = consultarProducto(codigoProducto);
            System.out.println("-------------------------");
            System.out.println("-- " + NomProducto.toString());
        } catch (Exception ex) {
            System.out.println("Error : " + ex.toString());
        }

//        boolean Encontrado = false;
//        for (int i = 0; i < Carrito.size(); i++) {
//            if (Carrito.get(i).getCodigo() == codigoProducto) {
//                Encontrado = true;
//                Carrito.get(i).setCant(Carrito.get(i).getCant() + 1);
//                break;
//            }
//        }
//        if (Encontrado == false) {
//            Carrito.add(new Producto(codigoProducto, NomProducto, 1500, 1));
//        }
    }

    public void pasarDatos(Producto pro) {
        MiProducto = (Producto) pro;
    }

    public void actualizarCantidad() throws IOException {
        System.out.println("entro al metodo");
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String cantidad = request.getParameter("editar:cant");
        System.out.println("--- " + cantidad);
        boolean edito = false;
        for (int i = 0; i < Carrito.size(); i++) {
            if (Carrito.get(i).getCodigo() == MiProducto.getCodigo()) {
                System.out.println("-------------------------");
                edito = true;
                Carrito.get(i).setCant(Integer.parseInt(cantidad));
                break;
            }
        }
        MiProducto = null;
        if (edito) {
            System.out.println("****");
            FacesContext.getCurrentInstance().getExternalContext().redirect("Mercar.xhtml");
        }
    }

    public ArrayList<Producto> getListaProducto() {
        return listaProducto;
    }

    public void setListaProducto(ArrayList<Producto> listaProducto) {
        this.listaProducto = listaProducto;
    }

    public ArrayList<Producto> getCarrito() {
        return Carrito;
    }

    public void setCarrito(ArrayList<Producto> Carrito) {
        this.Carrito = Carrito;
    }

    public int getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(int codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public Producto getMiProducto() {
        return MiProducto;
    }

    public void setMiProducto(Producto MiProducto) {
        this.MiProducto = MiProducto;
    }

//    private java.util.List<cl.services.ws.Productos> consultarProducto(int codigo) {
//        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
//        // If the calling of port operations may lead to race condition some synchronization is required.
//        cl.services.ws.MercaSuperWS port = service.getMercaSuperWSPort();
//        MercaSuperWS ser=new MercaSuperWS();
//        System.out.println("***");
//        System.out.println("--------------------");
//        XMLType xml = null;
//        xml=(XMLType) port.consultarProducto(codigo);
//        System.out.println("-- " + xml.toString());
//        System.out.println("-------");
//        System.out.println("---");
//        return null;
//    }
    private java.util.List<cl.services.ws.Productos> consultarProducto(int codigo) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        cl.services.ws.MercaSuperWS port = service.getMercaSuperWSPort();
        System.out.println("------");

        port.consultarProducto(codigo);
        
        return null;
    }

}
