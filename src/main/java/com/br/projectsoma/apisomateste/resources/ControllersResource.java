package com.br.projectsoma.apisomateste.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.projectsoma.apisomateste.error.ResourceNotFoundException;
import com.br.projectsoma.apisomateste.models.Extrato;
import com.br.projectsoma.apisomateste.models.Funcionario;
import com.br.projectsoma.apisomateste.repository.ExtratoRepository;
import com.br.projectsoma.apisomateste.repository.FuncionarioRepository;
import com.br.projectsoma.apisomateste.util.ValorTotal;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/api")
@Api(value = "API SOMA TESTE")
@CrossOrigin(origins = "*")
public class ControllersResource {
	double somavalorTotal;
	@Autowired
	FuncionarioRepository funcionarioRepository;
	@Autowired
	ExtratoRepository extratoRepository;

	@GetMapping("/funcionarios")
	@ApiOperation(value = "Este método retorna uma lista de todos os funcionários cadastrados no banco de dados")
	public List<Funcionario> listaFuncionarios() {
		List<Funcionario> funcionarios = new ArrayList();
		funcionarios = funcionarioRepository.findAll();
		if(funcionarios.isEmpty() == true) {
			throw new ResourceNotFoundException("Nenhum Funcionario Encontrado");
		}else {
			return funcionarios;
		}	
	}

	@GetMapping("/funcionario/{id}")
	@ApiOperation(value = "Este método retorna um funcionario apenas de acordo com o id passado")
	public ResponseEntity<?> funcionarioUnico(@PathVariable(value = "id") long id) {
		verificarExistenciaFuncionario(id);
		Funcionario funcionario = funcionarioRepository.findById(id);
		return new ResponseEntity<>(funcionario, HttpStatus.OK);
	}

	@PostMapping("/funcionario")
	@ApiOperation(value = "Este método salva um funcionario no banco de dados")
	public ResponseEntity<?> salvarFuncionario(@RequestBody Funcionario funcionario) {
		funcionarioRepository.save(funcionario);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@DeleteMapping("/funcionario")
	@PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Este método deleta um funcionario no banco de dados")
	public ResponseEntity<?> deletaFuncionario(@RequestBody Funcionario funcionario) {
		verificarExistenciaFuncionario(funcionario.getId());
		funcionarioRepository.delete(funcionario);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PutMapping("/funcionario")
	@ApiOperation(value = "Este método atualiza os dados de um funcionario no banco de dados")
	public ResponseEntity<?> atualizaFuncionario(@RequestBody Funcionario funcionario) {
		verificarExistenciaFuncionario(funcionario.getId());
		funcionarioRepository.save(funcionario);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("/extratos")
	@ApiOperation(value = "Este método retorna uma lista de todos os extratos cadastrados no banco de dados")
	public List<Extrato> listaExtratos() {
		List<Extrato> extratos = new ArrayList();
		extratos = extratoRepository.findAll();
		if(extratos.isEmpty() == true) {
			throw new ResourceNotFoundException("Nenhum Extrato Encontrado");
		}else {
			for(int i = 0; i < extratos.size(); i++) {
				somavalorTotal += (extratos.get(i).getValor());
			}
			return extratos;
		}	

	}
	
	@GetMapping("/extratos-valor-total")
	@ApiOperation(value = "Este método retorna o valor total da soma de despesas e receitas")
	public double valorTotal() {
		listaExtratos();
		return somavalorTotal;
	}


	@GetMapping("/extrato/{id}")
	@ApiOperation(value = "Este método retorna um extratos apenas de acordo com o id passado")
	public ResponseEntity<?> extratoUnico(@PathVariable(value = "id") long id) {
		verificarExistenciaExtrato(id);
		Extrato extrato = extratoRepository.findById(id);
		return new ResponseEntity<>(extrato, HttpStatus.OK);
	}

	@PostMapping("/extrato")
	@ApiOperation(value = "Este método salva um extrato no banco de dados")
	public ResponseEntity<?> salvarExtrato(@RequestBody Extrato extrato) {
		extratoRepository.save(extrato);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@DeleteMapping("/extrato")
	@ApiOperation(value = "Este método deleta um extrato no banco de dados")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> deletaExtrato(@RequestBody Extrato extrato) {
		verificarExistenciaExtrato(extrato.getId());
		extratoRepository.delete(extrato);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PutMapping("/extrato")
	@ApiOperation(value = "Este método atualiza os dados de um extrato no banco de dados")
	public ResponseEntity<?> atualizaExtrato(@RequestBody Extrato extrato) {
		verificarExistenciaExtrato(extrato.getId());
		extratoRepository.save(extrato);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	//
	private void verificarExistenciaFuncionario(Long id) {
		if(!funcionarioRepository.findById(id).isPresent())
			throw new ResourceNotFoundException("Nenhum Funcionario encontrado com o ID: " + id);
	}
	
	private void verificarExistenciaExtrato(Long id) {
		if(!extratoRepository.findById(id).isPresent())
			throw new ResourceNotFoundException("Nenhum Extrato encontrado com o ID: " + id);
	}
	
}
