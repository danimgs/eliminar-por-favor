package com.bolsadeideas.springboot.app;

import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//Esta clase de configuración es necesaria para acceder a las imagenes de un directorio externo, como un directorio de recursos, para agregar recursos.
//Se va a comentar la clase ya que no la vamos a necesitar. Vamos a realizar la carga de imagenes con un metodo handler desde el controlador que se encarga de recibir 
	//la imagen como parametro en un Path variable y la convierte en un rescuros en un Input Stream y la carga en la respuesta hhyp, como un Response Entity
@Configuration //Se marca como clase de configuración. Implementamos la interfaz WebMvcConfigurer y sobreescribimos un metodo addResourceHandlers, para agregar recursos
public class MvcConfig implements WebMvcConfigurer{
	//esta implementación se realiza en versiones 2.0 en adelante de springboot. Anteriormente en otras versiones (1.5) extendiende de WebMvcConfigurerAdapter, pero está obsoleta.

	private final Logger log = LoggerFactory.getLogger(getClass());
	/*@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {//Añadimos dicho metodo para agregar recursos a nuestro proyecto. Lo añadimos desde btn drcho -> Source -> Owverwrite....
		// TODO Auto-generated method stub
		WebMvcConfigurer.super.addResourceHandlers(registry);
		
		String resourcePath = Paths.get("uploads").toAbsolutePath().toUri().toString();//Le añadimos la nueva ruta absoluta. Le añadimos el URI para incluir el esquema file
		log.info(resourcePath);//debug del resource Path
		
		registry.addResourceHandler("/uploads/**") //usamos registry, para registrar nuestra nueva ruta. Lo suyo es usar la misma que en la plantilla Ver.html. Añadimos ** para mapear al nombre de la imagen
													//para que se pueda cargar, es decir el valor de la variable
		//.addResourceLocations("file:/C:/Temp/uploads/");//Despues añadimos la ubicación, del directorio físico. Tiene que ser la misma que la que tenemos en el controlador. Comentamos ya que le vamos a meter la ruta absoluta
		.addResourceLocations(resourcePath);
		//.addResourceLocations("file:opt/uploads/");//Para linux
		
	
	}*/

	
	
}
