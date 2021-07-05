package com.projetos.api.java;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projetos.api.java.controller.ClienteController;
import com.projetos.api.java.model.Cliente;
import com.projetos.api.java.repository.ClienteRepository;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = ClienteController.class)
class ApiJavaApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClienteRepository clienteRepository;

    @Test
    public void listarClientes() throws Exception {
        Cliente cliente = new Cliente("TesteListar");
        List<Cliente> clienteList = List.of(cliente);
        when(clienteRepository.findAll()).thenReturn(clienteList);
        this.mockMvc.perform(get("/clientes"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("TesteListar")));
    }
    
    @Test
    public void adicionarClientes() throws Exception {
    	
    	Cliente cliente = new Cliente("TesteAdicionar");

        mockMvc.perform(post("/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(cliente)))
                .andExpect(status().isCreated());
    }
    
    @Test
    public void removerClientes() throws Exception {
    	
    	Cliente cliente = new Cliente("TesteRemover");

        mockMvc.perform(delete("/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(cliente)))
                .andExpect(content().string(containsString("Delete Sucess!")));
    }
    
    private String toJson(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}


