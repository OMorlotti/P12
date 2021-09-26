package xyz.morlotti.lemur.clients.woocommerce.bean;

import lombok.*;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderCouponLinesProperties
{
	private Integer id;

	private String code;

	private String discount;

	private String discount_tax;

	private OrderMetadataProperties meta_data;
}
