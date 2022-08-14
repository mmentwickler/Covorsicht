package scs.covid.covorsicht.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import scs.covid.covorsicht.models.SubCities;
import scs.covid.covorsicht.models.Subscriber;
import scs.covid.covorsicht.repositories.SubCitiesRepository;

@Service
@Transactional
public class SubCitiesService {

	@Autowired
	private JavaMailSender emailSender;

	@Autowired
	SubCitiesRepository subCitiesRepository;

	@Autowired
	CityService cityService;

	public SubCities save(SubCities subCities) {

		return subCitiesRepository.save(subCities);
	}

	public SubCities update(SubCities subCities) {

		return subCitiesRepository.saveAndFlush(subCities);
	}

	public SubCities get(Long id) {
		return subCitiesRepository.findById(id).orElse(null);
	}

	public List<SubCities> getAll() {
		return subCitiesRepository.findAll();
	}

	public void addSubCities(int[] citiesIds, Subscriber subs) {
		for (int i : citiesIds) {

			// Checking if secondary role is not equal to primary role

			SubCities subCities = new SubCities();
			subCities.setSubscriber(subs);
			subCities.setCity(cityService.get((long) i));
			save(subCities);

		}
	}

	public void sendSimpleMessage(String to, String subject, String text) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("rajaue032@gmail.com");
		message.setTo(to);
		message.setSubject(subject);
		message.setText(text);
		emailSender.send(message);
	}

	public void delete(SubCities c) {
		subCitiesRepository.delete(c);
	}

}
