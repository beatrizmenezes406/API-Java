package com.projetos.api.java.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.projetos.api.java.model.Cliente;
import com.projetos.api.java.repository.ClienteRepository;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/clientes")
public class ClienteController {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@GetMapping
	public List<Cliente> listarClientes() {
		return clienteRepository.findAll();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cliente adicionarcliente(@RequestBody Cliente cliente) {
		return clienteRepository.save(cliente);
	}
	
	@DeleteMapping(value = "/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void removercliente(@PathVariable Long id) {
		 clienteRepository.deleteById(id);
		 
		
	}
	
	@PutMapping
	@ResponseStatus(HttpStatus.OK)
	public void atualizarcliente(@RequestBody Cliente cliente) {
		Cliente clienteToUpdate = clienteRepository.getById(cliente.getId());
		clienteToUpdate.setNome(cliente.getNome());
		clienteRepository.save(clienteToUpdate);
	}

}
