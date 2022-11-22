package com.bolsadeideas.springboot.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.bolsadeideas.springboot.app.models.service.IUploadFileService;

@SpringBootApplication
public class SpringBootDataJpaApplication implements CommandLineRunner{//Para Inicializar de forma automatica algo en nuestro proyecto. Cada vez que se inicie/ arranque el proyecto,se cree la carpeta upload.
																		//Implementamos una interfaz, implementamos el metodo run

	//Inyectamos la clase service UploadFileServiceImple
	@Autowired
	IUploadFileService uploadFileService;
	
	public static void main(String[] args) {
		SpringApplication.run(SpringBootDataJpaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception { //Le añadimos los metodos del services de borrar el directorio y crearlo
		uploadFileService.deleteAll();
		uploadFileService.init();
	}

}
