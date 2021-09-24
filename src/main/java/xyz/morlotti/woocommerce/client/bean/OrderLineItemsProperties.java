package xyz.morlotti.woocommerce.client.bean;

import lombok.*;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderLineItemsProperties
{
	private Integer id;

	private String name;

	private Integer product_id;

	private Integer variation_id;

	private Integer quantity;

	private String tax_class;

	private String subtotal;

	private String subtotal_tax;

	private String total;

	private String total_tax;

	private OrderTaxLinesProperties taxes;

	private OrderMetadataProperties meta_data;

	private String sku;

	private String price;
}
