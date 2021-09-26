package xyz.morlotti.lemur.clients.woocommerce.bean;

import lombok.*;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderTaxLinesProperties
{
	private Integer id;

	private String rate_code;

	private String rate_id;

	private String label;

	private Boolean compound;

	private String tax_total;

	private String shipping_tax_total;

	private OrderMetadataProperties meta_data;
}
