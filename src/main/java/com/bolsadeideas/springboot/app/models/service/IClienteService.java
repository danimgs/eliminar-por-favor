package com.bolsadeideas.springboot.app.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.bolsadeideas.springboot.app.models.entity.Cliente;

//Por cada método en la clase dao vamos a tener dicho métodos en la clase Service. Nos copiamos los métodos de la interfaz (IClienteDao) al Service y los implementamos en la clase Service
public interface IClienteService {
	
	public List<Cliente> findAll();//Metodo para listar los clientes
	
	public Page<Cliente> findAll(Pageable pageable); //Metodo que retorna un Page
	
	public void save(Cliente cliente);//Metodo para añadir un nuevo cliente. Recibe el cliente
	
	public Cliente findOne(Long id); //Nos va a retornar un cliente para editarlo. Tenemos que implementar el metodo en la clase DAO
	
	public void delete(Long id);//Metodo borrar que nos recibe el id, obtiene el objeto y lo elimina
	
}
