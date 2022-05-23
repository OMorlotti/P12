package xyz.morlotti.lemur.controllers_api.bean;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DeleteFolderOrFile
{
	private String path;
	private String name;
}
