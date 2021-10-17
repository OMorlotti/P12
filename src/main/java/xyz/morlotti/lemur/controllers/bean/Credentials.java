package xyz.morlotti.lemur.controllers.bean;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Credentials
{
	private String username;

	private String password;
}
