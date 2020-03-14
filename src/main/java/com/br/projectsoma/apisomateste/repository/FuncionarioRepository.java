package com.br.projectsoma.apisomateste.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.projectsoma.apisomateste.models.Funcionario;

public interface FuncionarioRepository extends JpaRepository<Funcionario,Long>{
		//Funcionario findById(long id);
	
	
}
