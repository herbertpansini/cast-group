package br.com.cast.avaliacao.controller;

import br.com.cast.avaliacao.model.Categoria;
import br.com.cast.avaliacao.service.CategoriaService;
import br.com.cast.avaliacao.service.CursoService;
import br.com.cast.avaliacao.service.dto.CategoriaDto;
import br.com.cast.avaliacao.service.dto.CursoDto;
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
import java.time.LocalDate;

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
public class CursoControllerIntegrationTest {
    @Autowired
    private WebApplicationContext context;

    @Autowired
    private CursoService cursoService;

    @Autowired
    private CategoriaService categoriaService;

    private MockMvc mvc;

    private final String URL = "/api/curso/";
    private final int PAGE = 0;
    private final int SIZE = 20;
    private final String INGLES = "Inglês";
    private final String ESPANHOL = "Espanhol";

    private static byte[] convertObjectToJsonBytes(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        JavaTimeModule module = new JavaTimeModule();
        mapper.registerModule(module);

        return mapper.writeValueAsBytes(object);
    }

    private CursoDto getCursoDto() {
        CursoDto cursoDto = new CursoDto();
        cursoDto.setAssunto(INGLES);
        cursoDto.setDataInicio(LocalDate.of(2021, 3, 1));
        cursoDto.setDataTermino(LocalDate.of(2021, 3, 31));
        cursoDto.setQuantidadeAlunosPorTurma(null);
        cursoDto.setCategoria( this.categoriaService.findById(1L) );
        return cursoDto;
    }

    @Before
    public void setUp() {
        this.mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void test01_saveSuccess() throws Exception {
        this.mvc.perform(post(URL)
                .content(convertObjectToJsonBytes(this.getCursoDto()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void test02_saveFailedWhenDataInicioMenorQueDataAtual() throws Exception {
        CursoDto cursoDto = this.getCursoDto();
        cursoDto.setDataInicio(LocalDate.of(2020, 11, 26));

        this.mvc.perform(post(URL)
                .content(convertObjectToJsonBytes(cursoDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void test03_saveFailedWhenPeriodoJaCadastrado() throws Exception {
        CursoDto cursoDto = this.getCursoDto();
        cursoDto.setDataInicio(LocalDate.of(2021, 3, 20));
        cursoDto.setDataTermino(LocalDate.of(2021, 3, 30));

        this.mvc.perform(post(URL)
                .content(convertObjectToJsonBytes(cursoDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void test04_updateSuccess() throws Exception {
        CursoDto cursoDto = this.cursoService.findByAssuntoContainingIgnoreCase(INGLES, PageRequest.of(PAGE, SIZE)).getContent().get(0);
        cursoDto.setAssunto(ESPANHOL);

        this.mvc.perform(put(URL + cursoDto.getCodigo())
                .content(convertObjectToJsonBytes(cursoDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void test05_findByAssuntoSuccess() throws Exception {
        this.mvc.perform(get(URL + "?assunto=" + ESPANHOL + "&page=" + PAGE + "&size=" + SIZE))
                .andExpect(status().isOk());
    }

    @Test
    public void test06_findByIdSuccess() throws Exception {
        CursoDto cursoDto = this.cursoService.findByAssuntoContainingIgnoreCase(ESPANHOL, PageRequest.of(PAGE, SIZE)).getContent().get(0);

        this.mvc.perform(get(URL + cursoDto.getCodigo()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("assunto", equalTo(ESPANHOL)));
    }

    @Test
    public void test07_findByIdNoContent() throws Exception {
        this.mvc.perform(get(URL + "/9999999"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void test08_deleteByIdSuccess() throws Exception {
        CursoDto cursoDto = this.cursoService.findByAssuntoContainingIgnoreCase(ESPANHOL, PageRequest.of(PAGE, SIZE)).getContent().get(0);

        this.mvc.perform(delete(URL + cursoDto.getCodigo()))
                .andExpect(status().isNoContent());
    }
}
