package xyz.morlotti.lemur.controllers_api.bean;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RenameFolderOrFile
{
	private String path;
	private String oldName;
	private String newName;
}
