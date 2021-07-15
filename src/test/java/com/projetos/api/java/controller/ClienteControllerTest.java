package com.projetos.api.java.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaTypeEditor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projetos.api.java.model.Cliente;
import com.projetos.api.java.repository.ClienteRepository;

import static org.hamcrest.CoreMatchers.any;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.awt.PageAttributes.MediaType;
import java.lang.reflect.Type;
import java.util.Optional;

import io.restassured.http.ContentType;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest
public class ClienteControllerTest {
	
	private MockMvc mockMvc;
	private ObjectMapper objectMapper;
	
	@Autowired
	private ClienteController clienteController;
	
	@MockBean
	private ClienteRepository clienteRepository;
	
	@BeforeEach
	public void setup() {
		objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders
                .standaloneSetup(clienteController)
                .build();
	}
	
	@Test
	public void deveRetornarOk_QuandoBuscaClientes() throws Exception {
		mockMvc.perform(get("/clientes"))
        .andExpect(content().contentType(org.springframework.http.MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
	}
	
	@Test
	public void deveRetornarOk_QuandoBuscaClientePorId() throws Exception {
		Cliente cliente = new Cliente();
		cliente.setId((long) 1);
		cliente.setNome("Teste");

		
		when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
		
		mockMvc.perform(get("/clientes/1"))
	        .andExpect(content().contentType(org.springframework.http.MediaType.APPLICATION_JSON))
	        .andExpect(status().isOk())
	        .andExpect(jsonPath("$.id", is(1)))
	        .andExpect(jsonPath("$.nome", is("Teste")));
		
		 verify(clienteRepository, times(1)).findById((long) 1);
	}
	
	@Test
	public void deveRetornarNotFound_QuandoBuscaClientePorIdInexistente() throws Exception {
		mockMvc.perform(get("/clientes/2")).andExpect(status().isNotFound());
	}
	
	
	
	@Test
	public void deveRetornarCreate_QuandoAdicionaCliente() throws Exception{

		Cliente cliente = new Cliente();
		cliente.setId((long) 1);
		cliente.setNome("Teste");

		when(clienteRepository.save(cliente)).thenReturn(cliente);

        mockMvc.perform(post("/clientes")
                .content(objectMapper.writeValueAsString(cliente))
                .header(HttpHeaders.CONTENT_TYPE, org.springframework.http.MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.nome", is("Teste")));

        verify(clienteRepository, times(1)).save(cliente);
	}
	
	@Test
    public void deveRetornarOk_QuandoAtualizaCliente() throws Exception {

        Cliente dto = new Cliente();
        dto.setNome("TesteAtualizar");

        Cliente cliente = new Cliente();
        cliente.setId((long) 1);
        cliente.setNome("Teste");

        when(clienteRepository.findById((long) 1)).thenReturn(Optional.of(cliente));
        when(clienteRepository.save(cliente)).thenReturn(cliente);

        mockMvc.perform(put("/clientes/1")
                .content(objectMapper.writeValueAsString(dto))
                .header(HttpHeaders.CONTENT_TYPE, org.springframework.http.MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
	
	@Test
	public void deveRetornarNotFound_QuandoAtualizaClienteInexistente() throws Exception {
		Cliente dto = new Cliente();
        dto.setNome("TesteAtualizar");
        
		mockMvc.perform(put("/clientes/2")
				.content(objectMapper.writeValueAsString(dto))
		        .header(HttpHeaders.CONTENT_TYPE, org.springframework.http.MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}
	
	@Test
    public void deveRetornarOk_QuandoDeletaCliente() throws Exception {

        Cliente cliente = new Cliente();
        cliente.setId((long) 1);

        when(clienteRepository.findById((long) 1)).thenReturn(Optional.of(cliente));

        mockMvc.perform(delete("/clientes/1"))
                .andExpect(status().isOk());

        verify(clienteRepository, times(1)).deleteById((long) 1);
    }
	
	@Test
	public void deveRetornarNotFound_QuandoDeletaClienteInexistente() throws Exception {
		mockMvc.perform(delete("/clientes/2")).andExpect(status().isNotFound());
	}

}
