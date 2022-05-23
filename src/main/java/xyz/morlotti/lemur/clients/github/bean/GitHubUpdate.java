package xyz.morlotti.lemur.clients.github.bean;

import lombok.*;

import java.util.Base64;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GitHubUpdate
{
	private String owner;

	private String message;

	private String content;

	private String sha;

	private String branch;

	public void setDecodedContent(byte[] data) throws Exception
	{
		content = Base64.getEncoder().encodeToString(data);
	}
}
