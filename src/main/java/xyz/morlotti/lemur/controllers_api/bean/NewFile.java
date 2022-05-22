package xyz.morlotti.lemur.controllers_api.bean;

import lombok.*;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class NewFile
{
	private String path;
	private String name;
	private String base64;

	public byte[] getContent()
	{
		return Base64.getDecoder().decode(base64.getBytes(StandardCharsets.UTF_8));
	}
}
