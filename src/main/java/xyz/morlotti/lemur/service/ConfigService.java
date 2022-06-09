package xyz.morlotti.lemur.service;

import java.util.Map;

public interface ConfigService
{
	Map<String, String> getConfig();

	void setConfig(Map<String, String> config);
}
