package com.bolsadeideas.springboot.app.models.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
//import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
//import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;



@Entity // Para anotar que una clase POJO con atributs que están mapeados a una tabla,
		// es una entidad de JPA o hibenate se anota con Entity.
@Table(name = "clientes") // Si queremos que nuestra clase entity se llame igual que el nombre de la tabla
							// de la bbdd. Nombre de tablas en minuscula y terminan en plural. Este va a ser
							// el nombre que se llame la tabla
public class Cliente implements Serializable {// Serializacion: Proceso de convertir un objecto en una secuencia de
												// bytes para almacenarlo a la memoria o a la bbdd o a un json o a
												// sesiones http.
												// Hacerlo en las clases Entity con JPA y Hibernate

	

	@Id // Anotación clave primaria
	@GeneratedValue(strategy = GenerationType.IDENTITY) // Motor de creación de la clave, en este caso autocremental
	private Long id;

	// @Column(name="NOMBRE_CLIENTE",) //Para personalizar un campo dentro de la
	// bbdd y el tipo de dato
	
	@NotEmpty //Que no esté vacio. Solamente con String
	//@Size(min=4,max=12)
	private String nombre;
	@NotEmpty
	private String apellido;
	@NotEmpty
	@Email //Para que el formato sea de tipo Email
	private String email;

	@Column(name = "create_at")//Nombre de la columna de la base de datos
	@Temporal(TemporalType.DATE) // Para fechas. Indicar el formato que se va a guardar en la bbdd
	@DateTimeFormat(pattern="yyyy-MM-dd")//Formato de validación de fecha
	@NotNull //No aplica a String
	
	private Date createAt;
	
	private String foto;
	
	private static final long serialVersionUID = 1L;

	/*@PrePersist //Anotación para que se ejecute antes de que se guarde en la base de datos y para que forme parte del entity manager. Justo antes de invocar el metodo persist
	public void prePersist() {//Antes de que se guarda en la base de datos, para meterle la fecha de hoy
		createAt = new Date();
	}*/ //COmentamos la fecha ya que la vamos a añadir nosotros en el formulario
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	
	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}
