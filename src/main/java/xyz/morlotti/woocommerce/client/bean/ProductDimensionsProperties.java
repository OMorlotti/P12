package xyz.morlotti.woocommerce.client.bean;

import lombok.*;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductDimensionsProperties
{
	private String length;

	private String width;

	private String height;
}
