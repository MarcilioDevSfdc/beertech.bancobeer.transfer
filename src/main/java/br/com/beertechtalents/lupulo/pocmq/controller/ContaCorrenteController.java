package br.com.beertechtalents.lupulo.pocmq.controller;

import br.com.beertechtalents.lupulo.pocmq.model.ContaCorrente;
import br.com.beertechtalents.lupulo.pocmq.model.TipoTransacao;
import br.com.beertechtalents.lupulo.pocmq.model.Transacao;
import br.com.beertechtalents.lupulo.pocmq.service.ContaCorrenteService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/contaCorrente")
@Api(value = "Endpoints para check de conta corrente")
@RequiredArgsConstructor
@Slf4j
public class ContaCorrenteController {

    final ContaCorrenteService correnteService;

    @ApiOperation(value = "Busca saldo total", nickname = "GET", notes = "Busca todas contas correntes", response = BigDecimal.class, tags = {"conta-corrente",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation", response = BigDecimal.class),
            @ApiResponse(code = 400, message = "Invalid status value")})
    @GetMapping(value = "/buscaTodas")
    public ResponseEntity<List<ContaCorrente>> buscaTodasContasCorrentes() {
        return new ResponseEntity<List<ContaCorrente>>(correnteService.listaTodasContasCorrentes(), HttpStatus.OK);
    }

    @ApiOperation(value = "Busca saldo total", nickname = "GET", notes = "Busca o saldo total", response = BigDecimal.class, tags = {"conta-corrente",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation", response = BigDecimal.class),
            @ApiResponse(code = 400, message = "Invalid status value")})
    @GetMapping(value = "/{idContaCorrente}")
    public ResponseEntity<BigDecimal> getSaldo(@PathVariable(value = "idContaCorrente")  String idContaCorrente) {
        return new ResponseEntity<>(correnteService.buscarSaldo(idContaCorrente), HttpStatus.OK);
    }

    @ApiOperation(value = "Adiciona uma nova conta corrente", nickname = "POST", notes = "", tags = {"conta-corrente",})
    @ApiResponses(value = {
            @ApiResponse(code = 405, message = "Invalid input")})
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> novaOperacao(@ApiParam(value = "Nova conta corrente", required = true) @RequestBody ContaCorrente body) {

        return new ResponseEntity<>(correnteService.salvarContaCorrente(body), HttpStatus.OK);
    }
}
