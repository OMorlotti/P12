package xyz.morlotti.lemur.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import xyz.morlotti.lemur.model.bean.Tag;
import xyz.morlotti.lemur.model.repositories.TagRepository;

@Service
public class TagsServiceImpl implements TagsService
{
	/*----------------------------------------------------------------------------------------------------------------*/

	@Autowired
	TagRepository tagRepository;

	/*----------------------------------------------------------------------------------------------------------------*/

	@Override
	public List<Tag> getTags()
	{
		return tagRepository.findAll();
	}

	/*----------------------------------------------------------------------------------------------------------------*/

	@Override
	public void addTag(Tag tag)
	{
		tagRepository.save(tag);
	}

	/*----------------------------------------------------------------------------------------------------------------*/

	@Override
	public void updateTag(Tag tag)
	{
		Tag existingTag = tagRepository.findById(tag.getId()).orElseThrow(() -> new RuntimeException("Tag `" + tag.getId() + "` not found"));

		existingTag.setName(tag.getName());

		tagRepository.save(existingTag);
	}

	/*----------------------------------------------------------------------------------------------------------------*/

	@Override
	public void deleteTag(int id)
	{
		Tag existingTag = tagRepository.findById(id).orElseThrow(() -> new RuntimeException("Tag `" + id + "` not found"));

		tagRepository.delete(existingTag);
	}

	/*----------------------------------------------------------------------------------------------------------------*/
}
