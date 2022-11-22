package com.bolsadeideas.springboot.app.models.dao;



import org.springframework.data.repository.PagingAndSortingRepository;

import com.bolsadeideas.springboot.app.models.entity.Cliente;



//public interface IClienteDao {
	
	
	//Vamos a comentar los 4 métodos y el nombre de la clase ya que lo vamos a realizar mediante la implementación usando la interfaz CrudRepository.
	//Vamos a convertir esta interfaz en nuestra CrudReposotiry
	//public List<Cliente> findAll();//Metodo para listar los clientes
	
	//public void save(Cliente cliente);//Metodo para añadir un nuevo cliente. Recibe el cliente
	
	//public Cliente findOne(Long id); //Nos va a retornar un cliente para editarlo. Tenemos que implementar el metodo en la clase DAO
	
	//public void delete(Long id);//Metodo borrar que nos recibe el id, obtiene el objeto y lo elimina


//CrudRepository<T, ID> --> Para el T, hay que indicar el tipo de Dato de nuestra clase entity Cliente, en nuestro caso Cliente,
// Para el ID --> Tipo de dato de nuestra clave, en este caso el id, que sería Long
//No hace falta anotarlo ni con Repository, ni con Component, ya que se está inyectando en la clase Service y IClienteDao lo hereda de CrudRepository
public interface IClienteDao extends PagingAndSortingRepository<Cliente, Long> {  //Anteriormente heredábamos de "CrudRepository<Cliente, Long>", 
																					//pero para implementar la paginación vamos a cambiarlo por PagingAndSortingRepository
																					//que hereda de CrudRepository
	//Aqui podemos añadir consultar personalizadas, pero ya los estamos declarando los métodos en la clase Service

}
