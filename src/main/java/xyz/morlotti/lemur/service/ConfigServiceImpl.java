package xyz.morlotti.lemur.service;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import xyz.morlotti.lemur.model.bean.Config;
import xyz.morlotti.lemur.model.repositories.ConfigRepository;

@Service
public class ConfigServiceImpl implements ConfigService
{
	/*----------------------------------------------------------------------------------------------------------------*/

	@Autowired
	ConfigRepository configRepository;

	/*----------------------------------------------------------------------------------------------------------------*/

	@Override
	public Map<String, String> getConfig()
	{
		try
		{
			return configRepository.findAll().stream().collect(Collectors.toMap(x -> x.getKey(), y -> y.getVal()));
		}
		catch(Exception e)
		{
			return new HashMap<>();
		}
	}

	/*----------------------------------------------------------------------------------------------------------------*/

	@Override
	public void setConfig(Map<String, String> config)
	{
		List<Config> newConfig = config.entrySet().stream().map(x -> new Config(null, x.getKey(), x.getValue(), null)).collect(Collectors.toList());

		configRepository.deleteAll();

		configRepository.saveAll(newConfig);
	}

	/*----------------------------------------------------------------------------------------------------------------*/
}
