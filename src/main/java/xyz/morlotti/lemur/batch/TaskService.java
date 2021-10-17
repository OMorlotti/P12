package xyz.morlotti.lemur.batch;

import lombok.extern.log4j.Log4j2;

import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.beans.factory.annotation.Autowired;

import xyz.morlotti.lemur.service.ArtworksService;

@Log4j2
@Service
@EnableAsync
@EnableScheduling
public class TaskService
{
	/*----------------------------------------------------------------------------------------------------------------*/

	@Autowired
	ArtworksService artworksService;

	/*----------------------------------------------------------------------------------------------------------------*/

	// See: https://riptutorial.com/spring/example/21209/cron-expression
	@Scheduled(cron = "0 1 * * * *") // Tous les jours à 1h
	public void mainTask()
	{
		try
		{
			artworksService.synchronize();
		}
		catch (Exception e)
		{
			log.error("Error synchronizing shop and artworks: {}", e.getMessage());
		}
	}

	/*----------------------------------------------------------------------------------------------------------------*/
}
