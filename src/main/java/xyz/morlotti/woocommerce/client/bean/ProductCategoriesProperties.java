package xyz.morlotti.woocommerce.client.bean;

import lombok.*;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductCategoriesProperties
{
	private Integer id;

	private String name;

	private String slug;
}
