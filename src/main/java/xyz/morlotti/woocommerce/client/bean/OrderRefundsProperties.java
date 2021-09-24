package xyz.morlotti.woocommerce.client.bean;

import lombok.*;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderRefundsProperties
{
	private Integer id;

	private  String reason;

	private String total;
}
