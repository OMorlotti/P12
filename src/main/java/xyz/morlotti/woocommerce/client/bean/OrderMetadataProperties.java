package xyz.morlotti.woocommerce.client.bean;

import lombok.*;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderMetadataProperties
{
	private Integer id;

	private String key;

	private String value;
}
