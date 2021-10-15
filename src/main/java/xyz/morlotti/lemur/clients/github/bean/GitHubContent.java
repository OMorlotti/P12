package xyz.morlotti.lemur.clients.github.bean;

import lombok.*;

import java.util.Base64;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GitHubContent
{
	private String name;

	private String path;

	private String content;

	private String encoding;

	public byte[] getDecodedContent() throws Exception
	{
		if(!"base64".equalsIgnoreCase(encoding))
		{
			throw new Exception("cannot decode content");
		}

		return Base64.getMimeDecoder().decode(content);
	}
}
