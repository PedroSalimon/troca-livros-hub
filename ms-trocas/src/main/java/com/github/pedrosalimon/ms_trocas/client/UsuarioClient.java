package com.github.pedrosalimon.ms_trocas.client;

import com.github.pedrosalimon.ms_trocas.dto.ConfirmacaoTrocaRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("ms-usuarios")
public interface UsuarioClient {

    @RequestMapping(method = RequestMethod.PUT,
            value = "/api/v1/livros/troca/confirmada")
    void confirmarTroca(@RequestBody ConfirmacaoTrocaRequestDto requestDto);
}
