package scs.covid.covorsicht.controllers;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mashape.unirest.http.Unirest;

import scs.covid.covorsicht.models.City;
import scs.covid.covorsicht.models.CityCases;
import scs.covid.covorsicht.services.CityCasesService;
import scs.covid.covorsicht.services.CityService;
import scs.covid.covorsicht.utility.ErrorUtility;
import scs.covid.covorsicht.utility.JsonResponse;

@RestController
@RequestMapping("/citycases")
public class CityCasesController {

	@Autowired
	CityCasesService cityCasesService;

	@Autowired
	CityService cityService;

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public @ResponseBody JsonResponse add(@RequestParam(value = "totalC", required = true) int totalC,
			@RequestParam(value = "newC", required = true) int newC,
			@RequestParam(value = "alert", required = true) boolean alert,
			@RequestParam(value = "date", required = true) String date,
			@RequestParam(value = "cityId", required = true) int cityId)

			throws UnsupportedEncodingException {

		JsonResponse response = new JsonResponse();

		// int[] cIds = citiesIds.split(",");

		CityCases cityCases = new CityCases();
		cityCases.setAlert(alert);
		City city = cityService.get((long) cityId);
		cityCases.setCity(city);
		cityCases.setTotalCases(totalC);
		cityCases.setNewCases(newC);
		cityCases.setDate(Long.valueOf(date));

		cityCases = cityCasesService.save(cityCases);

		response.setStatus("SUCCESS");
		response.setMessage(ErrorUtility.NO_ERROR.getMessage());
		response.setStatus(ErrorUtility.NO_ERROR.getName());
		response.setPayLoad(cityCases);
		return response;
	}

//	@RequestMapping(value = "/getCurrent", method = RequestMethod.GET)
//	public @ResponseBody JsonResponse getCurrent()
//
//			throws UnsupportedEncodingException {
//
//		JsonResponse response = new JsonResponse();
//
//		List<CityCases> cityCases = cityCasesService.getCurent(0, 0);
//		// List<City> cityCases = cityService.getCurent();
//
//		response.setStatus("SUCCESS");
//		response.setMessage(ErrorUtility.NO_ERROR.getMessage());
//		response.setStatus(ErrorUtility.NO_ERROR.getName());
//		response.setPayLoad(cityCases);
//		return response;
//	}

	@RequestMapping(value = "/getCurrentSingle", method = RequestMethod.GET)
	public @ResponseBody JsonResponse getCurrentSingle(@RequestParam(value = "cityId", required = true) int cityId)

			throws UnsupportedEncodingException {

		JsonResponse response = new JsonResponse();

		CityCases cityCases = cityCasesService.getSingle(cityService.get((long) cityId));
		// List<City> cityCases = cityService.getCurent();

		response.setStatus("SUCCESS");
		response.setMessage(ErrorUtility.NO_ERROR.getMessage());
		response.setStatus(ErrorUtility.NO_ERROR.getName());
		response.setPayLoad(cityCases);
		return response;
	}

	@RequestMapping(value = "/getByCity", method = RequestMethod.GET)
	public @ResponseBody JsonResponse getByCity(@RequestParam(value = "cityId", required = true) int cityId)

			throws UnsupportedEncodingException {

		JsonResponse response = new JsonResponse();

		// CityCases cityCases = cityCasesService.getSingle(cityService.get((long)
		// cityId));
		List<CityCases> cityCases = cityCasesService.getAllByCityDateDesc(cityService.get((long) cityId));

		response.setStatus("SUCCESS");
		response.setMessage(ErrorUtility.NO_ERROR.getMessage());
		response.setStatus(ErrorUtility.NO_ERROR.getName());
		response.setPayLoad(cityCases);
		return response;
	}

	@RequestMapping(value = "/get", method = RequestMethod.POST)
	public @ResponseBody JsonResponse addSubscriber(@RequestParam(value = "email", required = true) String email,
			@RequestParam(value = "citiesIds", required = true) int[] citiesIds) throws UnsupportedEncodingException {
		JsonResponse jresponse = new JsonResponse();

		try {
			com.mashape.unirest.http.HttpResponse<String> response = Unirest
					.get("https://www.citypopulation.de/en/germany/covid/")
					.header("cache-control", "no-cache").header("postman-token", "3247dd2d-3b96-4326-3e68-f228c3c37c50")
					.asString();
			String startTable = "";
			String fianlTable = "";
			if (response.getBody() != null || response.getBody() != "") {
				startTable = response.getBody().substring(response.getBody().indexOf("<table") + 0,
						response.getBody().length());
			}
			if (startTable != "") {
				fianlTable = startTable.substring(0, startTable.indexOf("</table>") + 8);
				System.out.println(fianlTable);
			}


		} catch (Exception e) {

		}

		jresponse.setStatus("SUCCESS");
		jresponse.setMessage(ErrorUtility.NO_ERROR.getMessage());
		jresponse.setStatus(ErrorUtility.NO_ERROR.getName());

		return jresponse;

	}
}
