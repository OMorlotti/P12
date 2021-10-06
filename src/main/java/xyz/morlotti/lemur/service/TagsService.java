package xyz.morlotti.lemur.service;

import xyz.morlotti.lemur.model.bean.Tag;

import java.util.List;

public interface TagsService
{
	List<Tag> getTags();

	void addTag(Tag tag);

	void updateTag(Tag tag);

	void deleteTag(int id);
}
