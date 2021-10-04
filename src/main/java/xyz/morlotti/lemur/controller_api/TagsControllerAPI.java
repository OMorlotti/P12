package xyz.morlotti.lemur.controller_api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import xyz.morlotti.lemur.controller_api.bean.DataSource;
import xyz.morlotti.lemur.model.bean.Tag;
import xyz.morlotti.lemur.service.TagsService;

@RestController
public class TagsControllerAPI
{
	@Autowired
	TagsService tagsService;

	@RequestMapping(value = "/api/tags", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public DataSource<Tag> tags()
	{
		return new DataSource<Tag>(tagsService.getTags());
	}
}
