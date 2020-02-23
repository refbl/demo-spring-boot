package br.com.demo.controller;

import br.com.demo.controller.dto.TopicoDto;
import br.com.demo.model.Curso;
import br.com.demo.model.Topico;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class TopicosController {

    @RequestMapping("/topicos")
    public List<TopicoDto> lista(){
        Topico topico = new Topico("Duvida",
                "Duvida com Spring",
                new Curso("Spring", "Programacao"));

        return TopicoDto.converter(Arrays.asList(topico, topico, topico));
    }
}
