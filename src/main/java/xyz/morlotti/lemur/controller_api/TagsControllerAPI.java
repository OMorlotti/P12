package xyz.morlotti.lemur.controller_api;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

import xyz.morlotti.lemur.model.bean.Tag;
import xyz.morlotti.lemur.service.TagsService;
import xyz.morlotti.lemur.controller_api.bean.DataSource;

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

	@RequestMapping(value = "/api/tags/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public String deleteTags(@PathVariable("id") int id)
	{
		System.out.println(id);

		return "{}";
	}
}
