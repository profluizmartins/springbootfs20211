package br.ufg.inf.fs.business;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.ufg.inf.fs.entities.Quarto;
import br.ufg.inf.fs.exceptions.QuartoException;
import br.ufg.inf.fs.repositories.QuartoRepository;

@Service
public class QuartoBusiness {
	
	@Autowired
	private QuartoRepository repository;
	
	public List<Quarto> findAll(){
		return repository.findAll();		
	}
	
	
	public Page<Quarto> paginator(Pageable pageable){
		return repository.findAll(pageable);		
	}
	
		
	public Quarto findById(Integer id) {
		Optional<Quarto> retorno = repository.findById(id);
		return retorno.get();
	}
	
	public Quarto insert(Quarto quarto) throws QuartoException {
		this.validaQuarto(quarto);
		return repository.save(quarto);
	}
	
	public Quarto update(Quarto quarto) throws QuartoException {
		this.validaQuarto(quarto);
		return repository.save(quarto);
	}
	
	public void delete(Integer id) {
		repository.deleteById(id);
	}
	
	
	private void validaQuarto(Quarto quarto) throws QuartoException {
		if(quarto.getNrQuarto() == null || quarto.getNrQuarto() < 0) {
			throw new QuartoException("0209");
		}
		if(quarto.getQtdLeito() == null || quarto.getQtdLeito()  < 1) {
			throw new QuartoException("0210");
		}

		if(quarto.getPrDiaria() == null || quarto.getPrDiaria() < 0) {
			throw new QuartoException("0211");
		}
		
		if(quarto.getCategoriaQuarto() == null) {
			throw new QuartoException("0212");
		}
		
	}
	
}
