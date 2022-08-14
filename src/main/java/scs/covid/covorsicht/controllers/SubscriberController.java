package scs.covid.covorsicht.controllers;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import scs.covid.covorsicht.models.Subscriber;
import scs.covid.covorsicht.services.SubCitiesService;
import scs.covid.covorsicht.services.SubscriberService;
import scs.covid.covorsicht.utility.ErrorUtility;
import scs.covid.covorsicht.utility.JsonResponse;

@RestController
@RequestMapping("/subscriber")
public class SubscriberController {

	@Autowired
	SubscriberService subscriberService;

	@Autowired
	SubCitiesService subCitiesService;

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public @ResponseBody JsonResponse addSubscriber(@RequestParam(value = "email", required = true) String email,
			@RequestParam(value = "citiesIds", required = true) int[] citiesIds) throws UnsupportedEncodingException {

		JsonResponse response = new JsonResponse();

		// int[] cIds = citiesIds.split(",");

		Subscriber subscriber = new Subscriber();
		subscriber.setEmail(email);

		subscriber = subscriberService.save(subscriber);

		subCitiesService.addSubCities(citiesIds, subscriber);
		

		response.setStatus("SUCCESS");
		response.setMessage(ErrorUtility.NO_ERROR.getMessage());
		response.setStatus(ErrorUtility.NO_ERROR.getName());
		response.setPayLoad(subscriber);
		return response;
	}

	@RequestMapping(value = "/sendmail", method = RequestMethod.POST)
	public @ResponseBody JsonResponse sendmail() throws UnsupportedEncodingException {

		JsonResponse response = new JsonResponse();

		subCitiesService.sendSimpleMessage("to", "subject", "text");

		return response;
	}

}
