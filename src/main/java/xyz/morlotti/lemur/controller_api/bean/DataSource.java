package xyz.morlotti.lemur.controller_api.bean;

import lombok.*;

import java.util.List;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DataSource<T>
{
	private List<T> data;
}
