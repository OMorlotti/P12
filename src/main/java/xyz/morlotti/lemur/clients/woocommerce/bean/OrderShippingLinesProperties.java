package xyz.morlotti.lemur.clients.woocommerce.bean;

import lombok.*;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderShippingLinesProperties
{
	private Integer id;

	private String method_title;

	private String method_id;

	private String total;

	private String total_tax;

	private OrderTaxLinesProperties taxes;

	private OrderMetadataProperties meta_data;
}
