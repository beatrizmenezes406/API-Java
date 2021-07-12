package com.projetos.api.java.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	
	@GetMapping(path = "/{id}") 
	public ResponseEntity listarClientePorId(@PathVariable Long id) {
		return clienteRepository.findById(id)
				.map(record -> ResponseEntity.ok().body(record))
				.orElse(ResponseEntity.notFound().build());
		
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cliente adicionarcliente(@RequestBody Cliente cliente) {
		return clienteRepository.save(cliente);
	}
	
	@DeleteMapping(path = "/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity  removercliente(@PathVariable Long id) {
		return clienteRepository.findById(id)
				.map(record -> {
					clienteRepository.deleteById(id);
		            return ResponseEntity.ok().build();
				}).orElse(ResponseEntity.notFound().build());
		
	}
	
	@PutMapping(path = "/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity atualizarcliente(@PathVariable("id") long id, @RequestBody Cliente cliente) {
		return clienteRepository.findById(id)
				.map(record -> {
					record.setNome(cliente.getNome());
					record.setEmail(cliente.getEmail());
		            record.setTelefone(cliente.getTelefone());
		            record.setCpfcnpj(cliente.getCpfcnpj());
		            record.setEndereco(cliente.getEndereco());
		            Cliente updated = clienteRepository.save(record);
		            return ResponseEntity.ok().body(updated);
				}).orElse(ResponseEntity.notFound().build());
	}

}
