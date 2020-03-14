package com.br.projectsoma.apisomateste.resources;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.projectsoma.apisomateste.models.Extrato;
import com.br.projectsoma.apisomateste.models.Funcionario;
import com.br.projectsoma.apisomateste.repository.ExtratoRepository;
import com.br.projectsoma.apisomateste.repository.FuncionarioRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/api")
@Api(value = "API SOMA TESTE")
@CrossOrigin(origins = "*")
public class ControllersResource {

	@Autowired
	FuncionarioRepository funcionarioRepository;
	@Autowired
	ExtratoRepository extratoRepository;

	@GetMapping("/funcionarios")
	@ApiOperation(value = "Este método retorna uma lista de todos os funcionários cadastrados no banco de dados")
	public List<Funcionario> listaFuncionarios() {
		return funcionarioRepository.findAll();
	}

	@GetMapping("/funcionario/{id}")
	@ApiOperation(value = "Este método retorna um funcionario apenas de acordo com o id passado")
	public Optional<Funcionario> funcionarioUnico(@PathVariable(value = "id") long id) {
		return funcionarioRepository.findById(id);
	}

	@PostMapping("/funcionario")
	@ApiOperation(value = "Este método salva um funcionario no banco de dados")
	public Funcionario salvarFuncionario(@RequestBody Funcionario funcionario) {
		return funcionarioRepository.save(funcionario);
	}

	@DeleteMapping("/funcionario")
	@ApiOperation(value = "Este método deleta um funcionario no banco de dados")
	public void deletaFuncionario(@RequestBody Funcionario funcionario) {
		funcionarioRepository.delete(funcionario);
	}

	@PutMapping("/funcionario")
	@ApiOperation(value = "Este método atualiza os dados de um funcionario no banco de dados")
	public Funcionario atualizaFuncionario(@RequestBody Funcionario funcionario) {
		return funcionarioRepository.save(funcionario);
	}

	@GetMapping("/extratos")
	@ApiOperation(value = "Este método retorna uma lista de todos os extratos cadastrados no banco de dados")
	public List<Extrato> listaExtratos() {
		return extratoRepository.findAll();
	}

	@GetMapping("/extrato/{id}")
	@ApiOperation(value = "Este método retorna um extratos apenas de acordo com o id passado")
	public Optional<Extrato> extratoUnico(@PathVariable(value = "id") long id) {
		return extratoRepository.findById(id);
	}

	@PostMapping("/extrato")
	@ApiOperation(value = "Este método salva um extrato no banco de dados")
	public Extrato salvarExtrato(@RequestBody Extrato extrato) {
		return extratoRepository.save(extrato);
	}

	@DeleteMapping("/extrato")
	@ApiOperation(value = "Este método deleta um extrato no banco de dados")
	public void deletaExtrato(@RequestBody Extrato extrato) {
		extratoRepository.delete(extrato);
	}

	@PutMapping("/extrato")
	@ApiOperation(value = "Este método atualiza os dados de um extrato no banco de dados")
	public Extrato atualizaExtrato(@RequestBody Extrato extrato) {
		return extratoRepository.save(extrato);
	}

}
