package com.bolsadeideas.springboot.app.models.service;

import java.io.IOException;
import java.net.MalformedURLException;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface IUploadFileService {
	
	public Resource load(String filename) throws MalformedURLException;//Metodo para mostrar la imagen
	
	public String copy(MultipartFile file) throws IOException;//Metodo copiar la imagen
	
	public boolean delete(String filename);//Metodo para saber si se ha borrado la imagen y mandar la información al flash del controlador
	
	public void deleteAll();//Metodo para borrar todo el directorio Upload
	
	public void init() throws IOException;//Metodo para crear todo el directorio Upload

}
