package xyz.morlotti.lemur.clients.github.bean;

import lombok.*;

import java.util.Base64;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GitHubDelete
{
	private String owner;

	private String repo;

	private String path;

	private String message;

	private String sha;
}
