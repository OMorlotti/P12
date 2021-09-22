package xyz.morlotti.woocommerce.client.bean;

import lombok.*;

import java.util.List;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductAttributsProperties
{
	private Integer id;

	private String name;

	private Integer position;

	private Boolean visible;

	private Boolean variation;

	private List<String> options;
}
