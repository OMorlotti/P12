package xyz.morlotti.lemur.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xyz.morlotti.lemur.model.bean.Tag;
import xyz.morlotti.lemur.repositories.TagRepository;

import java.util.List;

@Service
public class TagsServiceImpl implements TagsService
{
	@Autowired
	TagRepository tagRepository;

	@Override
	public List<Tag> getTags()
	{
		return tagRepository.findAll();
	}
}
