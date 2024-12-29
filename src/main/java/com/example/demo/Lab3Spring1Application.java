package com.example.demo;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;


@OpenAPIDefinition (
info = @Info(

title = "Lab4",
contact = @Contact(

name = "Kryvenko",
email = "bogdan0706042@gmail.com"
),

description = "RESTful вебсервіси",
version = "1.1",
license = @License(name = "Apache 2.0", url =

"https://www.apache.org/licenses/LICENSE-2.0.html")

),
servers = {

@Server(url = "http://localhost:8080", description = "test server"),
}

)
@SpringBootApplication
public class Lab3Spring1Application {

	public static void main(String[] args) {
		SpringApplication.run(Lab3Spring1Application.class, args);
	}

}
