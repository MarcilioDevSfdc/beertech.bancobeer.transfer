package br.com.beertechtalents.lupulo.pocmq.service;

import br.com.beertechtalents.lupulo.pocmq.model.TipoTransacao;
import br.com.beertechtalents.lupulo.pocmq.model.Transacao;
import br.com.beertechtalents.lupulo.pocmq.repository.TransacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransacaoService {

    final TransacaoRepository transacaoRepository;
    final ContaCorrenteService correnteService;

    public void salvarTransacao(Transacao transacao){

        transacaoRepository.save(transacao);
        correnteService.atualizarSaldoContaCorrente(transacao.getContaCorrenteDestino().toString(), transacao.getValor());
    }

    public void tranferenciaEntreContas(String contaOrigem, String contaDestino, BigDecimal  valor){
        Transacao transferencia = new Transacao();
        transferencia.setValor(valor);
        transferencia.setContaCorrenteOrigem(UUID.fromString(contaOrigem));
        transferencia.setContaCorrenteDestino(UUID.fromString(contaDestino));
        transferencia.setTipo(TipoTransacao.TRANSFERENCIA_CONTAS);
        transacaoRepository.save(transferencia);

        correnteService.atualizarSaldoContaCorrente(contaOrigem, transferencia.getValor().negate());
        correnteService.atualizarSaldoContaCorrente(contaDestino, transferencia.getValor());
    }


}
