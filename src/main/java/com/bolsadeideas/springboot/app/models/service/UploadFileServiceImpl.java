package com.bolsadeideas.springboot.app.models.service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.slf4j.Logger;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

//Clase para reutilizar código que teniamos en el controlador. Lo anotamos como @Service
@Service //Con Service registramos este beans (objeto) en el contenedor del framework, por lo que de esta forma podemos inyectarlo (en el controlador) con @Autowired
public class UploadFileServiceImpl implements IUploadFileService {
	
	private final Logger log = org.slf4j.LoggerFactory.getLogger(getClass()); //Para mostrar log por la consola
	
	private final static String UPLOADS_FOLDER = "uploads";//Constante de la carpeta donde guardamos las imágenes del proyectos.
	
	@Override
	public Resource load(String filename) throws MalformedURLException {//Si metemos una exceción en el service, tambien tiene que estar en la interfaz
		
		Path pathFoto = getPath(filename);//getPath -> Metodo creado anteriormente
		log.info("pathFoto: " + pathFoto);
		Resource recurso = null;

		recurso = new UrlResource(pathFoto.toUri());
		if (!recurso.exists() || !recurso.isReadable()) {
			throw new RuntimeException("Error: no se puede cargar la imagen: " + pathFoto.toString());
		}

		return recurso;
	}

	@Override
	public String copy(MultipartFile file) throws IOException{
		String uniqueFilename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();//Añadimos un identificador ramndon para que el nombre del archivo sea único
		Path rootPath = getPath(uniqueFilename);//De esta forma guardamos la imagenes dentro de los archivos del proyecto, dentro de uploads
		
		//Path rootAbsolutePath = rootPath.toAbsolutePath();//Obtenemos la ruta absoluta. Como el metodo getPath retorna un absoluto, podemos quitar esta línea
		log.info("rootPath: "+ rootPath); //Path relativo
		//log.info("rootAbsolutePath: "+ rootAbsolutePath);//Path absoluto. Lo comentamos porque ya lo tenemos
	
		Files.copy(file.getInputStream(), rootPath);//Obtenemos el string de entrada de nuestra imagen y la ruya absoluta, para poder copiarla a la nueva ruta
		
		return uniqueFilename;
	}

	@Override
	public boolean delete(String filename) {
		Path rootPath = getPath(filename);//Obtenemos la ruta absoluta de la imagen.
		File archivo = rootPath.toFile();//Obtenemos el file
		if(archivo.exists() && archivo.canRead()){//Validamos si el archivo existe y que sea accesible
			if (archivo.delete()) {
				return true;
			}
		}
		return false;
	}
	
	//Metodo que teniamos en el controlado para recuperar la ruta y reutilizar código.
	public Path getPath(String filename) {
		return Paths.get(UPLOADS_FOLDER).resolve(filename).toAbsolutePath(); //Retorna el path completo. Con el resolve, nos permite añadir el nombre del archivo y lo convierte en absoluto
	}

	//Metodo que borra el directorio
	@Override
	public void deleteAll() {
		FileSystemUtils.deleteRecursively(Paths.get(UPLOADS_FOLDER).toFile());
	}

	//Metodo que crea el directorio
	@Override
	public void init() throws IOException {
		Files.createDirectory(Paths.get(UPLOADS_FOLDER));
	}
}
