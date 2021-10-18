package br.ufg.inf.fs.ctrl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufg.inf.fs.Messages;
import br.ufg.inf.fs.business.QuartoBusiness;
import br.ufg.inf.fs.entities.Quarto;
import br.ufg.inf.fs.exceptions.QuartoException;

@CrossOrigin
@RestController
@RequestMapping(value="quartos")
public class QuartoCtrl {

	@Autowired
	private QuartoBusiness business;
	
	//@PreAuthorize("hasRole('ADMIN')")
	@GetMapping
	public ResponseEntity<List<Quarto>> findAll(){
		HttpHeaders headers = new HttpHeaders();
		HttpStatus status = HttpStatus.OK;
		List<Quarto> list = new ArrayList<Quarto>();
		try {
			list = business.findAll();
			if(list.size() == 0) {
				headers.add("message", Messages.get("0107"));
			}
		}catch (Exception e) {
			status = HttpStatus.BAD_REQUEST;
			headers.add("message", Messages.get("0002"));
		}
		return new ResponseEntity<List<Quarto>>(list, headers, status);
	}
	
	//@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/paginator")
	public ResponseEntity<Page<Quarto>> paginator(Pageable pageable){
		HttpHeaders headers = new HttpHeaders();
		HttpStatus status = HttpStatus.OK;
		Page<Quarto> list = null;
		try {
			list = business.paginator(pageable);
			if(list.getSize() == 0) {
				headers.add("message", Messages.get("0107"));
			}
		}catch (Exception e) {
			status = HttpStatus.BAD_REQUEST;
			headers.add("message", Messages.get("0002"));
		}
		return new ResponseEntity<Page<Quarto>>(list, headers, status);
	}
	
	
	//@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
	@GetMapping("/{id}")
	public ResponseEntity<Quarto> findById(@PathVariable Integer id){
		Quarto retorno = new Quarto();
		
		HttpHeaders headers = new HttpHeaders();
		HttpStatus status = HttpStatus.OK;
		try {
			retorno = business.findById(id);
			if(retorno == null) {
				headers.add("message", Messages.get("0107"));
			}
		}catch (Exception e) {
			status = HttpStatus.BAD_REQUEST;
			headers.add("message", Messages.get("0002"));
		}
		return new ResponseEntity<Quarto>(retorno, headers, status);
	}
	
	//@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public ResponseEntity<Quarto> insert(@RequestBody Quarto quarto){
		HttpHeaders headers = new HttpHeaders();
		HttpStatus status = HttpStatus.CREATED;
		
		try {
			quarto = business.insert(quarto);
			headers.add("message", Messages.get("0101"));
		} catch (QuartoException e) {
			headers.add("message", Messages.get(e.getMessage()));
			status = HttpStatus.BAD_REQUEST;
		} catch (Exception e) {
			headers.add("message", Messages.get("0102"));
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Quarto>(quarto, headers, status);
	}
	
	//@PreAuthorize("hasRole('ADMIN')")
	@PutMapping
	public ResponseEntity<Quarto> update(@RequestBody Quarto quarto){
		System.out.println(quarto);
		HttpHeaders headers = new HttpHeaders();
		HttpStatus status = HttpStatus.OK;
		
		try {
			quarto = business.update(quarto);
			headers.add("message", Messages.get("0103"));
		} catch (QuartoException e) {
			headers.add("message", Messages.get(e.getMessage()));
			status = HttpStatus.BAD_REQUEST;
		} catch (Exception e) {
			headers.add("message", Messages.get("0104"));
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Quarto>(quarto, headers, status);
		
	}
	
	//@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		HttpHeaders headers = new HttpHeaders();
		HttpStatus status = HttpStatus.NO_CONTENT;
		
		try {
			business.delete(id);
		} catch (Exception e) {
			headers.add("message", Messages.get("0106"));
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Void>(headers, status);
	}
	
}
