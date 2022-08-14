package scs.covid.covorsicht.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import scs.covid.covorsicht.models.City;
import scs.covid.covorsicht.models.CityCases;
import scs.covid.covorsicht.repositories.CityCasesRepository;

@Service
@Transactional
public class CityCasesService {

	@Autowired
	CityCasesRepository cityCasesRepository;

	public CityCases save(CityCases cityCases) {

		return cityCasesRepository.save(cityCases);
	}

	public CityCases update(CityCases cityCases) {

		return cityCasesRepository.saveAndFlush(cityCases);
	}

	public CityCases get(Long id) {
		return cityCasesRepository.findById(id).orElse(null);
	}

	public List<CityCases> getAll() {
		return cityCasesRepository.findAll();
	}

	public List<CityCases> getCurent(long id, int limit) {

		return cityCasesRepository.getCurrent(id, PageRequest.of(0, 1));
	}

	public CityCases getSingle(City c) {

		return cityCasesRepository.findTopByCityOrderByDateDesc(c);
	}

	public boolean getCitySituation(City c) {

		CityCases cityCases = cityCasesRepository.findTopByCityOrderByDateDesc(c);

		if (cityCases.isAlert())
			return true;
		else
			return false;

	}

	public List<CityCases> getAllByCityDateDesc(City c) {

		return cityCasesRepository.findByCityOrderByDateDesc(c);
	}

	public void delete(CityCases c) {
		cityCasesRepository.delete(c);
	}

}
