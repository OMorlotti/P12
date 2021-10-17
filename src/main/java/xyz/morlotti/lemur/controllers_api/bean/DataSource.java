package xyz.morlotti.lemur.controllers_api.bean;

import lombok.*;

import java.util.List;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DataSource<T> // For using AJAX with datatables (see: https://datatables.net/)
{
	private List<T> data;
}
