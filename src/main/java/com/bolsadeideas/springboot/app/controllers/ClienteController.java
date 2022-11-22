package com.bolsadeideas.springboot.app.controllers;


import java.io.IOException;
import java.net.MalformedURLException;

import java.util.Map;


import javax.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bolsadeideas.springboot.app.models.entity.Cliente;
import com.bolsadeideas.springboot.app.models.service.IClienteService;
import com.bolsadeideas.springboot.app.models.service.IUploadFileService;
import com.bolsadeideas.springboot.app.util.paginator.PageRender;

@Controller //Marcamos la clase como un controlador
@SessionAttributes("cliente")//Indicamos que se va a guardar en sesión los atributos del objeto cliente, mapeados al formulario. Cada vez que se invoca el crear o el editar (una petición GET) va a obtener 
//el objeto cliente, lo guarda en los atributos de la sessión y lo pasa a la vista, y en la vista queda dentro de la session, por lo tanto todos los datos quedan persistentes hasta que se envie al método guardar,
//donde se va a eliminar la sesión, con el metodo SessionStatus
public class ClienteController {

	@Autowired
	//@Qualifier("clienteDaoJPA")//Usammos esta anotación ya que podemos tener mas Clases que implementen de IClienteDao por Hibernate o JDBC
	//private IClienteDao clienteDao; //Cliente mas generico, en este caso el de la interfaz. Va a buscar un componente registrado en el contenedor. busca un beans que implemente la interfaz ICienteDao.
									//De esta forma dentro de la Clase ClienteDaoImpl. Como la clase ClienteDaoImpl está registrado con Repository e implementa de la interfaz ICienteDao, lo encuentra y lo
									//inyecta en el atributo clienteDao. De esta forma lo podemos utilizar mediante el modelAttribute
	
	//Una vez tengamos la Clase Service inyectaremos el Cliente Service en vez de tener de forma directa el ClienteDao. Comentamos el IClienteDao
	private IClienteService clienteService;
	
	@Autowired
	private IUploadFileService uploadFileService;
	
	//private final Logger log = org.slf4j.LoggerFactory.getLogger(getClass()); //Para mostrar log por la consola. Lo comentamos ya que nos lo llevamos a la clase service UploadFileServiceImple 
	
	//Constante de la carpeta donde guardamos las imágenes del proyectos. Lo comentamos ya que nos lo llevamos a la clase service UploadFileServiceImpl
	//private final static String UPLOADS_FOLDER = "uploads";
	
	//Metodo para cargar las imágenes
	@GetMapping(value="uploads/{filename:.+}")//El .+ es para no truncar la extensión del archivo
	public ResponseEntity<Resource> verFoto(@PathVariable String filename){//Este metodo va a devolver un recurso (imagen)
		
		//Implementamos el método load() de la clase Service UploadFileServiceImple
		Resource recurso = null;
		try {
			recurso = uploadFileService.load(filename);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		//Lo comentamos ya que nos lo llevamos a la clase service UploadFileServiceImple al metodo load()
		/*Path pathFoto = Paths.get(UPLOADS_FOLDER).resolve(filename).toAbsolutePath();//Con el resolve, nos permite añadir el nombre del archivo.Lo comentamos ya que nos lo llevamos a la clase service UploadFileServiceImpl
		log.info("pathFoto: "+ pathFoto);
		Resource recurso = null;
		try {
			recurso = new UrlResource(pathFoto.toUri());
			if(!recurso.exists()|| !recurso.isReadable()) {
				throw new RuntimeException("Error: no se puede cargar la imagen: "+ pathFoto.toString() );
			}
		} catch (MalformedURLException e) {//Excepción para ver si está mal formada la url
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\"" + recurso.getFilename() + "\"")
				.body(recurso);//se pasa nuestra imagen cargada en el urlResource
	}
	
	
	
	//Metodo para ver el detalle y la foto del cliente
	@GetMapping(value="/ver/{id}")
	public String ver(@PathVariable(value="id") Long id, Map<String, Object> model, RedirectAttributes flash) {
					//Path para pasarle el valor del id del cliente. Map para pasar datos a la vista. RedirectAttribute para mensajes de flash
		
		Cliente cliente = clienteService.findOne(id); //Obtenemos el CLiente a traves del Cliente Service
		if(cliente==null) {
			flash.addFlashAttribute("error","El Cliente no existe en la base de datos.");
			return "redirect:/listar";
		}
		
		model.put("cliente", cliente);
		model.put("titulo","Detalle cliente: "+ cliente.getNombre());
		
		return "ver";
	}
	
	
	@RequestMapping(value="/listar",method=RequestMethod.GET)//RequestMapping, para validar el ejemplo. Por defecto el tipo de petición es GET
	public String Listar(@RequestParam(name="page", defaultValue= "0") int page, Model model) { 
		//Metemos @RequestParam para obtener la pagina actual en la que estamos, mediante la url. Le indicamos el nombre que le vamos a dar y la página por defecto, la 0
		//Metemos la clase model para meter datos a la vista
		Pageable pageRequest = PageRequest.of(page, 4); //Le vamos a indicar que queremos mostrar 5 registros por página. Si hay mas registros que paginas, lo muestra por rangos
		Page<Cliente> clientes = clienteService.findAll(pageRequest); //Tenemos que hacer la invocación al service pero Paginable, que retorna un Page. Nos devuelve el listado de clientes
		
		//Vamos a crear el objeto PageRender que se encarga de calcular parametros, como total de paginas, pagina actual, para tener un listado de clientes. Le tenemos que pasar la url
		//de la vista y los clientes, de nuestra lista paginable
		PageRender <Cliente> pageRender = new PageRender<>("/listar",clientes); //C
		model.addAttribute("titulo", "Listado de Clientes");
		model.addAttribute("clientes",clientes); //Le pasamos el listado de clientes a la vista. Anteriormente le pasabamos el metodo "clienteService.findAll()"
		model.addAttribute("page", pageRender);//Añadimos a la vista como 'page' nuestro pageRender (paginador)
		return "listar";
	}
	
	@RequestMapping(value="/form")//Primeramente mostramos al usuario un formulario para despues guardar el usuario a en la bbdd. Por defecto el method es GET
	public String crear(Map<String, Object> model) {
		Cliente cliente = new Cliente();
		model.put("cliente", cliente); //Cliente del metodo crear
		model.put("titulo","Formulario de Cliente");
		return "form";
		
	}
	
	@RequestMapping(value="/form/{id}")//Misma url que el form pero pasandole el id.
	public String editar(@PathVariable(value="id") Long id, Map<String, Object> model, RedirectAttributes flash) {//con el PathVariable: anotación para que inyecte el valor del parametro de la ruta. 
																				//Le damos el nombre del parametro que se tiene que llamar igual que el value del request Mapping
		Cliente cliente = null;
		if(id>0) {
			cliente = clienteService.findOne(id);
			if(cliente==null) {
				flash.addFlashAttribute("error","El ID del Cliente no existe en la BBDD!");
				return "redirect:/listar";
			}
		} else {
			flash.addFlashAttribute("error","El ID del Cliente no puede ser cero!");
			return "redirect:/listar";
		}
		model.put("cliente", cliente);
		model.put("titulo", "Editar Cliente");
		
		return "form";
	}
	
	@RequestMapping(value="/form", method=RequestMethod.POST)//Despues a traves del submit enviamos los datos del formulario, mediante el post. Con el get recuperábamos los datos
	public String guardar(@Valid /*@ModelAttribute ("cliente")*/ Cliente cliente, BindingResult result, Model model, @RequestParam("file") MultipartFile foto, RedirectAttributes flash, SessionStatus status) { //Recibimos el Cliente y redirigimos a la vista Listar para ver que se ha guardado correctamente
													//para habilitar la validación usamos la anotación @Valid en el argumento del objeto que se recibe en el formulario.
													//Añadimos el BindingResult para tratar si el formulario tiene errores que vuelva a la vista formulario.
													//IMPORTANTE: El BindingResult siempre va junto al objeto del formulario, como argumento del método. Despues se podran añadir mas parametros.
		//El /*@ModelAttribute*/ lo comentamos ya que si el Cliente que se pasa como argumento de este método (Cliente cliente), tiene el mismo nombre que el "cliente"
		//que se le está pasando a la vista en el método crear (model.put("cliente", cliente), no hace falta añadirlo ya que lo pasa de forma automática.
		//SessionStatus para cerrar la session
		//RedirectAttributes para mostrar mensajes de alerta al usuario en la vista
	
		//Si tiene errores que vuelva a la vista formulario, y sino que guarde al cliente. Si falla pasamos el titulo
		
		//Le añadimos la anotación @RequestParam, pasandole el nombre del parametro que le hemos añadido a la vista, (file), para
		if(result.hasErrors()) {
			model.addAttribute("titulo","Formulario de Cliente");
			return "form";
		}
		
		if(!foto.isEmpty()) {//Si no está vacio, lo copiamos al directorio donde lo queramos guardar
			//Path directorioRecursos = Paths.get("src//Main/resources//static//uploads");//le pasamos la ruta donde guardar nuestra imágenes. Comentamos esto ya que vamos a guardar las imágenes fuera de nuestra app
			//String rootPath = directorioRecursos.toFile().getAbsolutePath(); //Obtenemos el string del directorio. De esta forma con el objeto String podemos concatenar el nombre del archivo para poder
																			 //moverlo o escribirlo donde queramos. Vamos a comentarlo tambien ya que se va a modificar la ruta donde almacenar las imágenes
			//String rootPath = "C://Temp//uploads";//Añadimos una ruta externa donde almacenar las imágenes del cliente. Tiene que ser la misma que la clase de configuración.
													//Comentamos ya que vamos a indicar una ruta absoluta dentro del proyecto.
			
			//En esta parte vamos a eliminar la imagen antigua si se reemplaza por una nueva. Primero preguntamos si el usuario existe (si es distinto de null, existe), con el id y si es mayor que 0
			//y tambien preguntar por la imagen
			if(cliente.getId() != null 
					&& cliente.getId() > 0
					&& cliente.getFoto() != null
					&& cliente.getFoto().length() > 0){
				
				//Lo comentamos ya que lo vamos a realizar en la clase service UploadFileServiceImple
				/*Path rootPath = Paths.get(UPLOADS_FOLDER).resolve(cliente.getFoto()).toAbsolutePath();//Obtenemos la ruta absoluta de la imagen.
				File archivo = rootPath.toFile();//Obtenemos el file
				if(archivo.exists() && archivo.canRead()){//Validamos si el archivo existe y que sea accesible
					archivo.delete();
				}*/
				
				uploadFileService.delete(cliente.getFoto()); //Lo eliminamos con el obteniendo la imagen
			}
			
			//Lo comentamos ya que nos lo llevamos a la clase service UploadFileServiceImple al metodo copy(), ( menos el flash) y el cliente.setFoto
			/*String uniqueFilename = UUID.randomUUID().toString() + "_" + foto.getOriginalFilename();//Añadimos un identificador ramndon para que el nombre del archivo sea único
			Path rootPath = Paths.get(UPLOADS_FOLDER).resolve(uniqueFilename);//De esta forma guardamos la imagenes dentro de los archivos del proyecto, dentro de uploads
			
			Path rootAbsolutePath = rootPath.toAbsolutePath();//Obtenemos la ruta absoluta
			log.info("rootPath: "+ rootPath); //Path relativo
			log.info("rootAbsolutePath: "+ rootAbsolutePath);//Path absoluto
			
			try {
				//byte[] bytes = foto.getBytes();//Vamos a obtener los bytes del archivo. Nos pide manejo de errores
				//Path rutaCompleta = Paths.get(rootPath + "//" + foto.getOriginalFilename()); //Obtenemos la ruta final, con la ruta de nuestro directorio, concatenado con el nombre del archivo
				//Files.write(rutaCompleta, bytes);//De esta forma, creamos y escribimos la imagen en el directorio upload. Comentamos las 3 líneas ya que en vez de usar FilesWrite, usamos el método copy
				Files.copy(foto.getInputStream(), rootAbsolutePath);//Obtenemos el string de entrada de nuestra imagen y la ruya absoluta, para poder copiarla a la nueva ruta
				
				flash.addFlashAttribute("info","Ha subido correctamente '" + uniqueFilename + "'");//Añadimos un mensaje de alerta
				
				cliente.setFoto(uniqueFilename);//Finalmente le pasamos el nombre de la foto al Cliente, y así poder mostrarlo en la pagina de detalle
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}   */
			
			
			//De esta forma movemos la imagen
			String uniqueFilename = null;
			try {
				uniqueFilename = uploadFileService.copy(foto);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			flash.addFlashAttribute("info","Ha subido correctamente '" + uniqueFilename + "'");//Añadimos un mensaje de alerta
			
			cliente.setFoto(uniqueFilename);//Finalmente le pasamos el nombre de la foto al Cliente, y así poder mostrarlo en la pagina de detalle
		}
		
		String mensajeFlash = (cliente.getId() != null) ? "Cliente editado con éxito!" : "Cliente creado con éxito!";
		clienteService.save(cliente);
		status.setComplete();//Para cerrar la sesión. Al invocar este metodo elimina el objeto cliente de la session
		flash.addFlashAttribute("success",mensajeFlash); // Le pasamos el atributo "success", que luego vamos utilizar en la vista (Comprobando si existe)
		return "redirect:listar";
	}
	
	@RequestMapping(value="/eliminar/{id}")
	public String eliminar(@PathVariable (value="id") Long id, RedirectAttributes flash) {
		if(id>0) {
			Cliente cliente = clienteService.findOne(id);//Para eliminar la foto del cliente, 1º obtenemos el cliente
			clienteService.delete(id);
			flash.addFlashAttribute("success","Cliente eliminado con exito!");
			
			//Validamos que la foto exista antes de eliminar. Lo comentamos ya que se ha hecho en el metodo delete de la clase UploadFileServiceImple ( menos el flash)
			/*Path rootPath = Paths.get(UPLOADS_FOLDER).resolve(cliente.getFoto()).toAbsolutePath();//Obtenemos la ruta absoluta de la imagen.
			File archivo = rootPath.toFile();//Obtenemos el file
			if(archivo.exists() && archivo.canRead()){//Validamos si el archivo existe y que sea accesible
				if (archivo.delete()) {
					flash.addFlashAttribute("info","Foto "+ cliente.getFoto()+ " eliminada con éxito!");
				}
			}*/
			
			if (uploadFileService.delete(cliente.getFoto())) {
				flash.addFlashAttribute("info","Foto "+ cliente.getFoto()+ " eliminada con éxito!");
			}
			
		}
		return "redirect:/listar";
	}
}
