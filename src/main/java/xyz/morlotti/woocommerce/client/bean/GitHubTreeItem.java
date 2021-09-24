package xyz.morlotti.woocommerce.client.bean;

import lombok.*;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GitHubTreeItem
{
	private String path;

	private String mode;

	private String type;

	private String sha;

	private Long size;

	private String url;
}
