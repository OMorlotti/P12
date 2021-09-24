package xyz.morlotti.woocommerce.client.bean;

import lombok.*;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GitHubUser
{
	private Integer id;

	private String login;

	private String name;

	private String email;

	private String html_url;

	private String avatar_url;
}
