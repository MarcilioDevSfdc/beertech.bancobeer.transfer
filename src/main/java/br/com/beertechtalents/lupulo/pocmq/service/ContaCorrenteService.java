package br.com.beertechtalents.lupulo.pocmq.service;

import br.com.beertechtalents.lupulo.pocmq.model.ContaCorrente;
import br.com.beertechtalents.lupulo.pocmq.repository.ContaCorrenteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ContaCorrenteService {

    final ContaCorrenteRepository contaCorrenteRepository;

    public BigDecimal buscarSaldo(String idContaCorrente){
        return contaCorrenteRepository.somaSaldo(UUID.fromString(idContaCorrente));
    }

    public String salvarContaCorrente(ContaCorrente contaCorrente){
        return contaCorrenteRepository.saveAndFlush(contaCorrente).getIdContaCorrente().toString();
    }

    public void atualizarSaldoContaCorrente(String idContaCorrente, BigDecimal valor){
        System.out.println("idContaCorrente: " + idContaCorrente);
        ContaCorrente contaCorrente = contaCorrenteRepository.findContaCorrenteByIdContaCorrente(UUID.fromString(idContaCorrente));


        contaCorrente.setSaldo(contaCorrente.getSaldo().add(valor));
        contaCorrenteRepository.save(contaCorrente);
    }

    public List<ContaCorrente> listaTodasContasCorrentes(){
        return contaCorrenteRepository.findAll();
    }
}
