package xyz.morlotti.woocommerce.client.bean;

import lombok.*;

import java.util.List;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GitHubTree
{
	private String sha;

	private String url;

	private List<GitHubTreeItem> tree;
}
