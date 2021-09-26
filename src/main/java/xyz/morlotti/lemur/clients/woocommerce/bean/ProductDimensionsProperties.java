package xyz.morlotti.lemur.clients.woocommerce.bean;

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
