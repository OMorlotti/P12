package xyz.morlotti.lemur.service.bean;

import lombok.*;

import java.util.Map;
import java.util.HashMap;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TreeItem
{
	public enum Type {FOLDER, FILE}

	private int id;

	private Type type;

	private String hash;

	private String abbreviatedHash;

	private long size;

	private String path;

	private String name;

	private final Map<String, TreeItem> folders = new HashMap<>();

	private final Map<String, TreeItem> files = new HashMap<>();
}
