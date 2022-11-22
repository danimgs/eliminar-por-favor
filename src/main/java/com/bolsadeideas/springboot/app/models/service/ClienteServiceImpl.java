package com.bolsadeideas.springboot.app.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bolsadeideas.springboot.app.models.dao.IClienteDao;
import com.bolsadeideas.springboot.app.models.entity.Cliente;


//IMPORTANTE!!!!!!! Esta clase ha sido modificada para implementar la interfaz CrudRepository


//Una clase Service está basado en el patrón de diseño cascade o fachada. Un unico punto de acceso hacia distintos daos o repositorios
//Dentro de una clase servicio podriamos tener difernte tipos de atributos y operar con diferentes clases dao. De esta forma evitamos tener que acceder a los daos al controlador
@Service
public class ClienteServiceImpl implements IClienteService{
	
	//Inyectamos el cliente dao. Para la implementación, al ser una fachada, accedemos a los métodos del dao. Ahora mismo tenemos un único DAO pero desde la clase Service podríamos
	//acceder a los distintos daos que tengamos. Dentro de la clase Service podemos manejar la transacción sin tener que implementar las anotaciones transaccional en el DAO.
	//Incluso dentro de un método de la clase Service podemos interactuar con diferenctes dao, dentro de la misma trasacción.
	//En el controlador, estamos implementando el ClienteDao. Una vez tengamos la clase Service inyectaremos la clase Service 
	@Autowired
	private IClienteDao clienteDao;
	
	//Nos movemos las anotaciones @@Transactional desde la clase DAO al Service
	@Transactional(readOnly=true)
	@Override
	public List<Cliente> findAll() {
		//Cuando usemos la implementación a partir de la interfaz CrudRepository, el metodo findAll() va a retornar un iterable, por lo que lo tenemos que castear, para que devuelva la lista
		//Podemos verlo en dicha web https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories
		//return clienteDao.findAll(); Antiguo
		return (List<Cliente>) clienteDao.findAll(); //Nuevo
	}

	@Transactional
	@Override
	public void save(Cliente cliente) {
		clienteDao.save(cliente);
		
	}
	
	@Transactional(readOnly=true)
	@Override
	public Cliente findOne(Long id) {
		//Caso parecido que en el findAll(). Pero en vez de usar el método findOne(), se va a usar el método findById(), que en este caso retorna un Optional, 
		//que envuelve el resultado de la consulta (el objeto entity)
		//return clienteDao.findAll(); Antiguo
		return clienteDao.findById(id).orElse(null);//Nuevo. Si lo encuentra que nos devuelva el cliente, sino null. Tenemos más métodos dentro del Optional, como el get()...
	}

	@Transactional
	@Override
	public void delete(Long id) {
		//Mismo caso que antes, no corresponde dicho método. Se va a usar deleteById.
		//clienteDao.delete(id); Antiguo
		clienteDao.deleteById(id); //Nuevo
	}

	@Transactional(readOnly=true)
	@Override
	public Page<Cliente> findAll(Pageable pageable) {//Metodo para implementar la paginación 
		return clienteDao.findAll(pageable);
	}

}
