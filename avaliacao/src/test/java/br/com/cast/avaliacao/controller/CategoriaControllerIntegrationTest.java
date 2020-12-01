package br.com.cast.avaliacao.controller;

import br.com.cast.avaliacao.service.CategoriaService;
import br.com.cast.avaliacao.service.dto.CategoriaDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CategoriaControllerIntegrationTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private CategoriaService categoriaService;

    private MockMvc mvc;

    private final String URL = "/api/categoria/";
    private final int PAGE = 0;
    private final int SIZE = 20;
    private final String PRINTER_COMMAND_LANGUAGE = "Printer Command Language";
    private final String DELPHI = "Delphi";

    private static byte[] convertObjectToJsonBytes(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        JavaTimeModule module = new JavaTimeModule();
        mapper.registerModule(module);

        return mapper.writeValueAsBytes(object);
    }

    @Before
    public void setUp() {
        this.mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void test01_saveSuccess() throws Exception {
        CategoriaDto categoriaDto = new CategoriaDto();
        categoriaDto.setDescricao(PRINTER_COMMAND_LANGUAGE);

        this.mvc.perform(post(URL)
                .content(convertObjectToJsonBytes(categoriaDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void test02_updateSuccess() throws Exception {
        CategoriaDto categoriaDto = categoriaService.findByDescricaoContainingIgnoreCase(PRINTER_COMMAND_LANGUAGE, PageRequest.of(PAGE, SIZE)).getContent().get(0);
        categoriaDto.setDescricao(DELPHI);

        this.mvc.perform(put(URL + categoriaDto.getCodigo())
                .content(convertObjectToJsonBytes(categoriaDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void test03_findAllSuccess() throws Exception {
        this.mvc.perform(get(URL + "/all"))
                .andExpect(status().isOk());
    }

    @Test
    public void test04_findByDescricaoSuccess() throws Exception {
        this.mvc.perform(get(URL + "?descricao= " + DELPHI + "&page=" + PAGE + "&size="+ SIZE))
                .andExpect(status().isOk());
    }

    @Test
    public void test05_findByIdSuccess() throws Exception {
        CategoriaDto categoriaDto = categoriaService.findByDescricaoContainingIgnoreCase(DELPHI, PageRequest.of(PAGE, SIZE)).getContent().get(0);
        this.mvc.perform(get(URL + categoriaDto.getCodigo()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("descricao", equalTo(DELPHI)));
    }

    @Test
    public void test06_findByIdNoContent() throws Exception {
        this.mvc.perform(get(URL + "/9999999"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void test07_deleteByIdSuccess() throws Exception {
        CategoriaDto categoriaDto = categoriaService.findByDescricaoContainingIgnoreCase(DELPHI, PageRequest.of(PAGE, SIZE)).getContent().get(0);
        this.mvc.perform(delete(URL + categoriaDto.getCodigo()))
                .andExpect(status().isNoContent());
    }
}
