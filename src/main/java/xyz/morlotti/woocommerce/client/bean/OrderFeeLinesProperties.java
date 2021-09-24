package xyz.morlotti.woocommerce.client.bean;

import lombok.*;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderFeeLinesProperties
{
	private Integer id;

	private String name;

	private String tax_class;

	private String tax_status;

	private String total;

	private String total_tax;

	private OrderTaxLinesProperties taxes;

	private OrderMetadataProperties meta_data;
}
