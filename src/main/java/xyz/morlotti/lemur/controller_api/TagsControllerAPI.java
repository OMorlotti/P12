package xyz.morlotti.lemur.controller_api;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
	public ResponseEntity<String> deleteTags(@PathVariable("id") int id)
	{
		try
		{
			tagsService.deleteTag(id);
		}
		catch(RuntimeException e)
		{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}

		return ResponseEntity.status(HttpStatus.OK).body(String.valueOf(id));
	}
}
