package xyz.morlotti.woocommerce.client.bean;

import lombok.*;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductMetadataProperties
{
	private Integer id;

	private String key;

	private String value;
}
