package com.bolsadeideas.springboot.app.models.dao;

//Esta clase no la vamos a usar ya que hemos comentado los métodos de la interfaz IClienteDao, ya que se va realizar la implementación por la interfaz CrudRepository
//No la eliminamos para dejarla como ejemplo


/****
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.bolsadeideas.springboot.app.models.entity.Cliente;





@Repository("clienteDaoJPA")//Anotacion para marcar la clase como componente de persistencia, de acceso a datos. Internamente es un esteriotipo de @Component.
							//Le añadimos un nombre por si queremos recuperarla desde el controlador mediante el @Qualifier
public class ClienteDaoImpl implements IClienteDao {

	@PersistenceContext//Contiene la unidad de persistencia. Inyecta el EM segun la configuración del jpa
	private EntityManager em; //Se encarga de manejar las clases de Entidades, el ciclo de vida, las persiste dentro del contexzto, todas las operaciones a la bbdd, de JPA. Van a la clase entity, no a la tabla
	
	@SuppressWarnings("unchecked")//Suprimimos el warning del em
	//@Transactional(readOnly=true)//marcamos el metodo como transaccional. Al ser consulta metemos el readOnly, si fuera insert quitariamos el readOnly
	//de esta forma, no se va a guardar el estado y vamos a mejorar el rendimiento del método
	//De esta manera la consulta no almacenará estado adicional a nivel de los objetos seleccionados.
	//Si tenemos una clase Service desde donde podemos acceder a la clase DAO, movemos las anotaciones @Transactional ya que podemos acceder directamente desde el Service, las comentamos.
	@Override
	public List<Cliente> findAll() {
		return em.createQuery("from Cliente").getResultList();
	}
	
	@Override
	//@Transactional(readOnly=true)
	public Cliente findOne(Long id) {
		return em.find(Cliente.class, id);//A traves de EM y el metodo find recuperamos el id del cliente y despues los actualizamos en el metodo save
	}

	
	//Para editar como para guardar uno nuevo
	@Override
	//@Transactional //Sin readOnly ya que es metodo de escritura para insertar un nuevo registro
	public void save(Cliente cliente) {
		if(cliente.getId() != null && cliente.getId() >0 )//Si el id del cliente es distinto de nulo y mayor que 0, se edita el cliente
		{
			em.merge(cliente);//Actualiar los datos existentes
		} else
		{
			em.persist(cliente);//Toma el objeto cliente y lo guarda dentro del contexto jpa de la persistencia, para insertarlo en la bbdd //El metodo persist crea un nuevo cliente
		}
	}

	

	@Override
	//@Transactional //Al actualizar la tabla no usamos ReadOnly
	public void delete(Long id) {
		//Lo primero obtenemos el cliente que vamos a eliminar. Lo obtenemos con el metodo findOne()
		//Cliente cliente = findOne(id);
		//Con el entity manager de JPA, usamos el metodo remove
		//em.remove(cliente);
		
		//Para hacerlo más limpio
		em.remove(findOne(id));
	}

}


***/
