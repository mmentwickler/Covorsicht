package scs.covid.covorsicht.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import scs.covid.covorsicht.models.City;
import scs.covid.covorsicht.repositories.CityRepository;

@Service
@Transactional
public class CityService {

	@Autowired
	CityRepository cityRepository;

	public City save(City city) {

		return cityRepository.save(city);
	}

	public City update(City city) {

		return cityRepository.saveAndFlush(city);
	}

	public City get(Long id) {
		return cityRepository.findById(id).orElse(null);
	}

	public List<City> getAll() {
		return cityRepository.findAll();
	}

	public void delete(City c) {
		cityRepository.delete(c);
	}

	public List<City> getCurent() {

		return null;// cityRepository.getCurrent();
	}

}
